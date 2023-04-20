package com.orches.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TransferAdapterImpl implements TransferAdapter {

    @Override
    public void deduct() {
        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (Exception e){}
        System.out.println("deduct!");
    }

    @Override
    public void refund() {
        System.out.println("refund!");
    }

    @Override
    public String getInfo() {
        return "Greeting!";
    }

}
