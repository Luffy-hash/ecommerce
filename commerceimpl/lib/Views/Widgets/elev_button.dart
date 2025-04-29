import 'package:commerceimpl/Commun/constantes.dart';
import 'package:flutter/material.dart';

class ElevButton extends StatelessWidget {
  final String text;
  final IconData myIcon;
  final Color? myColor;
  final Function onPressed;

  const ElevButton(
      {super.key,
      required this.text,
      required this.myIcon,
      this.myColor,
      required this.onPressed});

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      style: ElevatedButton.styleFrom(backgroundColor: myColor ?? APP_COLOR),
      onPressed: () {
        onPressed();
      },
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(myIcon),
          const SizedBox(width: 10),
          Text(text),
        ],
      ),
    );
  }
}
