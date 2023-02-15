package com.orches.activities;

import com.orches.adapter.TransferAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetInfoActivitiesImpl implements GetInfoActivities {
    private final TransferAdapter transferAdapter;

    public GetInfoActivitiesImpl(TransferAdapter transferAdapter) {
        this.transferAdapter = transferAdapter;
    }

    @Override
    public String getInfo() {
        return "hello!";
    }
}
