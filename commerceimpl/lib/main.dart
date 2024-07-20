import 'package:commerceimpl/Provider/provider_product.dart';
import 'package:commerceimpl/Views/Screens/product_screen.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<ProviderProduct>(
      create: (context) => ProviderProduct(),
      child: MaterialApp(
        title: 'Flutter Ecommerce demo',
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
          useMaterial3: true,
        ),
        home: const ProductsScreen(),
        debugShowCheckedModeBanner: false,
      ),
    );
  }
}
