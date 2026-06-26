package com.bugbank.bugbank_selenium_tests.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import com.bugbank.bugbank_selenium_tests.model.User;
import com.bugbank.bugbank_selenium_tests.pages.AccountPage;
import com.bugbank.bugbank_selenium_tests.pages.HomePage;
import com.bugbank.bugbank_selenium_tests.test.base.BaseTest;
import com.bugbank.bugbank_selenium_tests.utils.DataGenerator;
import io.qameta.allure.Step;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BugbankTest extends BaseTest{

    @Test
    @DisplayName("Criação de conta com saldo e verificação de dados")
    public void shouldCreateAccountWithBalanceAndVerifyDetails() {
        User user = DataGenerator.generateUser();
        
        createAccountWithBalance(user);

        AccountPage accountPage = new HomePage(driver).load().login(user);
        User actualUser = accountPage.findAndReturnUserAccountDetails(user);

        assertAccountDetails(actualUser, user);
    }

    @DisplayName("Transferência de R$ 100,00 entre contas e verificação de saldo")
    @Test
    public void shouldTransferBetweenAccountsAndVerifyBalance(){
        
        User sender = DataGenerator.generateUser();
        User receiver = DataGenerator.generateUser();
        String transferValue = "100.00";

        createAccountWithBalance(sender);
        createAccountWithBalance(receiver);

        fetchAccountDetails(receiver);

        AccountPage senderPage = new HomePage(driver).load().login(sender);
        senderPage.findAndReturnUserAccountDetails(sender);

        String successMessage = senderPage
                .navigateToTransfer()
                .fillTransferForm(receiver, transferValue)
                .getSucessMessage();

        assertTransferSuccess(successMessage);
        assertSenderBalanceAfterTransfer(sender, new BigDecimal(transferValue));
        assertReceiverBalanceAfterTransfer(receiver, new BigDecimal(transferValue));
    }

    @Step("Cria conta com saldo para {user.name}")
    private void createAccountWithBalance(User user){
        String modalText = new HomePage(driver)
                    .load()
                    .navigateToRegister()
                    .createAccountWithBalanceAndGetModalText(user);
        assertAccountCreatedSuccessfully(modalText, user);
    }

    @Step("Captura dados da conta de {user.name}")
    private void fetchAccountDetails(User user) {
        AccountPage page = new HomePage(driver).load().login(user);
        page.findAndReturnUserAccountDetails(user);
    }
    
    @Step("Verificar criação da conta de {user.name}")
    private void assertAccountCreatedSuccessfully(String expectedModalText, User user){
        assertThat(expectedModalText)
            .as("Modal deve aparecer após criação da conta com sucesso do %s", user.getName())
            .contains("foi criada com sucesso");
    }

    @Step("Verifica login e captura de dados do usuário {user.name}")
    private void assertAccountDetails(User expectedUser, User actualUser){

        assertThat(actualUser.getAccountNumber())
            .as("Número da conta não deve ser nulo")
            .isNotNull()
            .isNotEmpty();

        assertThat(actualUser.getDigit())
            .as("Digito da conta não deve ser nulo")
            .isNotNull()
            .isNotEmpty();
        
        assertThat(actualUser.getBalance())
                .as("Era esperado balanço igual a 1000.00, mas foi encontrado: %s", actualUser.getBalance())
                .isEqualByComparingTo(new BigDecimal("1000.00"));
    }

    @Step("Verifica mensagem de sucesso da transferência")
    private void assertTransferSuccess(String message) {
        assertThat(message)
                .as("Modal deve confirmar transferência realizada")
                .contains("realizada com sucesso");
    }

    @Step("Verifica saldo do sender após transferência de {transferValue}")
    private void assertSenderBalanceAfterTransfer(User sender, BigDecimal transferValue) {
        fetchAccountDetails(sender);
        BigDecimal expectedBalance = new BigDecimal("1000.00").subtract(transferValue);

        assertThat(sender.getBalance())
                .as("Saldo esperado após transferência: %s, encontrado: %s", expectedBalance, sender.getBalance())
                .isEqualByComparingTo(expectedBalance);
    }

    @Step("Verifica saldo do receiver após transferência de {transferValue}")
    private void assertReceiverBalanceAfterTransfer(User receiver, BigDecimal transferValue) {
        fetchAccountDetails(receiver);
        BigDecimal expectedBalance = new BigDecimal("1000.00").add(transferValue);

        assertThat(receiver.getBalance())
                .as("Saldo esperado após transferência: %s, encontrado: %s", expectedBalance, receiver.getBalance())
                .isEqualByComparingTo(expectedBalance);
    }
}