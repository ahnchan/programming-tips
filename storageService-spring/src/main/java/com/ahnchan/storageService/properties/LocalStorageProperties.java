package com.ahnchan.storageService.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "local-storage")
public class LocalStorageProperties {

    private String downloadLocalPath;
    private boolean webAccess;
    private String defaultUrl;
}
