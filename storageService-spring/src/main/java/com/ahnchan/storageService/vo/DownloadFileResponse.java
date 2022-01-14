package com.ahnchan.storageService.vo;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class DownloadFileResponse {
    private byte[] file;    // if enabled web access. this is empty
    private String name;    // file name
    private String url; // if enabled web access. this is not empty.
}