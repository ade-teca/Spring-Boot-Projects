package com.keisar.Library.Management.Application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public org.modelmapper.ModelMapper modelMapper(){
        org.modelmapper.ModelMapper modelMapper = new org.modelmapper.ModelMapper();

        // Esta configuração impede que valores nulos no DTO sobrescrevam dados na Entity
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);

        return modelMapper;
    }
}
