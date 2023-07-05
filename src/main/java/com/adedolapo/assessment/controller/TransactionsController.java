package com.adedolapo.assessment.controller;

import com.adedolapo.assessment.dto.TransactionDto;
import com.adedolapo.assessment.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionsController {

    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("transaction")
    private ResponseEntity createTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        return transactionService.createTransaction(transactionDto);
    }

    @GetMapping("statistics")
    private ResponseEntity getStatistics() {
        return transactionService.getStatistics();
    }

    @DeleteMapping("transaction")
    private ResponseEntity deleteTransaction() {
        return transactionService.deleteTransaction();
    }
}
