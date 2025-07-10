package com.scm.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken) {
            var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauthUser = (OAuth2User) authentication.getPrincipal();
            String username = "";

            if (clientId.equalsIgnoreCase("google")) {
                username = oauthUser.getAttribute("email").toString();

            } else if (clientId.equalsIgnoreCase("github")) {
                username = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                        : oauthUser.getAttribute("login").toString() + "@gmail.com";
            }
            return username;

        } else {
            return authentication.getName();
        }
    }

}
