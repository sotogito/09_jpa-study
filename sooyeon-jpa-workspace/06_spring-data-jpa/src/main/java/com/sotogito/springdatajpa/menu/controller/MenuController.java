package com.sotogito.springdatajpa.menu.controller;

import com.sotogito.springdatajpa.menu.dto.MenuDto;
import com.sotogito.springdatajpa.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/menu")
@Controller
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{menuCode}")
    public String menuDetail(@PathVariable Integer menuCode, Model model) {
        MenuDto menu = menuService.findMenuByCode(menuCode);

        System.out.println(menu);
        model.addAttribute("menu", menu);

        return "menu/detail";
    }

    @GetMapping("/list")
    public String menuList(Model model) {
        List<MenuDto> menuList =  menuService.findMenuList();

        model.addAttribute("menuList", menuList);

        return "menu/list";
    }

}
