package com.bugbank.bugbank_selenium_tests.test;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.bugbank.bugbank_selenium_tests.model.User;
import com.bugbank.bugbank_selenium_tests.pages.AccountPage;
import com.bugbank.bugbank_selenium_tests.pages.HomePage;
import com.bugbank.bugbank_selenium_tests.test.base.BaseTest;
import com.bugbank.bugbank_selenium_tests.utils.DataGenerator;

import io.qameta.allure.Step;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BugbankTest extends BaseTest{
    
    private static final Map<String,User> users = new LinkedHashMap<>();
   
    static {
        users.put("Sender", DataGenerator.generateUser());
        users.put("Receiver",  DataGenerator.generateUser());
        
    }
    
    static Stream<Arguments> provideUsers() {
        return users.entrySet().stream()
                .map(e -> Arguments.of(e.getKey(), e.getValue()));
    }
    
    @ParameterizedTest(name = "Criação de conta e login user:{0}")
    @MethodSource("provideUsers")
    @Order(1)
    public void createAndVerifyAccount(String userType, User user){
      
        String expectedModalText = new HomePage(driver)
                                    .load()
                                    .navigateToRegister()
                                    .createAccountWithBalanceAndGetModalText(user);
        verifyAccountCreated(expectedModalText, user);

        AccountPage page =  new HomePage(driver).load().login(user);
        User userExpected = page.findAndReturnUserAccountDetails(user);
        assertThat(userExpected.getName()).contains(user.getName());
    }

    
    @Step("Verificar criação da conta de {user.name}")
    private void verifyAccountCreated(String expectedModalText, User user){
        assertThat(expectedModalText)
            .as("Modal deve aparecer após criação da conta com sucesso do %s", user.getName())
            .contains("foi criada com sucesso");
    }
}