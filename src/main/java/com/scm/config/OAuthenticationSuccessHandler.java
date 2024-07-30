package com.scm.config;

import java.util.*;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.Entity.Providers;
import com.scm.Entity.User;
import com.scm.Repositories.UserRepo;
import com.scm.helper.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
    Logger logger=LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);
    @Autowired
    private UserRepo userRepo;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                logger.info("OAuthenticationSuccessHandler");

                var oAuth2AuthenticationToken=(OAuth2AuthenticationToken) authentication;
                String authorizedClientRegistrationId=oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
                logger.info(authorizedClientRegistrationId);

                DefaultOAuth2User oAuthUser=(DefaultOAuth2User) authentication.getPrincipal();
                User user1=new User();
                
                user1.setPassword("Pass@123");
                user1.setUserId(UUID.randomUUID().toString());
                user1.setProvider(Providers.GOOGLE);
                user1.setEnabled(true);
                user1.setEmailVerified(true);
                user1.setRoleList(List.of(AppConstants.ROLE_USER));
                
                if(authorizedClientRegistrationId.equalsIgnoreCase("google")){

                    user1.setEmail(oAuthUser.getAttribute("email").toString());
                    user1.setProfilePic(oAuthUser.getAttribute("picture").toString());
                    user1.setName(oAuthUser.getAttribute("name").toString());
                    user1.setProviderUserId(oAuthUser.getName());
                    user1.setAbout("Made by Google");
                    user1.setProvider(Providers.GOOGLE);

                }else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                    String email=oAuthUser.getAttribute("email") !=null ? oAuthUser.getAttribute("email").toString() : oAuthUser.getAttribute("login").toString()+"@gmail.com";
                    String name=oAuthUser.getAttribute("login").toString();
                    String picture=oAuthUser.getAttribute("avatar_url").toString();
                    

                    user1.setEmail(email);
                    user1.setProfilePic(picture);
                    user1.setName(name);
                    user1.setProviderUserId(oAuthUser.getName());
                    user1.setAbout("Made by GitHub");
                    user1.setProvider(Providers.GITHUB);
                }


                // DefaultOAuth2User user=(DefaultOAuth2User) authentication.getPrincipal();
                // logger.info(user.getName());
                // user.getAttributes().forEach((key,value)->{
                //     logger.info("{}=>{}",key,value);
                // });

                // String email=user.getAttribute("email").toString();
                // String name=user.getAttribute("name").toString();
                // String picture=user.getAttribute("picture").toString();
                
                // User user1=new User();
                // user1.setEmail(email);
                // user1.setName(name);
                // user1.setPassword("Pass@123");
                // user1.setProfilePic(picture);
                // user1.setUserId(UUID.randomUUID().toString());
                // user1.setProvider(Providers.GOOGLE);
                // user1.setEnabled(true);
                // user1.setEmailVerified(true);
                
                // user1.setRoleList(List.of(AppConstants.ROLE_USER));
                // user1.setAbout("Made by Google");
                
                // System.out.println(user1.getProvider());
                User user2=userRepo.findByEmail(user1.getEmail()).orElse(null);
                if(user2==null){
                    userRepo.save(user1);
                    logger.info("user saved "+user1.getEmail());
                }
                new DefaultRedirectStrategy().sendRedirect(request,response,"/home");
    }

}
