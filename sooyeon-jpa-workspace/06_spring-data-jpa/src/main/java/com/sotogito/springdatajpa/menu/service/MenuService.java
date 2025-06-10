package com.sotogito.springdatajpa.menu.service;

import com.sotogito.springdatajpa.menu.dto.MenuDto;
import com.sotogito.springdatajpa.menu.entity.Menu;
import com.sotogito.springdatajpa.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    public MenuDto findMenuByCode(Integer menuCode) {
        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

//        MenuDto menuDto = new MenuDto().from(menu);
        return modelMapper.map(menu, MenuDto.class);
    }

    public List<MenuDto> findMenuList() {
        return menuRepository.findAll(Sort.by("menuCode").descending()).stream()
                .map(menu -> modelMapper.map(menu, MenuDto.class))
                .toList();
    }

}
