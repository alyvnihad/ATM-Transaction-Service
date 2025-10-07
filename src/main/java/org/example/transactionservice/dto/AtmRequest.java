package org.example.transactionservice.dto;

import lombok.Data;

@Data
public class AtmRequest {
    private Long accountId;
    private Long cardNumber;
    private String type;
    private Double amount;
    private Long atmId;
}
