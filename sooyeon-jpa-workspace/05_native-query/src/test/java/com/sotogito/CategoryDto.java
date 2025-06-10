package com.sotogito;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.EntityResult;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

/// dto에 하면 왜안돼
//@SqlResultSetMapping(
//        name = "categoryAndNameCountMapping2",
//        classes = @ConstructorResult(
//                targetClass = CategoryDto.class,
//                columns = {                         /// 순서, 타입 정의 중요
//                        @ColumnResult(name = "category_code", type = Integer.class),
//                        @ColumnResult(name = "category_name", type = String.class),
//                        @ColumnResult(name = "menu_count", type = Integer.class)
//                }
//        )
//)
public class CategoryDto {

    private Integer categoryCode;
    private String categoryName;
    private Integer menuCount;

}
