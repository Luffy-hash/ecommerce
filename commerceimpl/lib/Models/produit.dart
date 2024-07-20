import 'dart:ffi';

class Product {
  int? id;
  String? name;
  String? image;
  Long? price;
  Long? quantity;
  String? description;

  Product(
      {required this.id,
      required this.name,
      required this.image,
      required this.price,
      required this.quantity,
      required this.description});

  Product.empty();

  factory Product.fromJson(Map<String, dynamic> json) {
    return Product(
        id: json['id'],
        name: json['name'],
        image: json['image'],
        price: json['price'],
        quantity: json['quantity'],
        description: json['description']);
  }

  Map<String, dynamic> toJson(Product product) {
    return {
      "id": product.id,
      "name": product.name,
      "image": product.image,
      "price": product.price,
      "quantity": product.quantity,
      "description": product.description
    };
  }

  @override
  String toString() {
    return 'Product { id : $id, name: $name, image: $image, price: $price, quantity: $quantity, description: $description}';
  }
}
