import 'package:ecommerce/Providers/auth_provider.dart';
import 'package:ecommerce/Providers/card_provider.dart';
import 'package:ecommerce/Views/home.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class CheckoutCartPage extends StatefulWidget {
  const CheckoutCartPage({super.key});

  @override
  State<CheckoutCartPage> createState() => _CheckoutCartPageState();
}

class _CheckoutCartPageState extends State<CheckoutCartPage> {

  final _adressController = TextEditingController();
  bool _isLoading = false;

  Future<void> _placeOrder() async {
    if (_adressController.text.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text("Veuillez donnez une adresse"),
          backgroundColor: Colors.red.shade500,
        )
      );
    }

    setState(() { _isLoading = true; });
    final authProvider = Provider.of<AuthProvider>(context, listen: false);
    final cartProvider = Provider.of<CardProvider>(context, listen: false);

    if (authProvider.token == null || authProvider.token!.isEmpty){
      ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text("Token : ${authProvider.token}"),
            backgroundColor: Colors.grey.shade500,
          )
      );
      setState(() {
        _isLoading = false;
      });
      return;
    }

    final _orderCart = {
      'shippingAddress' : _adressController.text,
      'item' : cartProvider.items.map(
          (item) =>  {
            'productId' : item.product.id,
            'quantity' : item.quantity
          }
      ).toList()
    };

    try {
      if (kDebugMode) {
        print("je rentre ici ${authProvider.user?.lastName}");
      }
      await authProvider.apiService.createdOrder(_orderCart);
      Future.delayed(Duration(seconds: 2));
      cartProvider.clear();
      if (mounted) {
        Navigator.of(context).pushAndRemoveUntil(
          MaterialPageRoute(builder: (_) => HomePage()), 
          (route) => false
        );
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text("Commande envoyez avec succès"),
            backgroundColor: Colors.green.shade500,
          )
        );
      }
    }
    catch(e) {
      if (mounted) {
        print("erreur commande : $e");
        ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text("erreur commande : $e"),
              backgroundColor: Colors.red.shade500,
            )
        );
      }
    }
    finally { setState(() { _isLoading = false; }); }

  }

  @override
  void dispose(){
    _adressController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final cartProvider = Provider.of<CardProvider>(context);

    return Scaffold(
      appBar: AppBar(title: Text("finaliser ma commande"),),
      body: SingleChildScrollView(
        padding: EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text("Recapitulatif", style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),),
            SizedBox(height: 16,),
            ListView.builder(
              shrinkWrap: true,
              itemCount: cartProvider.items.length,
              itemBuilder: (context, index){
              final cart = cartProvider.items[index];
              return ListTile(
                title: Text(cart.product.name, style: TextStyle(fontSize: 24),),
                subtitle: cart.quantity >= 2
                    ? Text("Quantités : ${cart.quantity}", style: TextStyle(fontSize: 20),)
                    : Text("Quantité : ${cart.quantity}", style: TextStyle(fontSize: 16)),
                trailing: Text(
                  "${cart.product.price.toStringAsFixed(2)} €",
                  style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold, color: Theme.of(context).primaryColor),
                ),
              );
            }),
            Divider(),
            SizedBox(height: 16,),
            TextField(
              controller: _adressController,
              decoration: InputDecoration(
                labelText: 'Veuillez saisir votre adresse de livraison',
                border: OutlineInputBorder(),
                suffixIcon: Icon(Icons.home_filled, size: 32,)
              ),
            ),
            SizedBox(height: 8,),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text("Total", style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),),
                Text(
                  "${cartProvider.totalAmount} €",
                  style: TextStyle(color: Theme.of(context).primaryColor, fontSize: 24))
              ],
            ),
            SizedBox(height: 36,),
            SizedBox(
              width: double.infinity,
              height: 50,
              child: ElevatedButton(
                style: ButtonStyle(
                  backgroundColor: WidgetStatePropertyAll(Colors.blue.shade500)
                ),
                onPressed: _isLoading ? null : _placeOrder, 
                child: _isLoading 
                    ? CircularProgressIndicator(color: Colors.white,)
                    : Text("Confirmer la commande", style: TextStyle(color: Colors.white),)
              ),
            )
          ],
        ),
      ),
    );
  }
}
