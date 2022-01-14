package com.ahnchan.storageService.properties.config;

import com.ahnchan.storageService.properties.LocalStorageProperties;
import com.ahnchan.storageService.service.StorageService;
import com.ahnchan.storageService.service.impl.LocalStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalStorageProperty(value = "localstorage.enable")
public class LocalStorageConfig {

    @Bean
    public LocalStorageProperties localStorageProperties() {
        return new LocalStorageProperties();
    }

    @Bean
    public StorageService localStorageService(final LocalStorageProperties localStorageProperties) {
        return new LocalStorageService();
    }

}
