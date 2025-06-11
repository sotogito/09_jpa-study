package com.johnth.section02.projection;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryResponseDto {
    private Integer categoryCode;
    private String categoryName;
}
