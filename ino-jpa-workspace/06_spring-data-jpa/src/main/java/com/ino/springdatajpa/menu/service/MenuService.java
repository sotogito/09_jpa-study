package com.ino.springdatajpa.menu.service;

import com.ino.springdatajpa.menu.dto.MenuDto;
import com.ino.springdatajpa.menu.entity.Menu;
import com.ino.springdatajpa.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    // 1. findById
    public MenuDto findMenuByCode(int menuCode) {
        // findById(식별자)
        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(() -> new IllegalArgumentException("wrong menuCode"));

        log.info("menu: {}", menu);

        // Menu Entity -> MenuDTO 변환을 통해 반환
        // Entity <-> DTO 상호 변환 라이브러리 : ModelMapper
        MenuDto menuDto = modelMapper.map(menu, MenuDto.class);

        return menuDto;
    }

    public List<MenuDto> findMenuList() {
        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").ascending());
        log.info("menuList: {}", menuList);
        List<MenuDto> menuDtoList = menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDto.class))
                .collect(Collectors.toList());
        return menuDtoList;
    }
}
