package ru.iashinme.homework04.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AppSetting.class)
public class ApplicationConfig {
}
