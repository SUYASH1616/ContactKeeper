package com.scm.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

    public static void removeMessage() {
        try{
            System.out.println("Removing messagr from session");
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attr != null) {
                HttpSession session = attr.getRequest().getSession(false); // false to not create a new session if one doesn't exist
                if (session != null) {
                    session.removeAttribute("message"); // Assuming the message attribute is named "message"
                }
            }
        }catch(Exception e){
            System.out.println("Error in session helper"+e);
            e.printStackTrace();
        }
        
    }
}
