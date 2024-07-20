import 'dart:ffi';

import 'package:commerceimpl/Commun/constantes.dart';
import 'package:commerceimpl/Provider/provider_product.dart';
import 'package:commerceimpl/Views/Widgets/add_product_drawer.dart';
import 'package:commerceimpl/Views/Widgets/elev_button.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ProductsScreen extends StatefulWidget {
  const ProductsScreen({super.key});

  @override
  State<ProductsScreen> createState() => _ProductsScreenState();
}

class _ProductsScreenState extends State<ProductsScreen> {
  final GlobalKey<ScaffoldState> _scaffoldStateGlobalKey =
      GlobalKey<ScaffoldState>();
  List<String> sortBy = ['Prix Ascendant', 'Prix Descendant', 'Produit'];
  SortType? sortType;
  String? sortValue;
  String? searchValue;
  int pageNum = 0;
  int nextPage = 1;

  @override
  void initState() {
    super.initState();
    Future.delayed(Duration.zero, () async {
      await Provider.of<ProviderProduct>(context, listen: false)
          .getProductLists(0, null, null, GetType.PAGING);
      pageNum =
          // ignore: use_build_context_synchronously
          Provider.of<ProviderProduct>(context, listen: false).pagesNumber!;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      key: _scaffoldStateGlobalKey,
      appBar: AppBar(
        backgroundColor: APP_COLOR,
        title: const Text(
          'E-commerce Admin Panel ...',
          style: TextStyle(fontWeight: FontWeight.w700),
        ),
        centerTitle: true,
        actions: const [SizedBox()],
      ),
      endDrawer: const AddProductDrawer(),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const SizedBox(
            height: 30,
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 30),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text(
                  'Product ...',
                  style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold),
                ),
                ElevButton(
                  text: 'Ajout Produit',
                  myIcon: Icons.add,
                  onPressed: () {
                    Provider.of<ProviderProduct>(context, listen: false)
                        .editToProduct = null;
                    _scaffoldStateGlobalKey.currentState!.openEndDrawer();
                  },
                ),
              ],
            ),
          )
        ],
      ),
    );
  }
}
