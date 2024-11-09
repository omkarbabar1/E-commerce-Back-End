package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.ProductExepton;
import com.example.e_commerce_backend.model.Product;
import com.example.e_commerce_backend.request.CreateProductReq;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public interface ProductService {

    public Product createProduct(CreateProductReq createProductReq)throws Exception;
    public String deleteProduct(Long productId)throws ProductExepton;
    public Product updateProduct(Long productId,Product product)throws ProductExepton;
    public Product findProductByid(Long productID)throws ProductExepton;
    public List<Product> findProductByCategory(String catagory)throws ProductExepton;
    public Page<Product> getAllProduct(String category, List<String>colors, List<String>sizes, Integer minPrice,
                                       Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber,
                                       Integer pageSize);
}
