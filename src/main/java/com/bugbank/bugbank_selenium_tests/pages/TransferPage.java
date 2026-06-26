package com.bugbank.bugbank_selenium_tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.bugbank.bugbank_selenium_tests.model.User;

public class TransferPage extends BasePage{

    public TransferPage(WebDriver driver) {
        super(driver);
    }

    private final By accountNumberField = By.name("accountNumber");

    private final By accountDigitField = By.name("digit");

    private final By transferValue = By.name("transferValue");

    private final By transferDescription = By.name("description");

    private final By transferButton = By.xpath("//button[normalize-space()='Transferir agora']");

    private void fillaccountNumberField(User user){
        type(accountNumberField, user.getAccountNumber());
    }

    private void fillaccountDigitField(User user){
        type(accountDigitField, user.getAccountNumber());
    }

}
