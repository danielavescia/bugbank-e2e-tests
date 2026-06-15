package com.bugbank.bugbank_selenium_tests.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.bugbank.bugbank_selenium_tests.model.User;
import com.bugbank.bugbank_selenium_tests.pages.HomePage;
import com.bugbank.bugbank_selenium_tests.pages.RegisterComponent;
import com.bugbank.bugbank_selenium_tests.test.base.BaseTest;
import com.bugbank.bugbank_selenium_tests.utils.DataGenerator;

public class BugbankTest extends BaseTest{
   
    private User sender;

    //private User receiver;
    
    @BeforeEach
    void setUpPage() {
        sender = User.builder()
            .name(DataGenerator.genereteName())
            .email(DataGenerator.generateEmail())
            .password(DataGenerator.generetePassword())
            .build();
    }

    @Test
    @DisplayName("Initial Test setup")
    public void firstTest() throws InterruptedException{
       RegisterComponent component  = new HomePage(driver)
                        .load()
                        .navigateToRegister()
                        .createAccountWithBalance(sender);
    
    }
}