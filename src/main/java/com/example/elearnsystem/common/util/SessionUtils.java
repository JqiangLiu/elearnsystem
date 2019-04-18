package com.example.elearnsystem.common.util;

import com.example.elearnsystem.authority.domain.Authority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {
    public static final String LOGIN_USER = "loginUser";

     public static void setUser(Authority emp, HttpSession session) {
         session.setAttribute(LOGIN_USER, emp);
     }

     public static Authority getUser(HttpSession session) {
         return (Authority)session.getAttribute(LOGIN_USER);
     }

     public static Authority getUser() {
         return (Authority)getsession().getAttribute(LOGIN_USER);
     }

     public static HttpSession getsession() {
                 return getRequest().getSession();
             }

             public static HttpServletRequest getRequest()
     {
               ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

               HttpServletRequest request = requestAttributes.getRequest();

               return request;
             }
}
