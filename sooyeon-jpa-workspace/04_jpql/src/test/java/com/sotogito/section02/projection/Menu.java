package com.sotogito.section02.projection;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "menu2")
@Table(name = "tbl_menu")
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_code")
    private Integer menuCode;

    @Embedded
    private MenuInfo menuInfo;

    @Column(name = "category_code")
    private Integer categoryCode;

//    @OneToOne
//    @JoinColumn(name = "category_code")
//    private Category category;

    @Column(name = "orderable_status", nullable = false)
    private String orderableStatus;

}
