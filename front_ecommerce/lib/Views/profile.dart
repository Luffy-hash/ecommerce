import 'package:ecommerce/Providers/auth_provider.dart';
import 'package:ecommerce/Views/login.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ProfilePage extends StatelessWidget {
  const ProfilePage({super.key});

  @override
  Widget build(BuildContext context) {
    final authProvider = Provider.of<AuthProvider>(context);
    final user = authProvider.user;

    return Scaffold(
      appBar: AppBar(title: Text("Profil"),),
      body: ListView(
        children: [
          SizedBox(height: 20,),
          CircleAvatar(
            radius: 50,
            backgroundColor: Theme.of(context).primaryColor,
            child: Icon(Icons.person, size: 50, color: Colors.white,),
          ),
          SizedBox(height: 16,),
          Text(
            user?.email ?? "Utilisateur",
            textAlign: TextAlign.center,
            style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          ),
          SizedBox(height: 8,),
          Text(
            "${user?.firstName ?? ''} ${user?.lastName ?? ''}",
            textAlign: TextAlign.center,
            style: TextStyle(fontSize: 16, color: Colors.grey),
          ),
          SizedBox(height: 32,),
          ListTile(
            leading: Icon(Icons.shopping_bag,),
            title: Text("Mes commandes"),
            trailing: Icon(Icons.arrow_forward_ios),
            onTap: (){},
          ),
          Divider(),
          ListTile(
            leading: Icon(Icons.logout, color: Colors.red.shade500,),
            title: Text("Deconnexion", style: TextStyle(color: Colors.red.shade500),),
            onTap: () async {
              await authProvider.logout();
              Navigator.of(context).pushAndRemoveUntil(
                MaterialPageRoute(builder: (_) => LoginPage()),
                (route) => false);

            },
          )
        ],
      ),
    );
  }
}
