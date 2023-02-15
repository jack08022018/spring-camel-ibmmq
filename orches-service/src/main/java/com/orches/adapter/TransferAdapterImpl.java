package com.orches.adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferAdapterImpl implements TransferAdapter {

    @Override
    public void deduct() {
        System.out.println("deduct!");
    }

    @Override
    public void refund() {
        System.out.println("refund!");
    }

}
