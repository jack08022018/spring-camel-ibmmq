package com.orches.worker;

import com.orches.activities.GetInfoActivities;
import com.orches.activities.GetInfoActivitiesImpl;
import com.orches.activities.TransferActivities;
import com.orches.activities.TransferActivitiesImpl;
import com.orches.adapter.TransferAdapter;
import com.orches.enumerator.TaskQueue;
import com.orches.workflow.TransferMoneyWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerFactoryOptions;
import io.temporal.worker.WorkerOptions;
import io.temporal.worker.WorkflowImplementationOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferMoneyWorker {
    final WorkflowClient workflowClient;
    final WorkerFactoryOptions defaultWorkerFactoryOptions;
    final WorkerOptions defaultWorkerOptions;
    final WorkflowImplementationOptions defaultWorkflowImplementationOptions;
    final TransferAdapter transferAdapter;

    @PostConstruct
    public void createWorker() {
        log.info("Registering Transfer Money Worker..");
        var workerFactory = WorkerFactory.newInstance(workflowClient, defaultWorkerFactoryOptions);
        var worker = workerFactory.newWorker(TaskQueue.TRANSFER_MONEY.toString(), defaultWorkerOptions);

        var completionClient = workflowClient.newActivityCompletionClient();
        worker.registerWorkflowImplementationTypes(defaultWorkflowImplementationOptions, TransferMoneyWorkflowImpl.class);
        worker.registerActivitiesImplementations(
                new TransferActivitiesImpl(transferAdapter, completionClient),
                new GetInfoActivitiesImpl(transferAdapter));
        workerFactory.start();
        log.info("Registering Transfer Money Worker..");
    }
}
