package com.orches.activities;

import com.orches.adapter.TransferAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferActivitiesImpl implements TransferActivities {
    private final TransferAdapter transferAdapter;

    public TransferActivitiesImpl(TransferAdapter transferAdapter) {
        this.transferAdapter = transferAdapter;
    }

    @Override
    public void deduct() {
        transferAdapter.deduct();
    }

    @Override
    public void refund() {
        transferAdapter.refund();
    }

}
