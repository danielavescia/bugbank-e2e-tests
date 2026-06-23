package com.bugbank.bugbank_selenium_tests.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import com.bugbank.bugbank_selenium_tests.model.User;
import com.bugbank.bugbank_selenium_tests.pages.HomePage;
import com.bugbank.bugbank_selenium_tests.test.base.BaseTest;
import com.bugbank.bugbank_selenium_tests.utils.DataGenerator;
import io.qameta.allure.Step;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BugbankTest extends BaseTest{
    
    static Stream<Arguments> provideUsers() {
      return Stream.of(
        Arguments.of("Sender", User.builder()
            .name(DataGenerator.genereteName())
            .email(DataGenerator.generateEmail())
            .password(DataGenerator.generetePassword())
            .build()),

             Arguments.of("Receiver", User.builder()
            .name(DataGenerator.genereteName())
            .email(DataGenerator.generateEmail())
            .password(DataGenerator.generetePassword())
            .build())
      );
            
    }

    @ParameterizedTest(name = "Criação de conta {0}")
    @MethodSource("provideUsers")
    @Order(1)
    public void createAndVerifyAccount(String userType, User user){
      
        String expectedModalText = new HomePage(driver)
                                    .load()
                                    .navigateToRegister()
                                    .createAccountWithBalanceAndGetModalText(user);
        verifyAccountCreated(expectedModalText, user);
    }

    @Step("Verificar criação da conta de {user.name}")
    private void verifyAccountCreated(String expectedModalText, User user){
        assertThat(expectedModalText)
            .as("Modal deve aparecer após criação da conta com sucesso do %s", user.getName())
            .contains("foi criada com sucesso");
    }
}