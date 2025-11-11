package org.example.transactionservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.transactionservice.dto.NotificationRequest;
import org.example.transactionservice.payload.AccountPayload;
import org.example.transactionservice.model.Transaction;
import org.example.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// Handles transaction logs and queries by transaction or account ID
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    @Value("${notification.service.url}")
    private String notificationUrl;

    // Save transaction log and send notification
    @Transactional
    public void transactionLog(AccountPayload accountPayload){
        // Save transaction in database
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountPayload.getAccountId());
        transaction.setCardNumber(accountPayload.getCardNumber());
        transaction.setType(accountPayload.getType());
        transaction.setAmount(accountPayload.getAmount());
        transaction.setAtmId(accountPayload.getAtmId());
        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);

        // Prepare and send notification email
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setTo(accountPayload.getEmail());
        notificationRequest.setAtmId(accountPayload.getAtmId());
        notificationRequest.setCardNumber(accountPayload.getCardNumber());
        notificationRequest.setType(accountPayload.getType());
        notificationRequest.setAmount(accountPayload.getAmount());
        notificationRequest.setDateTime(transaction.getCreatedAt());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accountPayload.getToken());

        HttpEntity<NotificationRequest> entity = new HttpEntity<>(notificationRequest,headers);

        // Send email with notification service called
        restTemplate.postForEntity(notificationUrl + "/email-log", entity, Void.class);

    }

    // Get transaction by ID
    public Transaction getTransactionId(Long id){
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found by transaction id"));

    }

    // Get all transactions for a specific account
    public List<Transaction> getAccountId(Long id){
        try {
            return transactionRepository.findByAccountId(id);
        }catch (Exception e){
            throw new RuntimeException("Not found by account id");
        }
    }

    // Report service request this return daily log show
    public List<Transaction> dailyLog(LocalDate date){
        return transactionRepository.dailyTransaction(date);
    }
}
