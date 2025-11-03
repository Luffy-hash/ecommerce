package fr.orleans.m1.wsi.ecommerce.controllers;

import fr.orleans.m1.wsi.ecommerce.dto.OrderRequest;
import fr.orleans.m1.wsi.ecommerce.models.Order;
import fr.orleans.m1.wsi.ecommerce.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderControllers
{
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createdOrder(@RequestBody OrderRequest request, Authentication auth){
        return ResponseEntity.ok(orderService.createdOrder(request, auth.getName()));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getUsersOrder(Authentication auth){
        return ResponseEntity.ok(orderService.getUsersOrder(auth.getName()));
    }
}
