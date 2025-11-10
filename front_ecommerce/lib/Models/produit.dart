import 'dart:ffi';

class Product {
  final int id;
  final String name;
  final String description;
  final String? image;
  final double price;
  final String category;
  final int stock;

  Product({
      required this.id,
      required this.name,
      this.image,
      required this.price,
      required this.stock,
      required this.description,
    required this.category
  });

  factory Product.fromJson(Map<String, dynamic> json) {
    return Product(
      id: json['id'],
      name: json['name'],
      image: json['image'],
      price: json['price'],
      stock: json['stock'] as int ?? 0,
      description: json['description'],
      category: json['category']
    );
  }
}
