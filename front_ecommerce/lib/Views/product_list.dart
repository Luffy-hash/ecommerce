import 'package:ecommerce/Providers/card_provider.dart';
import 'package:ecommerce/Providers/product_provider.dart';
import 'package:ecommerce/Views/card.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ProductListPage extends StatelessWidget {
  const ProductListPage({super.key});

  @override
  Widget build(BuildContext context) {

    final productProvider = Provider.of<ProductProvider>(context);
    final cartProvider = Provider.of<CardProvider>(context);

    return Scaffold(
      appBar: AppBar(
        title: Text("Produits"),
        actions: [
          Stack(
            children: [
              IconButton(
                  onPressed: (){
                    Navigator.of(context).push(MaterialPageRoute(builder: (_) => CartPage()));
                    if (cartProvider.itemCount > 0) {
                      
                    }
                  },
                  icon: Icon(Icons.shopping_bag)
              ),
            ],
          )
        ],
      ),
    );
  }
}
