package com.adedolapo.assessment;

import com.adedolapo.assessment.dto.TransactionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class TransactionControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    TransactionDto failWithBlankAmount = new TransactionDto();
    TransactionDto failWithNullAmount = new TransactionDto();
    TransactionDto createTransaction = new TransactionDto();
    TransactionDto createTransaction2 = new TransactionDto();
    TransactionDto createTransaction3 = new TransactionDto();
    TransactionDto failWhenTimeExceeds30 = new TransactionDto();
    TransactionDto failWithWrongDateFormat = new TransactionDto();
    TransactionDto failWithFutureTransactionDate = new TransactionDto();


    @Before
    public void setUp() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date date = new Date(System.currentTimeMillis());
        String formattedDate = sdf.format(date);
        failWithBlankAmount.setAmount("");
        failWithBlankAmount.setTimestamp(formattedDate);
        failWithNullAmount.setTimestamp(formattedDate);
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
    public void getStatisticsWhenNoTransactionCreated() throws Exception {
        ResultActions response = mockMvc.perform(get("/statistics")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testBlankAmount() throws Exception {
        ResultActions response = mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(failWithBlankAmount)));
        response.andExpect(MockMvcResultMatchers.status().is(400))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testNullAmount() throws Exception {
        ResultActions response = mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(failWithNullAmount)));
        response.andExpect(MockMvcResultMatchers.status().is(400))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void dontCreateUserWhenTimestampExceeds30Secs() throws Exception {
        ResultActions response = mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(failWhenTimeExceeds30)));
        response.andExpect(MockMvcResultMatchers.status().is(204))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void createTransactionWhenTimestampWithin30Secs() throws Exception {
        ResultActions response = mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createTransaction)));
        response.andExpect(MockMvcResultMatchers.status().is(201))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void createTransactionWhenTimestampWithin30Secs2() throws Exception {
        ResultActions response = mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createTransaction2)));
        response.andExpect(MockMvcResultMatchers.status().is(201))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void createTransactionWhenTimestampWithin30Secs3() throws Exception {
        ResultActions response = mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createTransaction3)));
        response.andExpect(MockMvcResultMatchers.status().is(201))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void failWhenTimeExceeds30() throws Exception {
        ResultActions response = mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(failWhenTimeExceeds30)));
        response.andExpect(MockMvcResultMatchers.status().is(204))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void failWithWrongDateFormat() throws Exception {
        ResultActions response = mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(failWithWrongDateFormat)));
        response.andExpect(MockMvcResultMatchers.status().is(422))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void failWithFutureTransactionDate() throws Exception {
        ResultActions response = mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(failWithFutureTransactionDate)));
        response.andExpect(MockMvcResultMatchers.status().is(422))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getStatistics() throws Exception {
        ResultActions response = mockMvc.perform(get("/statistics")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().is(404))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteTransactions() throws Exception {
        ResultActions response = mockMvc.perform(delete("/transaction")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().is(204))
                .andDo(MockMvcResultHandlers.print());
    }
}
