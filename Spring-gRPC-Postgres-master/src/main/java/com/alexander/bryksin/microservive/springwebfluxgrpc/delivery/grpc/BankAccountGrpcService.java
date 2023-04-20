package com.alexander.bryksin.microservive.springwebfluxgrpc.delivery.grpc;

import com.alexander.bryksin.microservive.springwebfluxgrpc.interceptors.LogGrpcInterceptor;
import com.alexander.bryksin.microservive.springwebfluxgrpc.mappers.BankAccountMapper;
import com.alexander.bryksin.microservive.springwebfluxgrpc.services.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;


@GrpcService(interceptors = {LogGrpcInterceptor.class})
@Slf4j
@RequiredArgsConstructor
public class BankAccountGrpcService extends com.grpc.bankService.ReactorBankAccountServiceGrpc.BankAccountServiceImplBase {

    private final BankAccountService bankAccountService;
    private final Tracer tracer;
    private final Validator validator;
    private static final Long TIMEOUT_MILLIS = 5000L;

    @Override
    @NewSpan
    public Mono<com.grpc.bankService.CreateBankAccountResponse> createBankAccount(Mono<com.grpc.bankService.CreateBankAccountRequest> request) {
        return request.flatMap(req -> bankAccountService.createBankAccount(validate(BankAccountMapper.of(req)))
                        .doOnNext(v -> spanTag("req", req.toString())))
                .map(bankAccount -> com.grpc.bankService.CreateBankAccountResponse.newBuilder().setBankAccount(BankAccountMapper.toGrpc(bankAccount)).build())
                .timeout(Duration.ofMillis(TIMEOUT_MILLIS))
                .doOnError(this::spanError)
                .doOnSuccess(result -> log.info("created account: {}", result.getBankAccount()));
    }

    @Override
    @NewSpan
    public Mono<com.grpc.bankService.GetBankAccountByIdResponse> getBankAccountById(Mono<com.grpc.bankService.GetBankAccountByIdRequest> request) {
        return request.flatMap(req -> bankAccountService.getBankAccountById(UUID.fromString(req.getId()))
                        .doOnNext(v -> spanTag("id", req.getId()))
                        .doOnSuccess(bankAccount -> spanTag("bankAccount", bankAccount.toString()))
                        .map(bankAccount -> com.grpc.bankService.GetBankAccountByIdResponse.newBuilder().setBankAccount(BankAccountMapper.toGrpc(bankAccount)).build()))
                .timeout(Duration.ofMillis(TIMEOUT_MILLIS))
                .doOnError(this::spanError)
                .doOnSuccess(response -> log.info("bankAccount: {}", response.getBankAccount()));
    }


    @Override
    @NewSpan
    public Mono<com.grpc.bankService.DepositBalanceResponse> depositBalance(Mono<com.grpc.bankService.DepositBalanceRequest> request) {
        return request
                .flatMap(req -> bankAccountService.depositAmount(UUID.fromString(req.getId()), BigDecimal.valueOf(req.getBalance()))
                        .doOnEach(v -> spanTag("req", req.toString()))
                        .map(bankAccount -> com.grpc.bankService.DepositBalanceResponse.newBuilder().setBankAccount(BankAccountMapper.toGrpc(bankAccount)).build()))
                .timeout(Duration.ofMillis(TIMEOUT_MILLIS))
                .doOnError(this::spanError)
                .doOnSuccess(response -> log.info("bankAccount: {}", response.getBankAccount()));
    }

    @Override
    @NewSpan
    public Mono<com.grpc.bankService.WithdrawBalanceResponse> withdrawBalance(Mono<com.grpc.bankService.WithdrawBalanceRequest> request) {
        return request.flatMap(req -> bankAccountService.withdrawAmount(UUID.fromString(req.getId()), BigDecimal.valueOf(req.getBalance()))
                        .doOnNext(v -> spanTag("req", req.toString()))
                        .map(bankAccount -> com.grpc.bankService.WithdrawBalanceResponse.newBuilder().setBankAccount(BankAccountMapper.toGrpc(bankAccount)).build()))
                .timeout(Duration.ofMillis(TIMEOUT_MILLIS))
                .doOnError(this::spanError)
                .doOnSuccess(response -> log.info("bankAccount: {}", response.getBankAccount()));
    }

    @Override
    @NewSpan
    public Flux<com.grpc.bankService.GetAllByBalanceResponse> getAllByBalance(Mono<com.grpc.bankService.GetAllByBalanceRequest> request) {
        return request
                .flatMapMany(req -> bankAccountService.findBankAccountByBalanceBetween(BankAccountMapper.findByBalanceRequestDtoFromGrpc(req))
                        .doOnNext(v -> spanTag("req", req.toString()))
                        .map(bankAccount -> com.grpc.bankService.GetAllByBalanceResponse.newBuilder().setBankAccount(BankAccountMapper.toGrpc(bankAccount)).build()))
                .timeout(Duration.ofMillis(TIMEOUT_MILLIS))
                .doOnError(this::spanError)
                .doOnNext(response -> log.info("bankAccount: {}", response.getBankAccount()));
    }

    @Override
    @NewSpan
    public Mono<com.grpc.bankService.GetAllByBalanceWithPaginationResponse> getAllByBalanceWithPagination(Mono<com.grpc.bankService.GetAllByBalanceWithPaginationRequest> request) {
        return request.flatMap(req -> bankAccountService.findAllBankAccountsByBalance(BankAccountMapper.findByBalanceRequestDtoFromGrpc(req))
                        .doOnNext(v -> spanTag("req", req.toString()))
                        .map(BankAccountMapper::toPaginationGrpcResponse))
                .timeout(Duration.ofMillis(TIMEOUT_MILLIS))
                .doOnError(this::spanError)
                .doOnSuccess(response -> log.info("response: {}", response.toString()));
    }

    private <T> T validate(T data) {
        var errors = validator.validate(data);
        if (!errors.isEmpty()) throw new ConstraintViolationException(errors);
        return data;
    }

    private void spanTag(String key, String value) {
        var span = tracer.currentSpan();
        if (span != null) span.tag(key, value);
    }

    private void spanError(Throwable ex) {
        var span = tracer.currentSpan();
        if (span != null) span.error(ex);
    }
}
