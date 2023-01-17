package com.camel.routes;
import org.springframework.stereotype.Component;
@Component
public class ServiceBean {

    public User response(User user) {
        user.name = user.name.toUpperCase();
        return user;
    }
    

}