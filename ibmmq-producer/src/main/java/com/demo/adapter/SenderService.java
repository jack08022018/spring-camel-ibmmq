package com.demo.adapter;

import grpc.TransactionRequest;
import grpc.TransactionResponse;

public interface SenderService {
    TransactionResponse deduct(TransactionRequest request);
}
