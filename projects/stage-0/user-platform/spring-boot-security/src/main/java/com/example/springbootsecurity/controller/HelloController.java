package com.example.springbootsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {
    @GetMapping("/hello")
    String login() {
        return "hello";
    }


    @ResponseBody
    @PostMapping("/api/gateway")
    Map apiGateway(@RequestBody(required = false) ApiGatewayRequest request) {
        System.out.println("ApiGatewayRequest:" + request);
        Map<String, Object> map = new HashMap();
        map.put("requestId","shenhao");
        map.put("responseCode","shenhao");
        map.put("responseMsg","17");
        Map<String, Object> data = new HashMap();
        data.put("name", "kevin");
        data.put("age", 18);
        map.put("data",data);
        return map;
    }

    public static class ApiGatewayRequest {
        private String app_id;
        private String sign;
        private String client_sign;
        private String request_content;
        private String api_code;
        private long timestamp;
        private String nonce;

        public ApiGatewayRequest(String app_id, String sign, String client_sign, String request_content, String api_code, long timestamp, String nonce) {
            this.app_id = app_id;
            this.sign = sign;
            this.client_sign = client_sign;
            this.request_content = request_content;
            this.api_code = api_code;
            this.timestamp = timestamp;
            this.nonce = nonce;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getClient_sign() {
            return client_sign;
        }

        public void setClient_sign(String client_sign) {
            this.client_sign = client_sign;
        }

        public String getRequest_content() {
            return request_content;
        }

        public void setRequest_content(String request_content) {
            this.request_content = request_content;
        }

        public String getApi_code() {
            return api_code;
        }

        public void setApi_code(String api_code) {
            this.api_code = api_code;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getNonce() {
            return nonce;
        }

        public void setNonce(String nonce) {
            this.nonce = nonce;
        }

        @Override
        public String toString() {
            return "ApiGatewayRequest{" +
                    "app_id='" + app_id + '\'' +
                    ", sign='" + sign + '\'' +
                    ", client_sign='" + client_sign + '\'' +
                    ", request_content='" + request_content + '\'' +
                    ", api_code='" + api_code + '\'' +
                    ", timestamp=" + timestamp +
                    ", nonce='" + nonce + '\'' +
                    '}';
        }
    }
}
