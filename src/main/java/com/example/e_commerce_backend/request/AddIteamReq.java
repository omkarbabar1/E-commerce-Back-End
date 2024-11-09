package com.example.e_commerce_backend.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddIteamReq {
    private Long productld;
    private String size;
    private int quantity;
    private Integer price;
}
