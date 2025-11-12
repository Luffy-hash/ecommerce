## code à revoir
SingleChildScrollView(
padding: EdgeInsets.only(
top: 16,
left: 16,
right: 16,
bottom: MediaQuery.of(context).viewInsets.bottom +16
),
child: Column(
crossAxisAlignment: CrossAxisAlignment.start,
children: [
Text(
"Recapitulatif",
style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
),
SizedBox(height: 16,),

            ListView.builder(
                itemCount: cartProvider.items.length,
                itemBuilder: (context, index) {
                  final cart = cartProvider.items[index];
                  return ListTile(
                    title: Text(cart.product.name),
                    subtitle: Text("Quantité : ${cart.quantity}"),
                    trailing: Text(
                      "${cart.totalPrice.toStringAsFixed(2)} €",
                      style: TextStyle(fontWeight: FontWeight.bold),
                    ),
                  );
                }
            ),
            Divider(),
            SizedBox(height: 16,),
            TextField(
              controller: _adressController,
              decoration: InputDecoration(
                labelText: "Adresse de livraison",
                border: OutlineInputBorder()
              ),
              maxLines: 3,
            ),
            SizedBox(height: 16,),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text("Total", style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),),
                Text(
                  "${cartProvider.totalAmount.toStringAsFixed(2)} €",
                  style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold, color: Theme.of(context).primaryColor),
                ),
                SizedBox(height: 16,),
                SizedBox(
                  width: double.infinity,
                  height: 50,
                  child: ElevatedButton(
                    onPressed: _isLoading ? null : _placeOrder,
                    child: _isLoading
                        ? CircularProgressIndicator(color: Colors.blue,)
                        : Text("Confirmer la commande")
                  ),
                )
              ],
            ),
          ],
        ),
      ),