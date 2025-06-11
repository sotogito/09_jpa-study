package com.sotogito.springdatajpa.menu.repository;

import com.sotogito.springdatajpa.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.refCategoryCode IS NOT NULL")
//    @Query(value = "SELECT c.category FROM tbl_category c WHERE c.ref_category_code IS NOT NULL", nativeQuery = true)
    List<Category> findSubCategoryList();

}
