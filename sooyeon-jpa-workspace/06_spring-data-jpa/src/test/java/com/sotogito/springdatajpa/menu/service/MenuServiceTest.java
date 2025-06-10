package com.sotogito.springdatajpa.menu.service;

import com.sotogito.springdatajpa.menu.dto.MenuDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    void findMenuByCode() {
        MenuDto menu = menuService.findMenuByCode(5);
        assertNotNull(menu);
    }

    @Test
    void findMenuByName() {
        List<MenuDto> result = menuService.findMenuList();
        result.forEach(System.out::println);
    }

}