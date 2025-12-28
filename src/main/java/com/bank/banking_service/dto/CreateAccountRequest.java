package com.bank.banking_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreateAccountRequest {

    @NotBlank(message = "Account holder name is required")
    private String holderName;

    @Min(value = 0, message = "Initial balance cannot be negative")
    private double initialBalance;

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }
}

