package com.sotogito;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "category4")
@Table(name = "tbl_category")
@SqlResultSetMapping(
        name = "categoryAndNameCountMapping1",
        entities = {@EntityResult(entityClass = Category.class)},
        columns = {@ColumnResult(name = "menu_count")}
)
@SqlResultSetMapping(
        name = "categoryAndNameCountMapping2",
        classes = @ConstructorResult(
                targetClass = CategoryDto.class,
                columns = {                         /// 순서, 타입 정의 중요
                        @ColumnResult(name = "category_code", type = Integer.class),
                        @ColumnResult(name = "category_name", type = String.class),
                        @ColumnResult(name = "menu_count", type = Integer.class)
                }
        )
)
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
        return "com.sotogito.Category [categoryCode=" + categoryCode
                + ", categoryName=" + categoryName
                + ", refCategoryCode="+refCategoryCode
                +"]";
    }

}
