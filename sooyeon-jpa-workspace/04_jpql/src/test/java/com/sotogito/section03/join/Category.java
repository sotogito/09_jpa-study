package com.sotogito.section03.join;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "category3")
@Table(name = "tbl_category")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_code")
    private Integer categoryCode;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    @OneToMany(mappedBy = "category")
    private List<Menu> menuList;



    @Override
    public String toString() {
        return "Category [categoryCode=" + categoryCode
                + ", categoryName=" + categoryName
                + ", refCategoryCode="+refCategoryCode
                +"]";
    }

}
