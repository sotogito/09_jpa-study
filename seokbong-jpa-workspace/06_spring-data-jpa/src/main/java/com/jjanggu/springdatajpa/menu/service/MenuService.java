package com.jjanggu.springdatajpa.menu.service;

import com.jjanggu.springdatajpa.menu.dto.MenuDto;
import com.jjanggu.springdatajpa.menu.entity.Menu;
import com.jjanggu.springdatajpa.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuService {

    private final ModelMapper modelMapper;
    private final MenuRepository  menuRepository;

    // 1.findById
    public void findMenuByCode(int menuCode) {

        // findById(식별자) : Optional<T>
        // * Optional : NullPointerException 방지를 위한 다양한 기능 제공

        Menu menu = menuRepository.findById(menuCode)
                                  .orElseThrow(() -> new IllegalArgumentException("잘못된 메뉴 코드입니다."));

        // Menu 엔티티 => Menu DTO로 변환해서 반환
        // Entitiy <=> DTO 상화 변환 도와주는 라이브러리 사용 : ModelMapper
        MenuDto menuDto = modelMapper.map(menu, MenuDto.class);

    }
}
