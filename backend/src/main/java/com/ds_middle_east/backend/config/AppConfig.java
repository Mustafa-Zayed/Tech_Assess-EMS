package com.ds_middle_east.backend.config;


import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * To map entities to DTOs and vice versa
 */
@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapperConfig() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true) // default is false
                .setFieldAccessLevel(AccessLevel.PRIVATE) // default is PUBLIC
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper;
    }
}
