package com.sotogito.section02.projection;

import jakarta.persistence.*;

@Entity(name = "category2")
@Table(name = "tbl_category")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_code")
    private Integer categoryCode;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

}
