package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.CategoryExepton;
import com.example.e_commerce_backend.exception.ProductExepton;
import com.example.e_commerce_backend.model.Category;
import com.example.e_commerce_backend.model.Product;
import com.example.e_commerce_backend.repo.CategoryRepo;
import com.example.e_commerce_backend.repo.ProductRepo;
import com.example.e_commerce_backend.request.CreateProductReq;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpli implements ProductService{


    private ProductRepo productRepo;
    private UserService userService;
    private CategoryRepo categoryRepo;

    @Override
    public Product createProduct(CreateProductReq req)throws CategoryExepton {

        Category topLevel = categoryRepo.findByName(req.getTopLavelCategory());
        System.out.println(req.getTopLavelCategory());
        System.out.println(req.getSecondLavelCategory());
        System.out.println(req.getThirdLavelCategory());


        if (req.getTopLavelCategory() == null || req.getSecondLavelCategory() == null || req.getThirdLavelCategory() == null) {
            throw new CategoryExepton("Category names must not be null");
        }
        if (topLevel==null){
            Category topLevelCat = new Category();
            topLevelCat.setName(req.getTopLavelCategory());
            topLevelCat.setLevel(1);

            topLevel=categoryRepo.save(topLevelCat);
        }

        Category secondLevel=categoryRepo.findByNameAndParent(req.getSecondLavelCategory(), topLevel.getName());

        if(secondLevel==null) {
            Category secondLavelCategory=new Category();
            secondLavelCategory.setName(req.getSecondLavelCategory());
            secondLavelCategory.setParentCategory(topLevel);
            secondLavelCategory.setLevel(2);
            secondLevel=categoryRepo.save(secondLavelCategory);
        }

        Category thirdLevel=categoryRepo.findByNameAndParent(req.getThirdLavelCategory(),secondLevel.getName());

        if(thirdLevel==null) {
            Category thirdLavelCategory=new Category();
            thirdLavelCategory.setName(req.getThirdLavelCategory());
            thirdLavelCategory.setParentCategory(secondLevel);
            thirdLavelCategory.setLevel(3);
            thirdLevel=categoryRepo.save(thirdLavelCategory);
        }
        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPersent(req.getDiscountPersent());
        product.setImageUrl(req.getImgUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());
        Product savedProduct =productRepo.save(product);


        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductExepton {
        Product product =findProductByid(productId);
        product.getSizes().clear();
        productRepo.delete(product);
        return "Product deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductExepton {
        Product product1 = findProductByid(productId);
        if (product.getQuantity()!=0){
            product1.setQuantity(product.getQuantity());
        }

        return productRepo.save(product1);
    }

    @Override
    public Product findProductByid(Long productID) throws ProductExepton {
        System.out.println("productID"+productID);
        Optional<Product> product =productRepo.findById(productID);
        System.out.println(productID);
        System.out.println(product);
        if (product.isPresent()){
            return product.get();
        }
        throw new ProductExepton("Product Not Found With Id - "+ productID);
    }

    @Override
    public List<Product> findProductByCategory(String catagory) throws ProductExepton {
        return List.of();
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber,pageSize);
        List<Product> products = productRepo.filterProducts(category,minPrice,maxPrice,minDiscount,sort);
        if (!colors.isEmpty()){
            products = products.stream().filter(p-> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }
        if (stock!=null){
            if(stock.equals("in_stock")) {
                products = products.stream().filter(product -> product.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products=products.stream().filter(p -> p.getQuantity()<1).collect(Collectors.toList());
            }
        }
        int startIndex = (int) page.getOffset();
        int endIndex = Math.min(startIndex+page.getPageSize(),products.size());

        List<Product> pageControl = products.subList(startIndex,endIndex);

        Page<Product> filterpage = new PageImpl<>(pageControl,page,products.size());

        return filterpage;
    }
}
