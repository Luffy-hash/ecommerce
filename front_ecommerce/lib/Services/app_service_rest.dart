import 'dart:convert';

import 'package:http/http.dart' as http;

class APIService {
  final String baseUrl = "http://localhost:8080/api";

  String? _token;

  void setToken(String token){
    _token = token;
  }

  Map<String, String> _getHeaders() {
    final header = { 'content-type': 'application/json'};
    if (_token != null) { header['Authorization'] = 'Bearer $_token'; }
    return header;
  }

  Future<Map<String, dynamic>> login(String email, String password) async {
    final response = await http.post(
      Uri.parse("$baseUrl/auth/login"),
      headers: {'content-type' : 'application/json'},
      body: jsonEncode({'email' : email, 'password': password})
    );
    if (response.statusCode == 200){ return jsonDecode(response.body); }
    else { throw Exception("Echec de connexion"); }
  }

  Future<Map<String, dynamic>> register(Map<String, dynamic> data) async {
    final response = await http.post(
      Uri.parse("$baseUrl/auth/register"),
      headers: {'content-type' : 'application/json'},
      body: jsonEncode(data)
    );
    if (response.statusCode == 200) { return jsonDecode(response.body); }
    else { throw Exception("Echec d'inscription"); }
  }
  
  Future<List<dynamic>> getProducts() async {
    final response = await http.get(
      Uri.parse("$baseUrl/products"),
      headers: _getHeaders()
    );
    if (response.statusCode == 200) { return jsonDecode(response.body); }
    else { throw Exception("Pas de produit!"); }
  }

  Future<Map<String, dynamic>> createdOrder(Map<String, dynamic> orderData) async {
    final response = await http.post(
      Uri.parse("$baseUrl/orders"),
      headers: _getHeaders(),
      body: jsonEncode(orderData)
    );

    if (response.statusCode == 200){ return jsonDecode(response.body); }
    else { throw Exception("Erreur de creation!"); }
  }
  
  Future<List<dynamic>> getUserOrder() async {
    final response = await http.get(
      Uri.parse('$baseUrl/orders'),
      headers: _getHeaders()
    );

    if (response.statusCode == 200){ return jsonDecode(response.body); }
    else { throw Exception("Pas de commande !"); }
  }

}
