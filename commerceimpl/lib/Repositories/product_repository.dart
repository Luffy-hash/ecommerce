import 'dart:convert';

import 'package:commerceimpl/Commun/constantes.dart';
import 'package:commerceimpl/Models/produit.dart';
import 'package:commerceimpl/Services/app_service_rest.dart';
import 'package:http/http.dart' as http;

class ProductRepository {
  // on appel notre service api
  final APIService _apiService = APIService();

  // listes de mes produits
  Future<Map<String, dynamic>> getProductLists(
      int? page, String? searchValue, SortType? sortType) async {
    // on initialise notre pagination de départ et de fin
    Map<String, String> params = {
      'page': page.toString(),
      "limit": PAGE_LIMIT.toString()
    };

    // trier par le nom du produit renseigné
    if (searchValue != null) params['productName'] = searchValue;

    // trier par ordre Ascendant ou Descendant
    if (sortType != null) {
      params['sortType'] = sortType.toString().split('.').last;
    }

    // passer nos paramètre a notre api rest
    http.Response response = await _apiService.get('/product', params);

    // decoder notre reponse de l'api
    dynamic responseJsonDecode = jsonDecode(response.body);

    // recupérer la liste des produit
    final productData = responseJsonDecode['data']['content'] as List;
    List<Product> listProducts =
        productData.map((json) => Product.fromJson(json)).toList();

    // recupérer les datas de nos pages
    final pagesData = responseJsonDecode['data']['totalPages'];

    // renvoyer nos donnée recupérer
    Map<String, dynamic> returnData = {
      'products list': listProducts,
      'pages number': pagesData
    };

    return returnData;
  }

  // ajouter un produit
  Future<Product> addProduit(Product product) async {
    // donner le produit à rajouter
    http.Response response =
        await _apiService.post('/product/add', product.toJson(product));

    // decoder notre produit
    dynamic productDecode = jsonDecode(response.body);

    // recupére uniquement la donnée à enregistrer
    final jsonRecupProduct = productDecode['data'];
    Product productRegister = Product.fromJson(jsonRecupProduct);

    // renvoie notre produit
    return productRegister;
  }

  // editer un produit
  Future<Product> updateProduit(Product product) async {
    // donner le produit à rajouter
    http.Response response = await _apiService.put(
        '/product/update/${product.id}', product.toJson(product));

    // decoder notre produit
    dynamic productDecode = jsonDecode(response.body);

    // recupére uniquement la donnée à enregistrer
    final jsonRecupProduct = productDecode['data'];
    Product productUpdating = Product.fromJson(jsonRecupProduct);

    // renvoie notre produit
    return productUpdating;
  }

  // delete un produit
  Future<dynamic> deleteProduit(Product product) async {
    // donner le produit à rajouter
    http.Response response =
        await _apiService.delete('/product/delete/${product.id}');

    // decoder notre produit
    dynamic productDecode = jsonDecode(response.body);

    // recupére le message de retour
    final jsonMessageRecup = productDecode['message'];
    return jsonMessageRecup;
  }
}
