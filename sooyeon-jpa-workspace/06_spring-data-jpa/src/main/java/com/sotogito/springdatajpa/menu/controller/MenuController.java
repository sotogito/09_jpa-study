package com.sotogito.springdatajpa.menu.controller;

import com.sotogito.springdatajpa.menu.dto.CategoryDto;
import com.sotogito.springdatajpa.menu.dto.MenuDto;
import com.sotogito.springdatajpa.menu.service.MenuService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

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

//    @GetMapping("/list")
//    public String menuList(Model model) {
//        List<MenuDto> menuList =  menuService.findMenuList();
//
//        model.addAttribute("menuList", menuList);
//
//        return "menu/list";
//    }


    /**
     * ## Pageable
     * 1. 페이징 처리에 필요한 정보(page, size, sort)를 처리하는 인터페이스
     * 2. Pageable 객체를 통해서 페이징 처리와 정렬을 동시에 처리할 수 있음
     * 3. 사용방법
     * 1) 페이징 처리에 필요한 정보를 따로 파라미터 전달받아 직접 생성하는 방법
     * PageRequest.of(요청페이지번호, 조회할데이터건수, Sort객체)
     * 2) 정해진 파라미터(page, size, sort)로 전달받아 생성된 객체 바로 주입하는 방법
     *
     * @PageableDefault Pageable pageable
     * => 따로 전달된 파라미터가 존재하지 앟ㄴ을 경우 기본값(페이지번호, 조회할데이터건수, 정렬기준없음)
     * 4. 주의사항
     * Pageable 인터페이스는 조회할 페이지번호 0부터 인식
     * => 넘어오는 페이지번호 파라미터를 -1 처리해야함
     */

    // /menu/list?[page=xx]&[size=zz]&[sort,xxx,asc/desc]
    @GetMapping("/list")
    public String menuList(@PageableDefault Pageable pageable, Model model) {
        Map<String, Object> menuListPageInfo = menuService.findMenuList(pageable);

        model.addAttribute("menuList", menuListPageInfo.get("menuList"));
        model.addAttribute("page", menuListPageInfo.get("page"));
        model.addAttribute("beginPage", menuListPageInfo.get("beginPage"));
        model.addAttribute("endPage", menuListPageInfo.get("endPage"));
        model.addAttribute("isFirst", menuListPageInfo.get("isFirst"));
        model.addAttribute("isLast", menuListPageInfo.get("isLast"));

        return "menu/list";
    }

    @GetMapping("/regist")
    public String registPage() {
        return "menu/regist";
    }

    @ResponseBody
    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryDto> categoryList() {
        return menuService.findCategoryList();
    }

    @PostMapping("/regist")
    public String registMenu(@ModelAttribute MenuDto newMenu) {
        menuService.registMenu(newMenu);

        return "redirect:/menu/list";
    }

    @GetMapping("/modify")
    public String modifyPage(@RequestParam Integer menuCode, Model model) {
        model.addAttribute("menu", menuService.findMenuByCode(menuCode));

        return "menu/modify";
    }

    @PostMapping("/modify/{menuCode}")
    public String modifyMenu(@PathVariable Integer menuCode,
                             @ModelAttribute MenuDto newMenu) {
        menuService.modifyMenu(newMenu);

        return "redirect:/menu/"+menuCode;
    }

    @GetMapping("/remove/{menuCode}")
    public String removeMenu(@PathVariable Integer menuCode) {
        menuService.deleteMenu(menuCode);

        return "redirect:/menu/list";
    }

}
