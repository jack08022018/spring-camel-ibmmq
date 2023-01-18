package com.camel.routes;
import com.camel.dto.User;
import org.springframework.stereotype.Component;

@Component
public class ServiceBean {

    public User getUser(User user) {
        user.name = user.name.toUpperCase();
        return user;
    }

    public String toUpper(User user) {
        return user.name.toUpperCase();
    }

//    public User response(User user) {
////        user.name = user.name.toUpperCase();
//        return user;
//    }
}