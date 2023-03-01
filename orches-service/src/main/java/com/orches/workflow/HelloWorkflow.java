package com.orches.workflow;

import com.orches.config.exceptions.NotRetryException;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface HelloWorkflow {
    @WorkflowMethod
    void hello() throws NotRetryException;

}
