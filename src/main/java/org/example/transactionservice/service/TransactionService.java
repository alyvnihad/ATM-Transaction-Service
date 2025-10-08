package org.example.transactionservice.service;

import lombok.RequiredArgsConstructor;
import org.example.transactionservice.payload.AccountPayload;
import org.example.transactionservice.model.Transaction;
import org.example.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public void transactionLog(AccountPayload accountPayload){
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountPayload.getAccountId());
        transaction.setCardNumber(accountPayload.getCardNumber());
        transaction.setType(accountPayload.getType());
        transaction.setAmount(accountPayload.getAmount());
        transaction.setAtmId(accountPayload.getAtmId());
        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    public Transaction getTransactionId(Long id){
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found by transaction id"));

    }

    public List<Transaction> getAccountId(Long id){
        try {
            return transactionRepository.findByAccountId(id);
        }catch (Exception e){
            throw new RuntimeException("Not found by account id");
        }
    }
}
