package com.sotogito.springdatajpa.menu.service;

import com.sotogito.springdatajpa.menu.dto.CategoryDto;
import com.sotogito.springdatajpa.menu.dto.MenuDto;
import com.sotogito.springdatajpa.menu.entity.Category;
import com.sotogito.springdatajpa.menu.entity.Menu;
import com.sotogito.springdatajpa.menu.repository.CategoryRepository;
import com.sotogito.springdatajpa.menu.repository.MenuRepository;
import com.sotogito.springdatajpa.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final PageUtil pageUtil;

    public MenuDto findMenuByCode(Integer menuCode) {
        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

//        MenuDto menuDto = new MenuDto().from(menu);
        return modelMapper.map(menu, MenuDto.class);
    }

//    public List<MenuDto> findMenuList() {
//        List<Menu> sortedMenuList = menuRepository.findAll(Sort.by(
//                Sort.Order.asc("category.categoryCode"),
//                Sort.Order.asc("menuPrice")
//        ));
//        return sortedMenuList.stream()
//                .map(menu -> modelMapper.map(menu, MenuDto.class))
//                .toList();
//    }

    //        log.info("총 개수: {}", pageAndMenu.getTotalElements());
    //        log.info("한 페이지당 표현할 개수 : {}", pageAndMenu.getSize());
    //        log.info("총 페이지 수: {}", pageAndMenu.getTotalPages());
    //        log.info("현재 요청 페이지번호: {}", pageAndMenu.getNumber() + 1);
    //        log.info("첫 페이지 여부: {}", pageAndMenu.isFirst());
    //        log.info("마지막 페이지 여부: {}", pageAndMenu.isLast());
    //        log.info("실제 조회 개수: {}", pageAndMenu.getNumberOfElements());
    //        log.info("현재 조회된 메뉴 목록: {}", pageAndMenu.getContent());

    ///  withPage : 현재 pageable의 기존 설정은그래도 두고, 페이지 번호만 바꾼 new Pageable 객체 반환
    /// findAll(pageable) : Page<T> - 페이지 정보와 해당 요청 페이지에 필요한 엔티티 목록 조회 결과 List<T>가담신 페이지
    public Map<String, Object> findMenuList(Pageable pageable) {
        pageable = pageable.withPage(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1);
        if(pageable.getSort().isEmpty()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(Sort.Direction.DESC, "menuCode")
            );
        }

        Page<Menu> pageAndMenu = menuRepository.findAll(pageable);

        List<MenuDto> menuList = pageAndMenu.getContent()
                .stream()
                .map(menu -> modelMapper.map(menu, MenuDto.class))
                .toList();

        Map<String, Object> pageInfo = pageUtil.getPageInfo(pageAndMenu, 5);
        pageInfo.put("menuList", menuList);

        return pageInfo;
    }

    public List<CategoryDto> findCategoryList() {
        List<Category> categoryList = categoryRepository.findSubCategoryList();

        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();
    }

    @Transactional
    public void registMenu(MenuDto newMenu) {
        Menu menu = modelMapper.map(newMenu, Menu.class);
        Category category = categoryRepository.findById(newMenu.getCategoryCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category code"));
        menu.setCategory(category);

        menuRepository.save(menu);
    }

    @Transactional /// 자동 커밋 및 롤백 - 엔티티트랜잭션의 역할을 함
    public void modifyMenu(MenuDto menuDto) {
        Menu menu = menuRepository.findById(menuDto.getMenuCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid menu code"));

        Category category = categoryRepository.findById(menuDto.getCategoryCode()) //기본 메뉴에 저장되어있는 카테고리와 동일한 카테고리가 반환
                .orElseThrow(() -> new IllegalArgumentException("Invalid category code"));

        menu.setMenuName(menuDto.getMenuName());
        menu.setMenuPrice(menuDto.getMenuPrice());
        menu.setCategory(category);
        menu.setOrderableStatus(menuDto.getOrderableStatus());

        menuRepository.save(menu);
    }

    @Transactional
    public void deleteMenu(Integer menuCode) {
        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid menu code"));

        menuRepository.delete(menu);
    }

}
