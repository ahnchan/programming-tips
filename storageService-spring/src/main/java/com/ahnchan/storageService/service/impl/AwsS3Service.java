package com.ahnchan.storageService.service.impl;

import com.ahnchan.storageService.service.AbstractStorageService;
import com.ahnchan.storageService.service.StorageService;
import com.ahnchan.storageService.vo.*;
import org.springframework.core.io.Resource;

public class AwsS3Service extends AbstractStorageService implements StorageService {

    @Override
    public UploadFileResponse uploadFile(UploadFileRequest request) {

        // TODO here

        UploadFileResponse uploadFileResponse = null;
        return uploadFileResponse;
    }

    @Override
    public Resource downloadFile(DownloadFileRequest request) {

        // TODO here

//        DownloadFileResponseponse downloadFileResponse = DownloadFileResponse.builder().build();
        Resource resource = null;

        return resource;
    }

    @Override
    public FileResponse getFile(FileRequest request) {
        return null;
    }
}
