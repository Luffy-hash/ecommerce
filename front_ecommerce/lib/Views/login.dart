import 'package:ecommerce/Providers/auth_provider.dart';
import 'package:ecommerce/Views/home.dart';
import 'package:ecommerce/Views/register.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  final _emailField = TextEditingController();
  final _passwordField = TextEditingController();

  // errors
  final _errors = [];

  void dispose(){
    _emailField.dispose();
    _passwordField.dispose();
    super.dispose();
  }

  Future<void> _login() async {
    if (_formKey.currentState!.validate()){
      final authProvider = Provider.of<AuthProvider>(context, listen: false);

      try{
        await authProvider.login(_emailField.text, _passwordField.text);
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
      body: SafeArea(
        child: SingleChildScrollView(
          padding: EdgeInsets.all(24),
          child: Center(
            child: Form(
                key: _formKey,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Icon(Icons.shopping_bag, size: 80, color: Colors.blue.shade700,),
                    SizedBox(height: 32,),
                    Text("Connexion", style: TextStyle(fontSize: 28, fontWeight: FontWeight.bold),),
                    SizedBox(height: 32,),
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
                      validator: (value) {
                        if (value == null || value.isEmpty){
                          return "Entrez votre email";
                        }
                        return null;
                      }
                    ),
                    SizedBox(height: 16,),
                    TextFormField(
                      controller: _passwordField,
                      decoration: InputDecoration(
                        label: Text("Mot de passe"),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(
                              Radius.circular(20)
                          ),
                        ),
                        prefixIcon: Icon(Icons.lock)
                      ),
                      obscureText: true,
                      validator: (value) {
                        if (value == null || value.isEmpty){
                          return "Entrez votre mot de passe";
                        }
                        return null;
                      },
                    ),
                    SizedBox(height: 24,),
                    SizedBox(
                      width: double.infinity,
                      height: 50,
                      child: ElevatedButton(
                          style: ButtonStyle(
                              backgroundColor: WidgetStatePropertyAll(Colors.blue.shade600)),
                          onPressed: authProvider.isLoading ? null : _login,
                          child: authProvider.isLoading
                              ? CircularProgressIndicator(color: Colors.white,)
                              : Text(
                                  "Se connecter",
                                  style: TextStyle(fontSize: 16, color: Colors.white),
                              )
                      ),
                    ),
                    SizedBox(height: 16,),
                    TextButton(
                        onPressed: () {
                          Navigator.of(context).push(
                            MaterialPageRoute(builder: (_) => RegisterPage())
                          );
                        },
                        child: Text(
                          "vous n'avez pas de compte ? Inscrivez-vous !",
                          style: TextStyle(fontWeight: FontWeight.bold),
                        )
                    )
                  ],
                )
            ),
          ),
        ),
      ),
    );
  }
}
