import 'package:ecommerce/Providers/auth_provider.dart';
import 'package:ecommerce/Providers/product_provider.dart';
import 'package:ecommerce/Views/card.dart';
import 'package:ecommerce/Views/product_list.dart';
import 'package:ecommerce/Views/profile.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int _selectedIndex = 0;

  @override
  void initState(){
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback( (_) {
      final authProvider = Provider.of<AuthProvider>(context, listen: false);
      final productProvider = Provider.of<ProductProvider>(context, listen: false);
      productProvider.loadProducts(authProvider.apiService);
    });
  }

  @override
  Widget build(BuildContext context) {
    final pages = [
      ProductListPage(),
      CartPage(),
      ProfilePage()
    ];

    return Scaffold(
      body: pages[_selectedIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.blue.shade500,
        onTap: (index) => setState(() => _selectedIndex = index),
        items: [
          BottomNavigationBarItem(icon: Icon(Icons.home_filled, size: 42,), label: "Acceuil"),
          BottomNavigationBarItem(icon: Icon(Icons.shopping_bag, size: 42), label: "Panier"),
          BottomNavigationBarItem(icon: Icon(Icons.person, size: 42), label: "Profil")
        ]
      ),
    );
  }
}
