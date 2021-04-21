package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.web.model.Oauth2Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class AuthorizeController extends HttpServlet {

    private final Logger log = Logger.getLogger(AuthorizeController.class.getName());

    /**
     * 让用户跳转到 GitHub
     * 这里不能加@ResponseBody，因为这里是要跳转而不是返回响应
     * 另外LoginController也不能用@RestController修饰
     *
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Oauth2Properties oauth2Properties = new Oauth2Properties();
        String url = oauth2Properties.getAuthorizeUrl() +
                "?client_id=" + oauth2Properties.getClientId() +
                "&redirect_uri=" + oauth2Properties.getRedirectUrl();
        log.info("授权url:" + url);
        response.sendRedirect(url);
    }
}
