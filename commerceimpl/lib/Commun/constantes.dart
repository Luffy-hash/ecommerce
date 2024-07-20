import 'package:flutter/cupertino.dart';

// couleurs de l'appli
const Color APP_COLOR = Color(0xff5b3bfe);

// nombre de page à afficher par defaut
const int PAGE_LIMIT = 10;

// choix d'affichage par defaut ASC, DESC
enum SortType {
  // ascendant
  ASC,
  // descendant
  DESC,
}

// type de filtre
enum GetType {
  // filtrer
  FILTER,
  // Paginer
  PAGING,
}
