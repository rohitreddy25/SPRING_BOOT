package com.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rest.FileUploadRestApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FileUploadRestApplication.class)
public class FileUploadRestApplicationTest {
    @Test
    public void testContextLoads() throws Exception {
    }
}
