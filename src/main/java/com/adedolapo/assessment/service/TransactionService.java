package com.adedolapo.assessment.service;

import com.adedolapo.assessment.dto.TransactionDto;
import com.adedolapo.assessment.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class TransactionService {

    // sets the timezone
    private final ZoneId timeZone = ZoneId.of("Africa/Lagos");
    List<Transaction> list = new ArrayList<>();
    // enables synchronization of writes into this shared resource
    List<Transaction> transactions = Collections.synchronizedList(list);
    public ResponseEntity createTransaction(TransactionDto transactionDto) {
        try{
            Transaction transaction = new Transaction();
            long now = new Date().getTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            // checks input date format conforms
            LocalDateTime dateTime = LocalDateTime.parse(transactionDto.getTimestamp(), formatter);
            long transactionTimeMilliseconds = dateTime.atZone(timeZone).toInstant().toEpochMilli();
            long difference = now - transactionTimeMilliseconds;
            long differenceInSeconds = difference/1000;
            // checks for future date
            if(differenceInSeconds < 0){
                return ResponseEntity.status(422).build();
            }
            // checks if dateTime is greater than 30 seconds
            if(differenceInSeconds > 30){
                return ResponseEntity.status(204).build();
            }
            // sets the amount and date and stored into the list
            transaction.setAmount(new BigDecimal(transactionDto.getAmount()));
            transaction.setLocalDateTime(dateTime);
            transactions.add(transaction);
        }catch(DateTimeParseException dateTimeParseException) {
            // catches date format parsing error
            return ResponseEntity.status(422).build();
        }
        // successful creation
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> result = new HashMap<>();
        long count = transactions.size();
        // returns 0 when no transaction has been created
        if(count == 0){
            result.put("sum", "0.00");
            result.put("avg", "0.00");
            result.put("max", "0.00");
            result.put("min", "0.00");
            result.put("count", 0);
            return ResponseEntity.status(404).body(result);
        }
        BigDecimal total = new BigDecimal("0.0");
        BigDecimal minAmount = BigDecimal.valueOf(Double.MAX_VALUE);
        BigDecimal maxAmount = BigDecimal.valueOf(Double.MIN_VALUE);
        BigDecimal average;
        // loop through transactions, compare and get min and max amount and add get the total amount
        for (Transaction transaction : transactions) {
            if(transaction.getAmount().compareTo(minAmount) < 0){
                minAmount = transaction.getAmount();
            }
            if(transaction.getAmount().compareTo(maxAmount) > 0) {
                maxAmount = transaction.getAmount();
            }
            total = total.add(transaction.getAmount());
        }
        // set the required output and add to the result map
        average = total.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        result.put("sum", String.valueOf(total.setScale(2, RoundingMode.HALF_UP)));
        result.put("avg", String.valueOf(average));
        result.put("max", String.valueOf(maxAmount.setScale(2, RoundingMode.HALF_UP)));
        result.put("min", String.valueOf(minAmount.setScale(2, RoundingMode.HALF_UP)));
        result.put("count", count);
        return ResponseEntity.status(200).body(result);
    }

    public ResponseEntity deleteTransaction() {
        // deletes all values from the transactions list
        transactions.clear();
        return ResponseEntity.status(204).build();
    }
}
