// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SenderService.proto

package grpc;

public interface TransactionRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpc.TransactionRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string transactionId = 1;</code>
   * @return The transactionId.
   */
  java.lang.String getTransactionId();
  /**
   * <code>string transactionId = 1;</code>
   * @return The bytes for transactionId.
   */
  com.google.protobuf.ByteString
      getTransactionIdBytes();

  /**
   * <code>string accountId = 2;</code>
   * @return The accountId.
   */
  java.lang.String getAccountId();
  /**
   * <code>string accountId = 2;</code>
   * @return The bytes for accountId.
   */
  com.google.protobuf.ByteString
      getAccountIdBytes();

  /**
   * <code>string recipientId = 3;</code>
   * @return The recipientId.
   */
  java.lang.String getRecipientId();
  /**
   * <code>string recipientId = 3;</code>
   * @return The bytes for recipientId.
   */
  com.google.protobuf.ByteString
      getRecipientIdBytes();

  /**
   * <code>int64 debitAmount = 4;</code>
   * @return The debitAmount.
   */
  long getDebitAmount();
}
