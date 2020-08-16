package com.app.manager.model.midware_model.pojo;

import com.app.manager.entity.User;
import com.app.manager.model.HelperMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class OauthUtils {
    public Optional<User> getPojoInfo(String uri, String token, String pojoId){
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        var entity = new HttpEntity<>("", headers);

        if(pojoId.toLowerCase().equals("google")){
            return pojoGoogle(uri, entity);
        }else if(pojoId.toLowerCase().equals("github")) {
            return pojoGithub(uri, entity);
        }else {
            return Optional.empty();
        }
    }

    private Optional<User> pojoGoogle(String uri, HttpEntity<String> entity){
        var restTemplate = new RestTemplate();
        var response = restTemplate
                .exchange(uri, HttpMethod.GET, entity, GooglePojo.class);
        var userAttributes = response.getBody();

        var user = new User();
        if(userAttributes == null || userAttributes.getEmail() == null
            || userAttributes.getEmail().isEmpty() || userAttributes.getEmail().isBlank()
            || !userAttributes.getEmail_verified()) return Optional.empty();
        user.setUsername(userAttributes.getName() != null
                && !userAttributes.getName().isEmpty()
                && !userAttributes.getName().isBlank()?
                userAttributes.getName().replaceAll("\\s","")
                : userAttributes.getEmail());
        user.setEmail(HelperMethod.removeDotEmail(userAttributes.getEmail()));

        return Optional.of(user);
    }

    private Optional<User> pojoGithub(String uri, HttpEntity<String> entity){
        var restTemplate = new RestTemplate();
        var response = restTemplate
                .exchange(uri, HttpMethod.GET, entity, GithubPojo.class);
        var userAttributes = response.getBody();
        var user = new User();
        if(userAttributes == null || userAttributes.getEmail() == null
                || userAttributes.getEmail().isEmpty()
                || userAttributes.getEmail().isBlank()) return Optional.empty();
        user.setUsername(userAttributes.getName() != null
                && !userAttributes.getName().isEmpty()
                && !userAttributes.getName().isBlank()?
                userAttributes.getName().replaceAll("\\s","")
                : userAttributes.getEmail());
        user.setEmail(userAttributes.getEmail());

        return Optional.of(user);
    }
}
