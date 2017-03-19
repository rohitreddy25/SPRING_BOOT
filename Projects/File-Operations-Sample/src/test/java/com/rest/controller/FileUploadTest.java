package com.rest.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;

import com.rest.dto.FileMetaData;
import static com.jayway.restassured.RestAssured.expect;
public class FileUploadTest extends BaseControllerTest {
	@Test
	public void testFileUpload() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		File file = new File("./test/sample.pdf");
		HttpPost post = new HttpPost("http://localhost:8080/fileoperations/upload");
		FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addPart("file", fileBody);
		HttpEntity entity = builder.build();
		post.setEntity(entity);
		HttpResponse response = httpclient.execute(post);
		
		response.getEntity();
	}
	
	
	
	@Test
	public void testSearch(){
		Map<String, List> response = expect().
                statusCode(HttpStatus.OK.value()).
                when().
                get("http://localhost:8080/fileoperations/search?fileId=test.pdf&creationDate=").
                as(Map.class);
		 Assert.assertNotNull(response);
		 Assert.assertTrue(response.get("fileIds").get(0).equals("test.pdf"));
	}
	
	@Test
	public void testGetFileMetadata(){
		System.out.println("testGetFileMetadata");
		FileMetaData fileMetaData = expect().
                statusCode(HttpStatus.OK.value()).
                when().
                get("http://localhost:8080/fileoperations/fileMetadata?fileId=test.pdf").
                as(FileMetaData.class);
		 Assert.assertNotNull(fileMetaData);
		 Assert.assertEquals(fileMetaData.getFileName(), "test.pdf");
	}
	
    
}
