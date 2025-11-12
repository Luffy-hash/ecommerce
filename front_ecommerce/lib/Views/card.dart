import 'package:ecommerce/Providers/card_provider.dart';
import 'package:ecommerce/Views/details/checkout_cart.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class CartPage extends StatelessWidget {
  const CartPage({super.key});

  @override
  Widget build(BuildContext context) {
    final cartProvider = Provider.of<CardProvider>(context);

    return Scaffold(
      appBar: AppBar(title: Text("Panier"),),
      body: cartProvider.items.isEmpty
        ? Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Icon(Icons.shopping_bag, size: 50, color: Colors.blue.shade500,),
              SizedBox(height: 16,),
              Text("Votre panier est vide")
            ],
          ),
        )
        : Column(
          children: [
            Expanded(
              child: ListView.builder(
                itemCount: cartProvider.items.length,
                itemBuilder: (context, index) {
                  final cart = cartProvider.items[index];
                  return Card(
                    margin: EdgeInsets.symmetric(vertical: 16, horizontal: 8),
                    child: Padding(
                      padding: EdgeInsets.all(12),
                      child: Row(
                        children: [
                          Container(
                            width: 80,
                            height: 80,
                            color: Colors.grey[200],
                            child: const Icon(Icons.image),
                          ),
                          const SizedBox(width: 12),
                          Expanded(
                            child: Column(
                              children: [
                                Text(
                                  cart.product.name,
                                  style: const TextStyle(
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                const SizedBox(height: 4),
                                Text(
                                  '${cart.product.price.toStringAsFixed(2)} €',
                                  style: TextStyle(
                                    color: Theme.of(context).primaryColor,
                                  ),
                                ),
                              ],
                            )
                          ),
                          Row(
                            children: [
                              IconButton(
                                icon: const Icon(Icons.remove),
                                onPressed: () {
                                  cartProvider.updatedQuantity(
                                    cart.product,
                                    cart.quantity - 1,
                                  );
                                },
                              ),
                              Text('${cart.quantity}'),
                              IconButton(
                                icon: const Icon(Icons.add),
                                onPressed: () {
                                  cartProvider.updatedQuantity(
                                    cart.product,
                                    cart.quantity + 1,
                                  );
                                },
                              ),
                              IconButton(
                                icon: const Icon(Icons.delete, color: Colors.red),
                                onPressed: () {
                                  cartProvider.removeItem(cart.product);
                                },
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  );
                },
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
                ],
              ),
              child: Column(
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        "Total",
                        style: TextStyle(
                            fontSize: 20,
                            fontWeight: FontWeight.bold
                        ),
                      ),
                      Text(
                        "${cartProvider.totalAmount.toStringAsFixed(2)} €",
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 24,
                          color: Theme.of(context).primaryColor
                        ),
                      )
                    ],
                  ),
                  SizedBox(height: 16,),
                  SizedBox(
                    width: double.infinity,
                    height: 50,
                    child: ElevatedButton(
                      style: ButtonStyle(
                        backgroundColor: WidgetStatePropertyAll(Colors.blue.shade500)
                      ),
                      onPressed: (){
                        Navigator.push(context, MaterialPageRoute(builder: (_) => CheckoutCartPage()));
                      },
                      child: Text("Commander", style: TextStyle(color: Colors.white),)
                    ),
                  )
                ],
              ),
            ),
          ],
        )
    );
  }
}
