import 'package:commerceimpl/Commun/constantes.dart';
import 'package:commerceimpl/Models/produit.dart';
import 'package:commerceimpl/Repositories/product_repository.dart';
import 'package:flutter/material.dart';

class ProviderProduct extends ChangeNotifier {
  List<Product> products = [];
  Product? editToProduct;
  int? pagesNumber = 0;

  // appeler ma repository
  final ProductRepository _repository = ProductRepository();

  // observateur sur la liste des produits
  getProductLists(int? page, String? searchValue, SortType? sortType,
      GetType getType) async {
    // on recupére la nos produits depuis le repository
    Map<String, dynamic> retournedData =
        await _repository.getProductLists(page, searchValue, sortType);

    // on stocke soit la liste des produit soit le numéro de page
    List<Product> pageProduct = retournedData['products list'];
    pagesNumber = retournedData['pages number'];

    // on test le type choisi par l'utilisateur et on affiche en conséquence
    if (getType == GetType.PAGING) {
      products = products + pageProduct;
    } else if (getType == GetType.FILTER) {
      products = pageProduct;
    }

    notifyListeners();
  }

  addProduct(Product product) async {
    Product savedProduct = await _repository.addProduit(product);
    products.add(savedProduct);
    notifyListeners();
  }

  editProduct(Product product) async {
    Product editedProduct = await _repository.updateProduit(product);
    products[products.indexOf(product)] = editedProduct;
    notifyListeners();
  }

  deleteProduct(Product product) async {
    await _repository.deleteProduit(product);
    products.remove(product);
    notifyListeners();
  }
}
