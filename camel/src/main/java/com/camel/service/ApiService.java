package com.camel.service;

public interface ApiService {
    <T> T handleTransactional();
//    void importExcel(MultipartFile file) throws Exception;
    void importExcel() throws Exception;
    <T> T findAllRental();
}
