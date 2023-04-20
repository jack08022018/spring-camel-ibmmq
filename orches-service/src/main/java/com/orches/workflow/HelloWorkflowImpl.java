package com.orches.workflow;

import com.orches.activities.TransferActivities;
import com.orches.config.exceptions.NotRetryException;
import com.orches.enumerator.TaskQueue;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class HelloWorkflowImpl implements HelloWorkflow {

    ActivityOptions activityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .setRetryOptions(RetryOptions.newBuilder()
                    .setMaximumAttempts(3)
                    .setDoNotRetry("com.orches.config.exceptions.NotRetryException")
                    .build())
            .build();

    private final TransferActivities transferActivities = Workflow.newActivityStub(TransferActivities.class, activityOptions);

    @Override
    public void hello() throws NotRetryException {
        log.info("hello:");
        transferActivities.deduct();
//        try {
//            transferActivities.refund();
//        }catch (Exception e) {
//            log.error("xxx: " + e.getMessage());
//        }
    }
}
