package com.adedolapo.assessment;

import com.adedolapo.assessment.dto.TransactionDto;
import com.adedolapo.assessment.service.TransactionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class TransactionServiceTests {

    TransactionService transactionService = new TransactionService();

    TransactionDto createTransaction = new TransactionDto();

    TransactionDto createTransaction2 = new TransactionDto();

    TransactionDto createTransaction3 = new TransactionDto();
    TransactionDto failWhenTimeExceeds30 = new TransactionDto();
    TransactionDto failWithWrongDateFormat = new TransactionDto();
    TransactionDto failWithFutureTransactionDate = new TransactionDto();



    public TransactionServiceTests() {
    }

    @Before
    public void setUp() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date date = new Date(System.currentTimeMillis());
        String formattedDate = sdf.format(date);
        createTransaction.setAmount("12.3456");
        createTransaction.setTimestamp(formattedDate);
        createTransaction2.setAmount("12.3456");
        createTransaction2.setTimestamp(formattedDate);
        createTransaction3.setAmount("12.3456");
        createTransaction3.setTimestamp(formattedDate);
        failWhenTimeExceeds30.setAmount("13.3456");
        failWhenTimeExceeds30.setTimestamp("2023-07-04T19:55:00.312Z");
        failWithWrongDateFormat.setAmount("13.3456");
        failWithWrongDateFormat.setTimestamp("2023-07-04");
        failWithFutureTransactionDate.setAmount("13.3456");
        failWithFutureTransactionDate.setTimestamp("2024-07-04T19:55:00.312Z");
    }

    @Test
    public void dontCreateUserWhenTimestampExceeds30Secs(){
        ResponseEntity response1 = transactionService.createTransaction(failWhenTimeExceeds30);
        assertEquals(HttpStatus.NO_CONTENT, response1.getStatusCode());
    }
    @Test
    public void createTransactionWhenTimestampWithin30Secs(){
        ResponseEntity response1 = transactionService.createTransaction(createTransaction);
        assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        ResponseEntity response2 = transactionService.createTransaction(createTransaction2);
        assertEquals(HttpStatus.CREATED, response2.getStatusCode());
        ResponseEntity response3 = transactionService.createTransaction(createTransaction3);
        assertEquals(HttpStatus.CREATED, response3.getStatusCode());
    }

    @Test
    public void failWhenTimeExceeds30(){
        ResponseEntity response1 = transactionService.createTransaction(failWhenTimeExceeds30);
        assertEquals(HttpStatus.NO_CONTENT, response1.getStatusCode());
    }

    @Test
    public void failWithWrongDateFormat(){
        ResponseEntity response1 = transactionService.createTransaction(failWithWrongDateFormat);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response1.getStatusCode());
    }

    @Test
    public void failWithFutureTransactionDate(){
        ResponseEntity response1 = transactionService.createTransaction(failWithWrongDateFormat);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response1.getStatusCode());
    }
}
