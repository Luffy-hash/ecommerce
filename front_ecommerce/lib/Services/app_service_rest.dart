import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:flutter/foundation.dart';

class APIService {
  //final String baseUrl = "http://10.21.171.251:8080/api";
  //final String baseUrl = "http://10.0.2.2:8080/api";

  static String get baseUrl {
    if (kIsWeb) { return "http://localhost:8080/api"; }
    else if (Platform.isAndroid) { return "http://10.21.171.251:8080/api"; }
    else { return "http://localhost:8080/api"; }
  }

  String? _token;

  void setToken(String token){
    _token = token;
  }

  Map<String, String> _getHeaders() {
    final header = { 'content-type': 'application/json'};
    if (_token != null && _token!.isNotEmpty) { header['Authorization'] = 'Bearer $_token'; }
    return header;
  }

  Future<Map<String, dynamic>> _handleResponse(http.Response response, String errorMsg) async {
    print("status code : ${response.statusCode}");
    print("response body : ${response.body}");

    if (response.statusCode >= 200 && response.statusCode < 300){
      try{

        if (response.body.isEmpty){ throw Exception("body vide"); }
        final decodeJson = jsonDecode(response.body);

        if(decodeJson == null){ throw Exception("body return null"); }
        return decodeJson as Map<String, dynamic>;
      }
      catch(e){
        print("Erreur de decodage JSON : $e");
        throw Exception("impossible de parser response : $e");
      }
    }
    else{
      String errorDetail = response.body;
      try {
        final errorJson = jsonDecode(response.body);
        errorDetail = errorJson['message'] ?? errorJson['error'] ?? response.body;
      }
      catch(_){}
      throw Exception("$errorMsg (${response.body}) : $errorDetail");
    }
  }

  Future<Map<String, dynamic>> login(String email, String password) async {
    try{
      final response = await http.post(
          Uri.parse("$baseUrl/auth/login"),
          headers: {'content-type' : 'application/json'},
          body: jsonEncode({'email' : email, 'password': password})
      );
      return _handleResponse(response, "Echec de connexion!");
    }
    catch(e){
      if (kDebugMode) {
        print("Login : $e");
      }
      rethrow;
    }
  }

  Future<Map<String, dynamic>> register(Map<String, dynamic> data) async {
    try {
      final response = await http.post(
          Uri.parse("$baseUrl/auth/register"),
          headers: {'content-type' : 'application/json'},
          body: jsonEncode(data)
      );
      if (kDebugMode) {
        print("code de sortie : ${response.statusCode}");
      }
      return _handleResponse(response, "Echec d'inscription");
    }
    catch(e){
      if (kDebugMode) {
        print("Register : $e");
      }
      rethrow;
    }
  }
  
  Future<List<dynamic>> getProducts() async {
    try{
      final response = await http.get(
          Uri.parse("$baseUrl/products"),
          headers: _getHeaders()
      );
      if (response.statusCode == 200) {
        if (response.body.isEmpty) { return [];}
        return jsonDecode(response.body) as List<dynamic>;
      }
      else { throw Exception("Pas de produit!"); }
    }
    catch(e){
      if (kDebugMode) {
        print("Products : $e");
      }
      rethrow;
    }
  }

  Future<Map<String, dynamic>> createdOrder(Map<String, dynamic> orderData) async {
    try{

      if (kDebugMode){
        print("Created Order ...");
        print("Token : $_token}");
        print("Headers : ${_getHeaders()}");
      }

      final response = await http.post(
          Uri.parse("$baseUrl/orders"),
          headers: _getHeaders(),
          body: jsonEncode(orderData)
      );
      if (kDebugMode){
        print("status code : ${response.statusCode}");
        print("body : ${response.body}");
      }
      return _handleResponse(response, "creation de commande echouer");
    }
    catch(e){
      if (kDebugMode) { print("created order : $e"); }
      rethrow;
    }
  }
  
  Future<List<dynamic>> getUserOrder() async {
    try {
      final response = await http.get(
          Uri.parse('$baseUrl/orders'),
          headers: _getHeaders()
      );

      if (response.statusCode == 200){
        if (response.body.isEmpty) { return []; }
        return jsonDecode(response.body) as List<dynamic>;
      }
      else { throw Exception("Pas de commande ! ${response.statusCode}"); }
    }
    catch(e){
      if (kDebugMode) { print("order by user : $e"); }
      rethrow;
    }
  }

}
