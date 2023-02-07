package grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: SenderService.proto")
public final class SenderServiceGrpc {

  private SenderServiceGrpc() {}

  public static final String SERVICE_NAME = "grpc.SenderService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.TransactionRequest,
      grpc.TransactionResponse> getDeductMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deduct",
      requestType = grpc.TransactionRequest.class,
      responseType = grpc.TransactionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.TransactionRequest,
      grpc.TransactionResponse> getDeductMethod() {
    io.grpc.MethodDescriptor<grpc.TransactionRequest, grpc.TransactionResponse> getDeductMethod;
    if ((getDeductMethod = SenderServiceGrpc.getDeductMethod) == null) {
      synchronized (SenderServiceGrpc.class) {
        if ((getDeductMethod = SenderServiceGrpc.getDeductMethod) == null) {
          SenderServiceGrpc.getDeductMethod = getDeductMethod =
              io.grpc.MethodDescriptor.<grpc.TransactionRequest, grpc.TransactionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "deduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.TransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.TransactionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SenderServiceMethodDescriptorSupplier("deduct"))
              .build();
        }
      }
    }
    return getDeductMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.TransactionRequest,
      grpc.TransactionResponse> getRefundMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "refund",
      requestType = grpc.TransactionRequest.class,
      responseType = grpc.TransactionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.TransactionRequest,
      grpc.TransactionResponse> getRefundMethod() {
    io.grpc.MethodDescriptor<grpc.TransactionRequest, grpc.TransactionResponse> getRefundMethod;
    if ((getRefundMethod = SenderServiceGrpc.getRefundMethod) == null) {
      synchronized (SenderServiceGrpc.class) {
        if ((getRefundMethod = SenderServiceGrpc.getRefundMethod) == null) {
          SenderServiceGrpc.getRefundMethod = getRefundMethod =
              io.grpc.MethodDescriptor.<grpc.TransactionRequest, grpc.TransactionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "refund"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.TransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.TransactionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SenderServiceMethodDescriptorSupplier("refund"))
              .build();
        }
      }
    }
    return getRefundMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SenderServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SenderServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SenderServiceStub>() {
        @java.lang.Override
        public SenderServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SenderServiceStub(channel, callOptions);
        }
      };
    return SenderServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SenderServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SenderServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SenderServiceBlockingStub>() {
        @java.lang.Override
        public SenderServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SenderServiceBlockingStub(channel, callOptions);
        }
      };
    return SenderServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SenderServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SenderServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SenderServiceFutureStub>() {
        @java.lang.Override
        public SenderServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SenderServiceFutureStub(channel, callOptions);
        }
      };
    return SenderServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class SenderServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void deduct(grpc.TransactionRequest request,
        io.grpc.stub.StreamObserver<grpc.TransactionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeductMethod(), responseObserver);
    }

    /**
     */
    public void refund(grpc.TransactionRequest request,
        io.grpc.stub.StreamObserver<grpc.TransactionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRefundMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getDeductMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                grpc.TransactionRequest,
                grpc.TransactionResponse>(
                  this, METHODID_DEDUCT)))
          .addMethod(
            getRefundMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                grpc.TransactionRequest,
                grpc.TransactionResponse>(
                  this, METHODID_REFUND)))
          .build();
    }
  }

  /**
   */
  public static final class SenderServiceStub extends io.grpc.stub.AbstractAsyncStub<SenderServiceStub> {
    private SenderServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SenderServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SenderServiceStub(channel, callOptions);
    }

    /**
     */
    public void deduct(grpc.TransactionRequest request,
        io.grpc.stub.StreamObserver<grpc.TransactionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeductMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void refund(grpc.TransactionRequest request,
        io.grpc.stub.StreamObserver<grpc.TransactionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRefundMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SenderServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<SenderServiceBlockingStub> {
    private SenderServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SenderServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SenderServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.TransactionResponse deduct(grpc.TransactionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeductMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.TransactionResponse refund(grpc.TransactionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRefundMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SenderServiceFutureStub extends io.grpc.stub.AbstractFutureStub<SenderServiceFutureStub> {
    private SenderServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SenderServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SenderServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.TransactionResponse> deduct(
        grpc.TransactionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeductMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.TransactionResponse> refund(
        grpc.TransactionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRefundMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_DEDUCT = 0;
  private static final int METHODID_REFUND = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SenderServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SenderServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DEDUCT:
          serviceImpl.deduct((grpc.TransactionRequest) request,
              (io.grpc.stub.StreamObserver<grpc.TransactionResponse>) responseObserver);
          break;
        case METHODID_REFUND:
          serviceImpl.refund((grpc.TransactionRequest) request,
              (io.grpc.stub.StreamObserver<grpc.TransactionResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SenderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SenderServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.SenderServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SenderService");
    }
  }

  private static final class SenderServiceFileDescriptorSupplier
      extends SenderServiceBaseDescriptorSupplier {
    SenderServiceFileDescriptorSupplier() {}
  }

  private static final class SenderServiceMethodDescriptorSupplier
      extends SenderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SenderServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SenderServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SenderServiceFileDescriptorSupplier())
              .addMethod(getDeductMethod())
              .addMethod(getRefundMethod())
              .build();
        }
      }
    }
    return result;
  }
}
