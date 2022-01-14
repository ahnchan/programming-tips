package com.ahnchan.storageService.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "aws-s3")
public class AwsS3Properties {
    private String accesskey;
    private String secretkey;
    private String bucket;
    private String region;
}
