package com.orches.workflow;

import com.orches.activities.TransferActivities;
import com.orches.config.exceptions.NotRetryException;
import com.orches.enumerator.TaskQueue;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class TransferMoneyWorkflowImpl implements TransferMoneyWorkflow {

    private final TransferActivities transferActivities = Workflow.newActivityStub(TransferActivities.class);

    @Override
    public void transferMoney() throws NotRetryException {
//        log.info("Transfer money start: {}", Workflow.getInfo().getWorkflowId());
        log.info("Transfer money start:");
        transferActivities.deduct();
//        Promise<String> promise = Async.function(() -> transferActivities.getData());
//        String info = promise.get();
        String data = transferActivities.getData();
        System.out.println("aaa: " + data);
        transferActivities.refund();
    }
}
