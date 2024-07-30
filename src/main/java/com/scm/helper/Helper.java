package com.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class Helper {
    public static String getEmailOfLoggedInUser(Authentication authentication){
        // Check if the authentication is an instance of OAuth2AuthenticationToken
        if (authentication instanceof OAuth2AuthenticationToken) {
            var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            // Get the principal as DefaultOAuth2User
            DefaultOAuth2User oAuthUser = (DefaultOAuth2User) authentication.getPrincipal();
            String userName = "";

            if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
                // Get the email attribute from the OAuth2User
                userName = oAuthUser.getAttribute("email").toString();
                System.out.println("Getting data from Google");
            } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
                System.out.println("Getting data from Github");
                // Check for email attribute; if not present, fallback to login attribute
                userName = oAuthUser.getAttribute("email") != null ? 
                           oAuthUser.getAttribute("email").toString() : 
                           oAuthUser.getAttribute("login").toString() + "@gmail.com";
            }
            return userName;
        } else {
            // For standard authentication, get the name (email) from the authentication object
            System.out.println("Getting data from database");
            return authentication.getName();
        }

    }


    public static String getLinkForEmailVeriFiaction(String emailToken){
        String link="http://localhost:1001/auth/verify-email?token=" + emailToken;

        return link;
    }
}



