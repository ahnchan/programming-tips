package com.ahnchan.storageService.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.io.InputStream;

@Getter
@Builder
public class DownloadFileRequest {
    @NonNull
    private String folder;
    @NonNull
    private String name;
}
