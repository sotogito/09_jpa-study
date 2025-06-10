package com.sotogito.springdatajpa.menu.dto;

import com.sotogito.springdatajpa.menu.entity.Menu;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class MenuDto implements Serializable {

    Integer menuCode;
    String menuName;
    Integer menuPrice;
//    CategoryDto category;
    Integer categoryCode; //자동으로 넣어줌
    String orderableStatus;


    public MenuDto from(Menu menu) {
        return MenuDto.builder()
                .menuCode(menu.getMenuCode())
                .menuName(menu.getMenuName())
                .menuPrice(menu.getMenuPrice())
                .categoryCode(menu.getCategory().getCategoryCode())
                .orderableStatus(menu.getOrderableStatus())
                .build();
    }

}