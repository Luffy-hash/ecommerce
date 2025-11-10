import 'package:ecommerce/Providers/card_provider.dart';
import 'package:ecommerce/Providers/product_provider.dart';
import 'package:ecommerce/Views/card.dart';
import 'package:ecommerce/Views/details/product_detail.dart';
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
                  },
                  icon: Icon(Icons.shopping_bag),
              ),
              if (cartProvider.itemCount > 0)
                Positioned(
                  right: 8,
                  top: 8,
                  child: Container(
                    padding: EdgeInsets.all(4),
                    decoration: BoxDecoration(
                      color: Colors.red,
                      shape: BoxShape.circle
                    ),
                    child: Text(
                      "${cartProvider.itemCount}",
                      style: TextStyle(fontSize: 10, color: Colors.white),
                    ),
                  ),
                )
            ],
          )
        ],
      ),
      body: productProvider.isLoading
          ? Center(child: CircularProgressIndicator(),)
          : GridView.builder(
          padding: EdgeInsets.all(16),
          gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: 2,
            childAspectRatio: 0.9,
            crossAxisSpacing: 10,
            mainAxisSpacing: 10
          ),
          itemCount: productProvider.products.length,
          itemBuilder: (context, index) {
            final product = productProvider.products[index];
            return GestureDetector(
              onTap: (){
                Navigator.of(context).push(
                    MaterialPageRoute(builder: (_) => ProductDetailPage(product: product)));
              },
              child: Card(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Expanded(child: Container(
                      decoration: BoxDecoration(
                        color: Colors.grey[200],
                        borderRadius: BorderRadius.vertical(
                          top: Radius.circular(4)
                        ),
                      ),
                      child: Center(
                        child: Icon(Icons.image, size: 50, color: Colors.grey[400],),
                      ),
                    )),
                    Padding(
                      padding: EdgeInsets.all(8),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            product.name,
                            style: TextStyle(fontWeight: FontWeight.bold),
                            maxLines: 2,
                            overflow: TextOverflow.ellipsis,
                          ),
                          SizedBox(height: 0,),
                          Text(
                            "${product.price.toStringAsFixed(2)} €",
                            style: TextStyle(
                              color: Theme.of(context).primaryColor,
                              fontWeight: FontWeight.bold
                            ),
                          )
                        ],
                      ),
                    )
                  ],
                ),
              ),
            );
          }
      ),
    );
  }
}
