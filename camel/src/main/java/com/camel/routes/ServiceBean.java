package com.camel.routes;
import org.springframework.stereotype.Component;
@Component
public class ServiceBean {

    public User response(User user) {
        user.setName(user.getName().toUpperCase());
        return user;
    }
    

}