import 'package:ecommerce/Models/produit.dart';
import 'package:ecommerce/Providers/card_provider.dart';
import 'package:ecommerce/Views/card.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ProductDetailPage extends StatelessWidget {
  final Product product;
  const ProductDetailPage({
    required this.product,
    super.key
  });

  @override
  Widget build(BuildContext context) {
    final cartProvider = Provider.of<CardProvider>(context, listen: false);
    return Scaffold(
      appBar: AppBar(title: Text(product.name),),
      body: Column(
        children: [
          Expanded(child: SingleChildScrollView(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Container(
                  height: 300,
                  color: Colors.grey[200],
                  child: Center(
                    child: Icon(Icons.image, size: 100, color: Colors.grey[400],),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.all(16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(product.name, style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),),
                      SizedBox(height: 8,),
                      Text(
                      "${product.price.toStringAsFixed(2)} €",
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 24,
                        color: Theme.of(context).primaryColor
                      ),),
                      SizedBox(height: 16,),
                      Text("Description", style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),),
                      SizedBox(height: 8,),
                      Text(product.description),
                      SizedBox(height: 16,),
                      Row(
                        children: [
                          Text("Categorie "),
                          Chip(label: Text(product.category))
                        ],
                      ),
                      product.stock == 1
                          ? Text("Stock : ${product.stock} unité disponible")
                          : Text("Stock : ${product.stock} unités disponibles"),
                    ],
                  ),
                ),
                Container(
                  padding: EdgeInsets.all(16),
                  decoration: BoxDecoration(
                    color: Colors.white,
                    boxShadow: [
                      BoxShadow(
                        color: Colors.grey.withOpacity(0.3),
                        spreadRadius: 1,
                        blurRadius: 5,
                      ),
                    ]
                  ),
                  child: SizedBox(
                    width: double.infinity,
                    height: 50,
                    child: ElevatedButton(
                        style: ButtonStyle(backgroundColor: WidgetStatePropertyAll(Colors.green.shade500)),
                        onPressed: (){
                          cartProvider.addItem(product);
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(
                              backgroundColor: Colors.green.shade500,
                              content: Text("${product.name} ajouté au panier"),
                              duration: Duration(seconds: 2),
                            )
                          );
                        },
                        child: Text("Ajouter au panier", style: TextStyle(fontSize: 16, color: Colors.white))
                    ),
                  ),
                )
              ],
            ),
          ))
        ],
      ),
    );
  }
}
