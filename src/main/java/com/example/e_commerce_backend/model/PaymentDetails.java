package com.example.e_commerce_backend.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor@AllArgsConstructor
public class PaymentDetails {
    private String  paymentMethod;
    private String  status;
    private String paymentld;
    private String razorpayPaymentLinkld;
    private String razorpayPaymentLinkReferenceld;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentld;
}
