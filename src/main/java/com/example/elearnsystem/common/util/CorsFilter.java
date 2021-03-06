package com.example.elearnsystem.common.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/2/16.
 */
@Configuration
public class CorsFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        System.out.println("设置跨域请求...");

        HttpServletResponse response = (HttpServletResponse) res;

        HttpServletRequest reqs = (HttpServletRequest) req;

        System.out.println(reqs.getHeader("Origin"));

        // Origin ->> http://localhost:8080，允许此URL进行访问资源
        // https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Access-Control-Allow-Origin?utm_source=mozilla&utm_medium=devtools-netmonitor&utm_campaign=default
        response.setHeader("Access-Control-Allow-Origin",reqs.getHeader("Origin")); // 这是必须的，下面都是可选项
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "86400");
//         application/x-www-form-urlencoded, multipart/form-data 或 text/plain
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
        chain.doFilter(req, res);
    }
    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}
