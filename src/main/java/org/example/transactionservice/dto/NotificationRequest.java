package org.example.transactionservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationRequest {
    private String to;
    private Long atmId;
    private Long cardNumber;
    private String type;
    private Double amount;
    private LocalDateTime dateTime;
}
