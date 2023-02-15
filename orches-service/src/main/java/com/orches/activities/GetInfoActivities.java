package com.orches.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface GetInfoActivities {
    @ActivityMethod
    String getInfo();
}
