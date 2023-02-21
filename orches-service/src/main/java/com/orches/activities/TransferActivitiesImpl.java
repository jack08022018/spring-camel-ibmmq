package com.orches.activities;

import com.orches.adapter.TransferAdapter;
import com.orches.dto.CompletionDto;
import io.temporal.activity.Activity;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.client.ActivityCompletionClient;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TransferActivitiesImpl implements TransferActivities {
    private final TransferAdapter transferAdapter;
    private final ActivityCompletionClient completionClient;

    public TransferActivitiesImpl(TransferAdapter transferAdapter, ActivityCompletionClient completionClient) {
        this.transferAdapter = transferAdapter;
        this.completionClient = completionClient;
    }

    @Override
    public void deduct() {
        transferAdapter.deduct();
//        ActivityExecutionContext context = Activity.getExecutionContext();
//        byte[] taskToken = context.getTaskToken();
//        context.doNotCompleteOnReturn();
//        ForkJoinPool.commonPool().execute(() -> composeGreetingAsync(taskToken, "greeting!"));
    }

    @Override
    public void refund() {
        transferAdapter.refund();
    }

    @Override
    public String getData() {
        ActivityExecutionContext context = Activity.getExecutionContext();
        byte[] taskToken = context.getTaskToken();
        context.doNotCompleteOnReturn();
        System.out.println("ActivityId: " + context.getInfo().getActivityId());
        System.out.println("WorkflowId: " + context.getInfo().getWorkflowId());
//        var dto = CompletionDto.builder()
//                .activityId(context.getInfo().getActivityId())
//                .workflowId(context.getInfo().getWorkflowId())
//                .build();
//        ForkJoinPool.commonPool().execute(() -> getData(dto));
        return null;
    }
    private void composeGreetingAsync(byte[] taskToken) {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String str = new String(taskToken, StandardCharsets.UTF_8);
        System.out.println("taskToken: " + str);
        String info = transferAdapter.getInfo();
        completionClient.complete(taskToken, info);
    }

}
