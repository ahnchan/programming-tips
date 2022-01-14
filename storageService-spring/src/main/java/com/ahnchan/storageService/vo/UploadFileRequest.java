package com.ahnchan.storageService.vo;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.InputStream;

@Getter
@Setter
@SuperBuilder
public class UploadFileRequest {
    @NonNull
    private InputStream stream;
    @NonNull
    private String folder;
    @NonNull
    private String name;
    @NonNull
    private String contentType;
}
