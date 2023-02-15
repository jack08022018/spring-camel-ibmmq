package com.orches.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface TransferActivities {
    @ActivityMethod
    void deduct();

    @ActivityMethod
    void refund();
}
