package com.keisar.UserManagementApp.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.Conditions;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                // Define que o mapeamento deve ser estrito (evita ambiguidades)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                // Pula propriedades que estão nulas na origem
                .setPropertyCondition(Conditions.isNotNull())
                // Permite que o ModelMapper acesse campos privados (se necessário)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        return modelMapper;
    }
}
