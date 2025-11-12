package fr.orleans.m1.wsi.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest
{
    private List<OrderItemDTO> items;
    private String shippingAddress;

    @Data
    public static class OrderItemDTO{
        private Long productId;
        private Integer quantity;
    }
}
