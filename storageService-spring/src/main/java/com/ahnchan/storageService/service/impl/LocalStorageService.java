package com.ahnchan.storageService.service.impl;

import com.ahnchan.storageService.exception.FileNotFoundException;
import com.ahnchan.storageService.exception.NoPermissionException;
import com.ahnchan.storageService.service.AbstractStorageService;
import com.ahnchan.storageService.service.StorageService;
import com.ahnchan.storageService.vo.*;
import com.google.common.io.Files;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class LocalStorageService extends AbstractStorageService implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalStorageService.class);

    @Value("${local-storage.path}")
    private String downloadPath;

    @Value("${local-storage.web.enable}")
    private boolean webAccess;

    @Value("${local-storage.web.url}")
    private String defaultUrl;

    @Override
    public UploadFileResponse uploadFile(UploadFileRequest request) {

        UploadFileResponse uploadFileResponse = null;

        // Exception
        // NoPermissionException, AccessDenyException
        // StorageIsFullException

        try {
            InputStream inputStream = stream(request.getStream());
            String fileName = "/"+ request.getFolder() +"/"+ request.getName();
            File targetFile = new File(downloadPath + fileName);
            java.nio.file.Files.copy(
                    inputStream,
                    targetFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            IOUtils.closeQuietly(inputStream);

            return UploadFileResponse.builder()
                    .name(request.getName())
                    .folder(request.getFolder())
                    .url((webAccess ? defaultUrl + fileName : ""))
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("file: "+ request.getName());
        }
    }

    @Override
    public Resource downloadFile(DownloadFileRequest request) throws FileNotFoundException, NoPermissionException {

        try {
            String folder = downloadPath +"/"+ request.getFolder();
            Path filePath = FileSystems.getDefault().getPath(folder, request.getName());
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("file: "+ folder +"/"+ request.getName());
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            throw new NoPermissionException();
        }
    }

    @Override
    public FileResponse getFile(FileRequest request) throws FileNotFoundException, NoPermissionException {

        FileResponse fileResponse = new FileResponse();

        try {
            if (webAccess) {
                fileResponse.setUrl(defaultUrl +"/"+ request.getFolder() +"/"+ request.getName());
            } else {
                String fileName = downloadPath +"/"+ request.getFolder() +"/"+ request.getName();
                File file = new File(fileName);
                fileResponse.setFile(Files.toByteArray(file));
            }

            fileResponse.setName(request.getName());

        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException(downloadPath +"/"+ request.getFolder() +"/"+ request.getName());
        } catch (IOException e1) {
            throw new NoPermissionException();
        }

        return fileResponse;
    }

}
