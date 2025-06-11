package com.sotogito.springdatajpa.menu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter

@Entity
@Table(name = "tbl_category")

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        return "com.sotogito.Category [categoryCode=" + categoryCode
                + ", categoryName=" + categoryName
                + ", refCategoryCode="+refCategoryCode
                +"]";
    }

}
