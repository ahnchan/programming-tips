package com.ahnchan.storageService;

import com.ahnchan.storageService.exception.FileNotFoundException;
import com.ahnchan.storageService.exception.NoPermissionException;
import com.ahnchan.storageService.service.StorageService;
import com.ahnchan.storageService.vo.*;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@SpringBootApplication
public class StorageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageServiceApplication.class, args);
	}

}

@RestController
class StorageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StorageController.class);

	// Storage Service
	private StorageService storageService;

	public StorageController(StorageService storageService) {
		this.storageService = storageService;
	}

	@PostMapping("/file")
	public UploadFileResponse uploadFile(@RequestBody MultipartFile file, @RequestParam String name, @RequestParam String folder) {
		try {
			return storageService.uploadFile(UploadFileRequest.builder()
					.stream(new ByteArrayInputStream(file.getBytes()))
					.folder(folder)
					.name(name)
					.contentType(file.getContentType())
					.build());
		} catch(IOException ie) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "", ie);
		} catch (RuntimeException re) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "", re);
		}
	}

	@GetMapping("/file")
	public FileResponse getFile(@RequestParam String name, @RequestParam String folder) {

		try {
			return storageService.getFile(FileRequest.builder()
					.folder(folder)
					.name(name)
					.build());

		} catch (FileNotFoundException fe) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, fe.getMessage(), fe);
		} catch (NoPermissionException ne) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "", ne);
		}
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile(@RequestParam String name, @RequestParam String folder, HttpServletRequest request) {

		Resource resource = null;
		try {
			resource = storageService.downloadFile(DownloadFileRequest.builder()
						.name(name)
						.folder(folder)
						.build());
		} catch (FileNotFoundException fe) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, fe.getMessage(), fe);
		} catch (NoPermissionException pe) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "", pe);
		}

		if (resource == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "", null);
		}

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOGGER.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if(contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
