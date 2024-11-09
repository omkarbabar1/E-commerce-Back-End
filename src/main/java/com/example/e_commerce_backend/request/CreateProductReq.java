package com.example.e_commerce_backend.request;

import com.example.e_commerce_backend.model.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Data
public class CreateProductReq {
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int discountPersent;
    private int quantity;
    private String brand;
    private String color;
    private Set<Size> size = new HashSet<>();
    private String imgUrl;
    private String topLavelCategory;
    private String secondLavelCategory;
    private String thirdLavelCategory;
}
