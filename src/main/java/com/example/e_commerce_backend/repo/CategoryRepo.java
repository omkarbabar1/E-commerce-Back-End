package com.example.e_commerce_backend.repo;

import com.example.e_commerce_backend.exception.CategoryExepton;
import com.example.e_commerce_backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

    public Category findByName(String name)throws CategoryExepton;

    @Query("SELECT c FROM Category c " +
            "WHERE c.name =:name " +
            "AND c.parentCategory.name=:parentCategoryName")
    public Category findByNameAndParent(@Param("name")String name,
                                        @Param("parentCategoryName")String parentCategoryName);
}
