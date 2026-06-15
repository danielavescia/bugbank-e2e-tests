package com.bugbank.bugbank_selenium_tests.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String email;

    private String name;

    private String password;

    @Builder.Default
    private String accountNumber = null;

    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;
}