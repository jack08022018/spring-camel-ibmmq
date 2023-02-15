package com.orches.workflow;

import com.orches.activities.TransferActivities;
import com.orches.enumerator.TaskQueue;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class HelloWorkflowImpl implements HelloWorkflow {

    private final ActivityOptions options = ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(10))
                    .setTaskQueue(TaskQueue.HELLO.name())
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build();

    private final TransferActivities transferActivities = Workflow.newActivityStub(TransferActivities.class, options);

    @Override
    public void hello() {
        log.info("hello:");
        transferActivities.deduct();
        transferActivities.refund();
    }
}
