package com.bugbank.bugbank_selenium_tests.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bugbank.bugbank_selenium_tests.model.User;
import com.bugbank.bugbank_selenium_tests.pages.HomePage;
import com.bugbank.bugbank_selenium_tests.pages.RegisterComponent;
import com.bugbank.bugbank_selenium_tests.test.base.BaseTest;
import com.bugbank.bugbank_selenium_tests.utils.DataGenerator;

import io.qameta.allure.Step;

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
    @DisplayName("Criação Conta do Sender")
    public void createSenderAccount(){
       RegisterComponent registerComponent  = new HomePage(driver)
                        .load()
                        .navigateToRegister()
                        .createAccountWithBalance(sender);
       
        verifyAccountCreated(sender, registerComponent);
        registerComponent.closeModalButton();
    }

    @Step("Verificar criação da conta de {user.name}")
    private void verifyAccountCreated(User user, RegisterComponent registerComponent){
        assertThat(registerComponent.getModalText())
            .as("Modal deve aparecer após criação da conta com sucesso do %s", sender.getName())
            .contains("foi criada com sucesso");
    }
}