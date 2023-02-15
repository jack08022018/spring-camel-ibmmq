package com.orches.workflow;

import com.orches.activities.TransferActivities;
import com.orches.enumerator.TaskQueue;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class TransferMoneyWorkflowImpl implements TransferMoneyWorkflow {

    private final ActivityOptions options = ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(10))
                    .setTaskQueue(TaskQueue.TRANSFER_MONEY.name())
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build();

    private final TransferActivities transferActivities = Workflow.newActivityStub(TransferActivities.class, options);

    @Override
    public void transferMoney() {
//        log.info("Transfer money start: {}", Workflow.getInfo().getWorkflowId());
        log.info("Transfer money start:");
        transferActivities.deduct();
        transferActivities.refund();
    }
}
