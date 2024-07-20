import 'dart:convert';

import 'package:http/http.dart' as http;

class APIService {
  final String baseUrl = "http://localhost:8080/api";
  final Map<String, String> _headers = {
    "content-type": "application/json",
    "accept": "application/json"
  };

  // renvoie la liste des produits
  Future<http.Response> get(String url, Map<String, String> params) async {
    try {
      Uri uri = Uri.parse(baseUrl + url).replace(queryParameters: params);
      http.Response response = await http.get(uri);

      return response;
    } catch (e) {
      return http.Response({"message ": e}.toString(), 400);
    }
  }

  // Ajoute un produit dans la bd
  Future<http.Response> post(String url, Map<String, dynamic> body) async {
    try {
      Uri uri = Uri.parse(baseUrl + url);
      String myBody = json.encode(body);
      http.Response response =
          await http.post(uri, headers: _headers, body: myBody);

      return response;
    } catch (e) {
      return http.Response({"message ": e}.toString(), 400);
    }
  }

  // modifie un produit dans la base de données
  Future<http.Response> put(String url, Map<String, dynamic> body) async {
    try {
      Uri uri = Uri.parse(baseUrl + url);
      String myBody = json.encode(body);
      http.Response response =
          await http.put(uri, headers: _headers, body: myBody);

      return response;
    } catch (e) {
      return http.Response({"message ": e}.toString(), 400);
    }
  }

  // suppression d'un produit
  Future<http.Response> delete(String url) async {
    try {
      Uri uri = Uri.parse(baseUrl + url);
      http.Response response = await http.delete(uri, headers: _headers);
      return response;
    } catch (e) {
      return http.Response({"message ": e}.toString(), 400);
    }
  }
}
