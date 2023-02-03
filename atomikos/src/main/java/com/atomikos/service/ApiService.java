package com.atomikos.service;

public interface ApiService {
    <T> T handleTransactional();
    <T> T findAllRental();
    <T> T getDataTransaction();
}
