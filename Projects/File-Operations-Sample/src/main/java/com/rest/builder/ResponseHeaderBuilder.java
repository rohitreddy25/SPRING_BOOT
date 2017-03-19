package com.rest.builder;

import org.springframework.http.HttpHeaders;


public class ResponseHeaderBuilder {
    private HttpHeaders httpHeaders;

    public static ResponseHeaderBuilder aResponseHeader() {
        return new ResponseHeaderBuilder();
    }

    private ResponseHeaderBuilder() {
        this.httpHeaders = new HttpHeaders();
    }

    public ResponseHeaderBuilder withSessionToken(String value) {
        this.httpHeaders.add("X-SL-SESSION-TOKEN", value);
        return this;
    }

    public HttpHeaders build() {
        return this.httpHeaders;
    }
}
