package com.rest.service;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rest.dto.FileMetaData;
@Service
@Component
public class FileOperationService {
	
	public List<String> searchFileIds(String fileId, String dateOfCreation){
		
			
		
		List<String> fileIds = new ArrayList<String>();
		Map<String, String> fileMetadata = getFileMetadata();
		if(!fileMetadata.isEmpty()){
				for (Map.Entry<String, String> entry : fileMetadata.entrySet())
				{
					String[] metaDataArr = entry.getValue().split(",");
					String fileName = entry.getKey();
					boolean isAddEligible = false;
					if(!fileId.isEmpty() && !dateOfCreation.isEmpty()){
						if(fileName.equals(fileId) && metaDataArr[2].equals(dateOfCreation)){
							isAddEligible = true;
						}
					}else if(!fileId.isEmpty() && !dateOfCreation.isEmpty()){
						if(fileName.equals(fileId) && metaDataArr[2].equals(dateOfCreation)){
							isAddEligible = true;
						}
					}else if(!fileId.isEmpty()){
						if(fileName.equals(fileId)){
							isAddEligible = true;
						}
					}else if(!dateOfCreation.isEmpty()){
						if(metaDataArr[2].equals(dateOfCreation)){
							isAddEligible = true;
						}
					}
					if(isAddEligible){
						fileIds.add(fileName);
					}
				}
		}
		
		return fileIds;
	}
	
	public FileMetaData getFileMetadata(String fileId){
		FileMetaData metaDataObj = null;
		Map<String, String> fileMetaData = getFileMetadata();
		String metaData =  fileMetaData.get(fileId);
		if(metaData != null){
			metaDataObj = new FileMetaData();
			String[] metaDataArr = metaData.split(",");
			metaDataObj.setContentType(metaDataArr[1]);
			metaDataObj.setCreationTime(metaDataArr[2]);
			metaDataObj.setFileName(fileId);
			metaDataObj.setSize(metaDataArr[0]);
		}
		return metaDataObj;
	}
	
	public String saveFile(MultipartFile file){
		if (!file.isEmpty()) {
			if(getFileMetadata(file.getOriginalFilename()) != null){
				return "Error: Uploading duplicate file";
			}
			try{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = 
	                    new BufferedOutputStream(new FileOutputStream(new File("./uploads/"+file.getOriginalFilename())));
				stream.write(bytes);
	            stream.close();
	            FileOutputStream fos = new FileOutputStream(new File("./uploads/metadata_db.txt"), true);
	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	            
	        	bw.write(file.getOriginalFilename()+","+file.getSize()+","+file.getContentType()+","+simpleDateFormat.format(new Date()));
	        	bw.newLine();
	        	bw.close();
	            return "successfully uploaded";
			}catch(Exception e){
				return "Exception while uploaded "+e.getMessage();
			}
		}else{
			return "Error: empty file";
		}
	}

	private Map<String, String> getFileMetadata(){
		Map<String, String> metaData = null;
		try {
			Scanner file = new Scanner(new File("./uploads/metadata_db.txt"));
			metaData = new HashMap<String,String>();
			while(file.hasNextLine()){
				String line = file.nextLine();
				String[] lineArr = line.split(",", 2);
				metaData.put(lineArr[0], lineArr[1]);
			}
		} catch (FileNotFoundException e) {
			return metaData;
		}
		return metaData;
	}
}
