package com.ahnchan.storageService.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class FileRequest {
    @NonNull
    private String folder;
    @NonNull
    private String name;
}
