package org.geektimes.projects.user.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.geektimes.projects.user.web.model.Oauth2Properties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;


public class OAuth2CallbackController extends HttpServlet {
    private final Logger log = Logger.getLogger(OAuth2CallbackController.class.getName());
    private final Oauth2Properties oauth2Properties = new Oauth2Properties();

    /**
     * 回调接口，用户同意授权后，GitHub会重定向到此路径
     *
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        log.info("code=" + code);
        // code换token
        String accessToken = null;
        try {
            accessToken = getAccessToken(code);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        // token换userInfo
        try {
            String userInfo = getUserInfo(accessToken);
            request.setAttribute("userInfo",userInfo);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/githubUserInfo.jsp");
        requestDispatcher.forward(request, response);
        //        response.sendRedirect("http://localhost:8080/hello/world");
    }

    private String getAccessToken(String code) throws RestClientException {
        String url = oauth2Properties.getAccessTokenUrl() +
                "?client_id=" + oauth2Properties.getClientId() +
                "&client_secret=" + oauth2Properties.getClientSecret() +
                "&code=" + code +
                "&grant_type=authorization_code";
        log.info("getAccessToken url:" + url);
        // 构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        // 指定响应返回json格式
        requestHeaders.add("accept", "application/json");
        // 构建请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        // post 请求方式
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        String responseStr = response.getBody();
        log.info("responseStr=" + responseStr);

        // 解析响应json字符串
        JSONObject jsonObject = JSONObject.parseObject(responseStr);
        String accessToken = jsonObject.getString("access_token");
        log.info("accessToken=" + accessToken);
        return accessToken;
    }

    private String getUserInfo(String accessToken) throws RestClientException {
        String url = oauth2Properties.getUserInfoUrl();
        log.info("getUserInfo url:" + url);
        // 构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        // 指定响应返回json格式
        requestHeaders.add("accept", "application/json");
        // AccessToken放在请求头中
        requestHeaders.add("Authorization", "token " + accessToken);
        // 构建请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        // get请求方式
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String userInfo = response.getBody();
        log.info("userInfo=" + userInfo);
        return userInfo;
    }

}
