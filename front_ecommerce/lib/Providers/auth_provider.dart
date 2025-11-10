
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

import '../Models/user.dart';
import '../Services/app_service_rest.dart';

class AuthProvider with ChangeNotifier
{
  User? _user;
  String? _token;
  final APIService _apiService = APIService();
  bool _isLoading = false;

  User? get user => _user;
  String? get token => _token;
  bool get isAuthenticated => _token != null;
  bool get isLoading => _isLoading;
  APIService get apiService => _apiService;

  Future<void> login(String email, String password) async {
    _isLoading = true;
    notifyListeners();

    try{
      final response = await _apiService.login(email, password);
      _token = response['token'];
      _user = User.fromJson(response['user']);
      _apiService.setToken(_token!);

      final prefs = await SharedPreferences.getInstance();
      await prefs.setString('token', _token!);
      await prefs.setString('user', response['user'].toString());

      _isLoading = false;
      notifyListeners();

    }
    catch(e){
      _isLoading = false;
      notifyListeners();
      rethrow;
    }
  }

  Future<void> register(Map<String, dynamic> data) async{
    _isLoading = true;
    notifyListeners();

    try{
      final response = await _apiService.register(data);
      _token = response['token'];
      _user = User.fromJson(response['user']);
      _apiService.setToken(_token!);

      final prefs = await SharedPreferences.getInstance();
      await prefs.setString('token', _token!);

      _isLoading = false;
      notifyListeners();
    }
    catch(e){
      _isLoading = false;
      notifyListeners();
      rethrow;
    }
  }

  Future<void> loadToken() async {
    final prefs = await SharedPreferences.getInstance();
    _token = prefs.getString('token');
    if (_token != null) { _apiService.setToken(_token!); }
    notifyListeners();
  }

  Future<void> logout() async {
    _user = null;
    _token = null;
    final prefs = await SharedPreferences.getInstance();
    await prefs.clear();
    notifyListeners();
  }

}