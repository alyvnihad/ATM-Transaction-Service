package org.example.transactionservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.transactionservice.dto.AtmRequest;
import org.example.transactionservice.model.Transaction;
import org.example.transactionservice.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/log")
    public void transactionLog(@RequestBody AtmRequest atmRequest){
        transactionService.transactionLog(atmRequest);
    }

    @GetMapping("/{transactionId}")
    public Transaction transactionId(@PathVariable Long transactionId){
        return transactionService.getTransactionId(transactionId);
    }

    @GetMapping("/accountId/{id}")
    public List<Transaction> accountId(@PathVariable Long id){
        return transactionService.getAccountId(id);
    }
}
