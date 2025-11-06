package org.example.transactionservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.transactionservice.payload.AccountPayload;
import org.example.transactionservice.model.Transaction;
import org.example.transactionservice.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/log")
    public void transactionLog(@RequestBody AccountPayload accountPayload){
        transactionService.transactionLog(accountPayload);
    }

    @GetMapping("/{transactionId}")
    public Transaction transactionId(@PathVariable Long transactionId){
        return transactionService.getTransactionId(transactionId);
    }

    @GetMapping("/accountId/{id}")
    public List<Transaction> accountId(@PathVariable Long id){
        return transactionService.getAccountId(id);
    }

    @PostMapping("/daily-log")
    public List<Transaction> dailyLog (LocalDate date){
        return transactionService.dailyLog(date);
    }
}
