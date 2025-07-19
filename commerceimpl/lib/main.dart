import 'package:commerceimpl/Provider/provider_product.dart';
import 'package:commerceimpl/Views/auth/login.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {  

    // permettre à l'application d'être en plein ecran et de visualiser le contenue
    SystemChrome.setSystemUIOverlayStyle(
      const SystemUiOverlayStyle(statusBarColor: Colors.transparent),
    );
    
    return ChangeNotifierProvider<ProviderProduct>(
      create: (context) => ProviderProduct(),
      child: const MaterialApp(
        title: 'My Buisness place',
        home: Login(),
        debugShowCheckedModeBanner: false,
      ),
    );
  }
}
