package com.example.e_commerce_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String review;

    @ManyToOne
    @JoinColumn(name="product_id")
    @JsonIgnore
    private Product product;

    @ ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;
    private LocalDateTime createdAt;
}
