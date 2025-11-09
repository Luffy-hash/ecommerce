import 'package:flutter/cupertino.dart';

// couleurs de l'appli
// ignore: constant_identifier_names
const Color APP_COLOR = Color(0xff5b3bfe);

// nombre de page à afficher par defaut
// ignore: constant_identifier_names
const int PAGE_LIMIT = 10;

// choix d'affichage par defaut ASC, DESC
enum SortType {
  // ascendant
  // ignore: constant_identifier_names
  ASC,
  // descendant
  // ignore: constant_identifier_names
  DESC,
}

// type de filtre
enum GetType {
  // filtrer
  // ignore: constant_identifier_names
  FILTER,
  // Paginer
  // ignore: constant_identifier_names
  PAGING,
}
