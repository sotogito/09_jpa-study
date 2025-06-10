package com.sotogito.springdatajpa.menu.dto;

import com.sotogito.springdatajpa.menu.entity.Category;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class CategoryDto implements Serializable {

    Integer categoryCode;
    String categoryName;
    Integer refCategoryCode;

    public CategoryDto from(Category category) {
        return CategoryDto.builder()
                .categoryCode(category.getCategoryCode())
                .categoryName(category.getCategoryName())
                .refCategoryCode(category.getRefCategoryCode())
                .build();
    }

}