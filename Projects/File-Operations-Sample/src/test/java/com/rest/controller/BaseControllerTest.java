package com.rest.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;
import com.rest.FileUploadRestApplication;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FileUploadRestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class BaseControllerTest {
    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        initData();
    }

    public void initData() {

    }
}
