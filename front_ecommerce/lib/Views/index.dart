
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../Providers/auth_provider.dart';
import 'home.dart';
import 'login.dart';

class IndexPage extends StatefulWidget {
  const IndexPage({super.key});

  @override
  State<IndexPage> createState() => _IndexPageState();
}

class _IndexPageState extends State<IndexPage> {

  @override
  void initState(){
    super.initState();
    _checkIndex();
  }

  Future<void> _checkIndex() async {
    final authProvider = Provider.of<AuthProvider>(context, listen: false);
    await authProvider.loadToken();

    await Future.delayed(Duration(seconds: 2));
    if (mounted){
      Navigator.of(context).pushReplacement(
          MaterialPageRoute(
              builder: (_) => authProvider.isAuthenticated
                  ? const HomePage()
                  : const LoginPage()
      ));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.shopping_bag, size: 100, color: Colors.blue.shade400,),
            SizedBox(height: 24,),
            Text("New-Commerce", style: TextStyle(fontSize: 32, fontWeight: FontWeight.bold),),
            SizedBox(height: 24,),
            CircularProgressIndicator()
          ],
        ),
      ),
    );
  }
}

