
import 'package:flutter/material.dart';

import '../Models/produit.dart';
import '../Services/app_service_rest.dart';


class ProductProvider with ChangeNotifier
{
  List<Product> _products = [];
  bool _isLoading = false;

  List<Product> get products => _products;
  bool get isLoading => _isLoading;

  Future<void> loadProducts(APIService apiService) async {
    _isLoading = true;
    notifyListeners();

    try {
      final data = await apiService.getProducts();
      _products = data.map((json) => Product.fromJson(json)).toList();
      _isLoading = false;
      notifyListeners();
    }
    catch(e){
      _isLoading = false;
      notifyListeners();
      rethrow;
    }
  }



}

