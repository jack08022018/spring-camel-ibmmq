package com.orches.activities;

import com.orches.config.exceptions.NotRetryException;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface TransferActivities {
    @ActivityMethod
    void deduct();

    @ActivityMethod
    void refund() throws NotRetryException;

    @ActivityMethod
    String getData();
}
