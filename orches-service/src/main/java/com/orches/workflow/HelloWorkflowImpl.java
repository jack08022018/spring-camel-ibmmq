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

    private final TransferActivities transferActivities = Workflow.newActivityStub(TransferActivities.class);

    @Override
    public void hello() {
        log.info("hello:");
        transferActivities.deduct();
        transferActivities.refund();
    }
}
