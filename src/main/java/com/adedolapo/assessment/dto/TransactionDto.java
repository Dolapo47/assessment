package com.adedolapo.assessment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    @NotNull(message = "Amount should not be null")
    @NotBlank(message = "Amount should not be blank")
    private String amount;
    @NotNull(message = "Timestamp should not be blank")
    private String timestamp;
}
