package org.example.transactionservice.service;

import lombok.RequiredArgsConstructor;
import org.example.transactionservice.dto.AtmRequest;
import org.example.transactionservice.model.Transaction;
import org.example.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public void transactionLog(AtmRequest atmRequest){
        Transaction transaction = new Transaction();
        transaction.setAccountId(atmRequest.getAccountId());
        transaction.setCardNumber(atmRequest.getCardNumber());
        transaction.setType(atmRequest.getType());
        transaction.setAmount(atmRequest.getAmount());
        transaction.setAtmId(atmRequest.getAtmId());
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
