
import 'package:flutter/material.dart';

import '../Models/cart_item.dart';
import '../Models/produit.dart';

class CardProvider with ChangeNotifier
{
  final List<CartItem> _items = [];

  List<CartItem> get items => _items;
  int get itemCount => _items.length;

  // calcul le montant total
  double get totalAmount => _items.fold<double>(0.0, (sum, item) => sum + item.totalPrice);

  void addItem(Product product){
    final existingIndex = _items.indexWhere((item) => item.product.id == product.id);
    if (existingIndex >= 0) { _items[existingIndex].quantity++; }
    else { _items.add(CartItem(product: product)); }
    notifyListeners();
  }

  void removeItem(Product product){
    _items.removeWhere((item) => item.product.id == product.id);
    notifyListeners();
  }

  void updatedQuantity(Product product, int quantity){
    final index = _items.indexWhere((item) => item.product.id == product.id);
    if (index >= 0){
      if (quantity <= 0) { _items.removeAt(index); }
      else { _items[index].quantity = quantity; }
      notifyListeners();
    }
  }

  void clear(){
    _items.clear();
    notifyListeners();
  }

}