package com.ino.springdatajpa.menu.controller;

import com.ino.springdatajpa.menu.dto.MenuDto;
import com.ino.springdatajpa.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{menuCode}")
    public String menuDetail(@PathVariable int menuCode, Model model){
        MenuDto menu = menuService.findMenuByCode(menuCode);
        model.addAttribute("menu", menu);
        return "menu/detail";
    }

    @GetMapping("/list")
    public String menuList(Model model){
        List<MenuDto> menu = menuService.findMenuList();
        model.addAttribute("menu", menu);
        return "menu/list";
    }
}
