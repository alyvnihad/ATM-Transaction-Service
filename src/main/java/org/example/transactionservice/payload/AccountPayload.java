package org.example.transactionservice.payload;

import lombok.Data;

@Data
public class AccountPayload {
    private Long accountId;
    private Long cardNumber;
    private String type;
    private Double amount;
    private Long atmId;
}
