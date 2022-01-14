package com.ahnchan.storageService.service;

import com.ahnchan.storageService.exception.FileNotFoundException;
import com.ahnchan.storageService.exception.NoPermissionException;
import com.ahnchan.storageService.vo.*;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public interface StorageService {
    public UploadFileResponse uploadFile(UploadFileRequest request);
    public Resource downloadFile(DownloadFileRequest request) throws FileNotFoundException, NoPermissionException;
    public FileResponse getFile(FileRequest request) throws FileNotFoundException, NoPermissionException;

//    public FileUrlResponse getFileUrl(FileUrlRequest request);
//    public DeleteFileResponse deleteFile(DeleteFileRequest request);
}
