
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'Providers/auth_provider.dart';
import 'Providers/card_provider.dart';
import 'Providers/product_provider.dart';
import 'Views/index.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => AuthProvider()),
        ChangeNotifierProvider(create: (_) => CardProvider()),
        ChangeNotifierProvider(create: (_) => ProductProvider())
      ],
      child: MaterialApp(
        title: 'Flutter Ecommerce demo',
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
          useMaterial3: true,
        ),
        home: const IndexPage(),
        debugShowCheckedModeBanner: false,
      ),
    );
  }
}
