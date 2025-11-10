import 'package:ecommerce/Providers/auth_provider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'home.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  State<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {

  final _formKey = GlobalKey<FormState>();

  final _emailField = TextEditingController();
  final _passwordField = TextEditingController();
  final _firstNameField = TextEditingController();
  final _lastNameField = TextEditingController();
  final _phoneField = TextEditingController();
  final _adressField = TextEditingController();

  @override
  void dispose(){
    _emailField.dispose();
    _passwordField.dispose();
    _firstNameField.dispose();
    _lastNameField.dispose();
    _phoneField.dispose();
    _adressField.dispose();
    super.dispose();
  }

  Future<void> _register() async {
    if (_formKey.currentState!.validate()){
      final authProvider = Provider.of<AuthProvider>(context, listen: false);
      try{
        await authProvider.register({
          'email': _emailField.text,
          'password': _passwordField.text,
          'firstName': _firstNameField.text,
          'lastName': _lastNameField.text,
          'phone': _phoneField.text,
          'adresse': _adressField.text
        });

        if (mounted){
          Navigator.of(context).pushReplacement(MaterialPageRoute(
              builder: (_) => const HomePage()
          ));
        }
      }
      catch(e){
        if (mounted){
          ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(content: Text("Erreur ${e.toString()}"))
          );
        }
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final authProvider = Provider.of<AuthProvider>(context);
    return Scaffold(
      appBar: AppBar(title: Text("Inscription"),),
      body: SafeArea(
          child: SingleChildScrollView(
            padding: EdgeInsets.all(24),
            child: Form(
              key: _formKey,
              child: Column(
                children: [
                  TextFormField(
                    controller: _emailField,
                    decoration: InputDecoration(
                        label: Text("E-mail"),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(
                              Radius.circular(20)
                          ),
                        ),
                        prefixIcon: Icon(Icons.email)
                    ),
                    keyboardType: TextInputType.emailAddress,
                    validator: (value) => value!.isEmpty ? "Requis" : null,
                  ),
                  SizedBox(height: 16,),
                  TextFormField(
                    controller: _passwordField,
                    decoration: InputDecoration(
                        label: Text("password"),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(
                              Radius.circular(20)
                          ),
                        ),
                        prefixIcon: Icon(Icons.lock)
                    ),
                    obscureText: true,
                    validator: (value) => value!.isEmpty ? "Requis" : null,
                  ),
                  SizedBox(height: 16,),
                  TextFormField(
                    controller: _firstNameField,
                    decoration: InputDecoration(
                        label: Text("Prénoms"),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(
                              Radius.circular(20)
                          ),
                        ),
                        prefixIcon: Icon(Icons.person)
                    ),
                    keyboardType: TextInputType.name,
                    validator: (value) => value!.isEmpty ? "Requis" : null,
                  ),
                  SizedBox(height: 16,),
                  TextFormField(
                    controller: _lastNameField,
                    decoration: InputDecoration(
                        label: Text("Nom"),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(
                              Radius.circular(20)
                          ),
                        ),
                        prefixIcon: Icon(Icons.person)
                    ),
                    keyboardType: TextInputType.name,
                    validator: (value) => value!.isEmpty ? "Requis" : null,
                  ),
                  SizedBox(height: 16,),
                  TextFormField(
                    controller: _phoneField,
                    decoration: InputDecoration(
                        label: Text("Téléphone"),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(
                              Radius.circular(20)
                          ),
                        ),
                        prefixIcon: Icon(Icons.phone)
                    ),
                    keyboardType: TextInputType.phone,
                    validator: (value) => value!.isEmpty ? "Requis" : null,
                  ),
                  SizedBox(height: 16,),
                  TextFormField(
                    controller: _adressField,
                    decoration: InputDecoration(
                        label: Text("Adresse"),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(
                              Radius.circular(20)
                          ),
                        ),
                        prefixIcon: Icon(Icons.apartment)
                    ),
                    keyboardType: TextInputType.streetAddress,
                    validator: (value) => value!.isEmpty ? "Requis" : null,
                  ),
                  SizedBox(height: 16,),
                  SizedBox(
                    width: double.infinity,
                    height: 50,
                    child: ElevatedButton(
                      style: ButtonStyle(
                          backgroundColor: WidgetStatePropertyAll(Colors.blue.shade600)),
                      onPressed: authProvider.isLoading ? null : _register,
                      child: authProvider.isLoading
                          ? CircularProgressIndicator(color: Colors.white)
                          : Text("S\'inscrire", style: TextStyle(fontSize: 16, color: Colors.white)),
                    ),
                  )
                ],
              )
            ),
          )),
    );
  }
}
