package fr.orleans.m1.wsi.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest
{
    private List<OrderItemsDTO> items;
    private String shippingAdress;

    @Data
    public static class OrderItemsDTO{
        private Long productId;
        private Integer quantity;
    }
}
