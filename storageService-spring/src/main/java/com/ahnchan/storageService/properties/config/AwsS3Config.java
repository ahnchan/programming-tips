package com.ahnchan.storageService.properties.config;

import com.ahnchan.storageService.properties.AwsS3Properties;
import com.ahnchan.storageService.service.StorageService;
import com.ahnchan.storageService.service.impl.AwsS3Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalStorageProperty(value="aws-s3.enable")
public class AwsS3Config {

    @Bean
    public AwsS3Properties awsS3Properties() {
        return new AwsS3Properties();
    }

    @Bean
    public StorageService awsS3Service(final AwsS3Properties AwsS3Properties) {
        return new AwsS3Service();
    }
}
