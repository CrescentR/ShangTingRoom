package com.room.ShangTingRoom.web.admin.custom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.room.ShangTingRoom.web.admin.custom.converter.StringToBaseEnumConverterFactory;
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public StringToBaseEnumConverterFactory stringToBaseEnumConverterFactory() {
        return new StringToBaseEnumConverterFactory();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(stringToBaseEnumConverterFactory());
    }
}
