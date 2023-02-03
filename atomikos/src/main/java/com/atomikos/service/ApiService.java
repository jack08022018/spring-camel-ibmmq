package com.atomikos.service;

import com.atomikos.dto.User;

public interface ApiService {
    <T> T handleTransactional(User user);
    <T> T findAllRental();
    <T> T getActor();
}
