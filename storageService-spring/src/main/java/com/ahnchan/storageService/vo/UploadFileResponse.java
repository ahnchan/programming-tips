package com.ahnchan.storageService.vo;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class UploadFileResponse {
    @NonNull
    private String folder;  // folder/bucket name
    @NonNull
    private String name;    // file name
    private String url; // If this is empty, This is not support WEB Access.
}
