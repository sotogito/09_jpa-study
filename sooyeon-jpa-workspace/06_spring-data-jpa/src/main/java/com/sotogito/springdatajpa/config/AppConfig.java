package com.sotogito.springdatajpa.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        /**
         * Menu 엔티티 => Menu DTO로 변환해서 반환
         * Entity <=> DTO 상호 반환 도와주는 라이브러리사용 : ModelMapper
         */
        return new ModelMapper();
    }

}
