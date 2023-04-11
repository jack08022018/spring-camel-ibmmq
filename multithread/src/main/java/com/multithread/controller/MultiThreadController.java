package com.multithread.controller;


import com.multithread.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class MultiThreadController {
    final ApiService apiService;

    @GetMapping(value = "/getDataAsync")
    public ModelMap getDataAsync() throws Exception {
//        return apiService.getDataAsyncNoThreadPool();
//        return apiService.getDataAsyncWithThreadPool();
//        return apiService.getDataAsyncAllOf();
        return apiService.getDataAsyncAnnotation();
    }

}
