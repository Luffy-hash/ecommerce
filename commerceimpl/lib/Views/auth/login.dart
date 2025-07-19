import 'package:flutter/material.dart';

class Login extends StatefulWidget {
  const Login({super.key});

  @override
  State<Login> createState() => _LoginState();
}

class _LoginState extends State<Login> {
  @override
  Widget build(BuildContext context) {

    final GlobalKey<FormState> formKey = GlobalKey<FormState>();

    final TextEditingController email = TextEditingController();
    final TextEditingController password = TextEditingController();

    // Message d'erreurs
    const String formEmpty = "Ce champs est requis !";

    @override
    // ignore: unused_element
    void dispose(){
      email.dispose();
      password.dispose();
      
      super.dispose();
    }

    return Scaffold(
      backgroundColor: const Color.fromRGBO(234, 248, 250, 0.993),
      body: SingleChildScrollView(
        child: Stack(
          children: [
            Transform(
              transform: Matrix4.identity()..rotateZ(20),
              origin: const Offset(150, 50),
              child: Image.asset(
                'assets/imgs/bg_liquid.png',
                width: 200,
              ),
            ),
            Positioned(
              right: 0,
              top: 200,
              child: Transform(
                transform: Matrix4.identity()..rotateZ(20),
                origin: const Offset(180, 100),
                child: Image.asset(
                  'assets/imgs/bg_liquid.png',
                  width: 200,
                ),
              ),
            ),
            // formulaire de connexion
            Container(
              padding: const EdgeInsets.all(25.0),
              decoration: const BoxDecoration(
                color: Color.fromRGBO(103, 225, 241, 0.802),
                borderRadius: BorderRadius.only(
                  bottomLeft: Radius.circular(180),
                  //bottomRight: Radius.circular(10),
                ),
              ),
              child: SizedBox(
                height: 700,
                child: Form (
                  key: formKey,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      TextFormField(
                        controller: email,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'E-mail',
                        ),
                        validator: (String? value) {
                          if (value == null || value.isEmpty) {
                            return formEmpty;
                          }
                          return null;
                        },
                      ),
                      const SizedBox(height: 30,),
                      TextFormField(
                        controller: password,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Mot de passe',
                        ),
                        validator: (String? value) {
                          if (value == null || value.isEmpty) {
                            return formEmpty;
                          }
                          return null;
                        },
                      ),
                      const SizedBox(height: 20,),
                      Padding(
                        padding: const EdgeInsets.all(0.0),
                        child: ElevatedButton(
                          style: ElevatedButton.styleFrom(
                              backgroundColor: const Color.fromRGBO(103, 225, 241, 0.802),
                              elevation: 1,
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(15), // button's shape
                              ),
                          ),
                          onPressed: () {
                            // Validate returns true if the form is valid, or false otherwise.
                            if (formKey.currentState!.validate()) {
                              // Process data.
                            }
                          },
                          child: const Text(
                            'Se connecter',
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 25,
                              color: Colors.black
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            )
          ],
        )
      ),
    );
  }
}