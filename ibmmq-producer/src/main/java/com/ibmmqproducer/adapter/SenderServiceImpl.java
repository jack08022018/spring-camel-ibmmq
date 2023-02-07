package com.ibmmqproducer.adapter;

import grpc.SenderServiceGrpc;
import grpc.TransactionRequest;
import grpc.TransactionResponse;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SenderServiceImpl implements SenderService {
    @GrpcClient("sender-service")
    private SenderServiceGrpc.SenderServiceBlockingStub senderServiceBlockingStub;

    @Override
    public TransactionResponse deduct(TransactionRequest request) {
        return senderServiceBlockingStub.deduct(request);
    }
}
