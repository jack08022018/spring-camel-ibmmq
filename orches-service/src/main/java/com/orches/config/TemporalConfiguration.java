package com.orches.config;

import com.orches.activities.TransferActivities;
import com.orches.activities.TransferActivitiesImpl;
import com.orches.worker.*;
import io.temporal.activity.ActivityOptions;
import io.temporal.activity.LocalActivityOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactoryOptions;
import io.temporal.worker.WorkerOptions;
import io.temporal.worker.WorkflowImplementationOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class TemporalConfiguration {
    final TemporalProperties temporalProperties;

    @Bean
    public WorkflowClient workflowClient() {
        var options = WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(temporalProperties.getServer())
                        .build();
        var service = WorkflowServiceStubs.newServiceStubs(options);
        return WorkflowClient.newInstance(service);

//        var service = WorkflowServiceStubs.newInstance(
//                WorkflowServiceStubsOptions.newBuilder().setTarget("127.0.0.1:7233").build());
//        var option = WorkflowClientOptions.newBuilder()
//                .setNamespace("default")
//                .build();
//        return WorkflowClient.newInstance(service, option);
    }

    @Bean
    public WorkerFactoryOptions defaultWorkerFactoryOptions() {
        return WorkerFactoryOptions.newBuilder()
                .setMaxWorkflowThreadCount(600)
//                .setWorkflowHostLocalPollThreadCount(5)
                .build();
    }

    @Bean
    public WorkerOptions defaultWorkerOptions() {
        return WorkerOptions.newBuilder()
//                .setMaxConcurrentActivityTaskPollers(25)
//                .setMaxConcurrentWorkflowTaskPollers(10)
//                .setMaxConcurrentActivityExecutionSize()
//                .setMaxConcurrentWorkflowTaskExecutionSize()
//                .setMaxConcurrentLocalActivityExecutionSize()
//                .setMaxWorkerActivitiesPerSecond()
//                .setMaxTaskQueueActivitiesPerSecond()
                .build();
    }

    @Bean
    public WorkflowImplementationOptions defaultWorkflowImplementationOptions() {
        var defaultActivityOptions = ActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofSeconds(15))
                .setRetryOptions(RetryOptions.newBuilder()
                        .setMaximumAttempts(50)
                        .build())
                .build();

        var defaultLocalActivityOptions = LocalActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofSeconds(15))
                .setLocalRetryThreshold(Duration.ofSeconds(15))
                .setRetryOptions(RetryOptions.newBuilder()
                        .setBackoffCoefficient(1)
                        .setInitialInterval(Duration.ofMillis(10))
                        .setMaximumAttempts(9999)
                        .build())
                .build();
        return WorkflowImplementationOptions.newBuilder()
                .setFailWorkflowExceptionTypes(NullPointerException.class)
//                .setActivityOptions()
                .setDefaultActivityOptions(defaultActivityOptions)
                .setDefaultLocalActivityOptions(defaultLocalActivityOptions)
                .build();
    }

}
