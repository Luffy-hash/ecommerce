package fr.orleans.m1.wsi.ecommerce.services;

import fr.orleans.m1.wsi.ecommerce.dto.OrderRequest;
import fr.orleans.m1.wsi.ecommerce.models.Order;
import fr.orleans.m1.wsi.ecommerce.models.OrderItem;
import fr.orleans.m1.wsi.ecommerce.models.Product;
import fr.orleans.m1.wsi.ecommerce.models.User;
import fr.orleans.m1.wsi.ecommerce.repositories.OrderRepository;
import fr.orleans.m1.wsi.ecommerce.repositories.ProductRepository;
import fr.orleans.m1.wsi.ecommerce.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public Order createdOrder(OrderRequest request, String userEmail){
        User user = usersRepository.findUserByEmail(userEmail).orElseThrow(
                () -> new RuntimeException("User not Found")
        );

        Order order = new Order();
        order.setUser(user);
        order.setShippingAdress(request.getShippingAdress());

        double total = 0.0;

        for(OrderRequest.OrderItemsDTO orderItemsDTO : request.getItems()){
            Product product = productRepository.findById(orderItemsDTO.getProductId()).orElseThrow(
                    () -> new RuntimeException("Not found product ")
            );

            if (product.getStock() < orderItemsDTO.getQuantity()){
                throw new RuntimeException("Stock insuffisant pour ce produit " + product.getName());
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(orderItemsDTO.getQuantity());
            item.setPrice(product.getPrice());

            order.getOrderItems().add(item);
            total += product.getPrice() * orderItemsDTO.getQuantity();

            product.setStock(product.getStock() - orderItemsDTO.getQuantity());
            productRepository.save(product);
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getUsersOrder(String userEmail) {
        User user = usersRepository.findUserByEmail(userEmail).orElseThrow(
                () -> new RuntimeException("Not found User")
        );
        return orderRepository.findByUserId(user.getId());
    }
}
