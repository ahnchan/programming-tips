package com.ahnchan.storageService.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private byte[] file;    // if enabled web access. this is empty
    private String name;    // file name
    private String url; // if enabled web access. this is not empty.
}