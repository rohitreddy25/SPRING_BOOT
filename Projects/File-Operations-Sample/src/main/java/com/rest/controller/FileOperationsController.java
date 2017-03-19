package com.rest.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rest.dto.FileMetaData;
import com.rest.service.FileOperationService;

@RestController
@RequestMapping("/fileoperations")
public class FileOperationsController {
	
	@Autowired
    private FileOperationService fileOperationService;

	@RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload( 
    		@RequestParam("file") MultipartFile file){
		return fileOperationService.saveFile(file);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/fileMetadata")
    public FileMetaData getFileMetadata(@RequestParam("fileId") String fileId) {
		return fileOperationService.getFileMetadata(fileId);
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/search")
	public Map<String, List> searchFileId(@RequestParam("fileId") String fileId,
			@RequestParam("creationDate") String creationDate) throws IOException {
		Map<String, List> response = new HashMap<String, List>();
		response.put("fileIds", fileOperationService.searchFileIds(fileId, creationDate));
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/download/{fileId:.+}")
	public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("fileId") String fileId) throws IOException {
		File file= new File("./uploads/"+fileId);
		 InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
	
	
}
