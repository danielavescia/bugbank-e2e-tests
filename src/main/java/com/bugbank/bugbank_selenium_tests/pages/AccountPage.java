package com.bugbank.bugbank_selenium_tests.pages;

import java.math.BigDecimal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.bugbank.bugbank_selenium_tests.model.User;

import io.qameta.allure.Step;

public class AccountPage extends BasePage{

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    private final String  urlAccountPage = "https://bugbank.netlify.app/home";

    private final By accountNumber = By.id("textAccountNumber");

    private final By accountBalance = By.xpath("//*[@id=\"textBalance\"]/span");

    private final By transferButton = By.id("btn-TRANSFERÊNCIA");

    private final By statementButton = By.id("btn-EXTRATO");

    private final By userName = By.xpath("//p[@id='textName']");


    public User findAndReturnUserAccountDetails(User user){
        loadAccountPage();
        validateUser(user);
        setAccountData(user);
        return user;
    }

    @Step("Verifica que o usuário logou")
    private void loadAccountPage(){
        driver.getCurrentUrl().equals(urlAccountPage);
        waitVisible(userName);
    }

    @Step("Navega para Página de Transferência")
    private TransferPage navigateToTransfer(){
        click(transferButton);
        return new TransferPage(driver);
    }

    @Step("Verifica se o nome exibido na home equivale ao usuário")
    private void validateUser(User user){
        String actualName = getUsername();
        if(!user.getName().equals(actualName)){
            throw new IllegalStateException("Usuário esperado: " + user.getName() + ", mas encontrou: " + actualName);
        }
    }

    private void setAccountData(User user){
        user.setAccountNumber(getAccountNumber());
        user.setBalance(getAccountBalance());
    }

    @Step("Obtem número da conta")
    private String getAccountNumber(){
       return waitVisible(accountNumber)
            .getText()
            .replace("Conta digital: ", "")
            .trim();
    }

    @Step("Obtem saldo atual")
    private BigDecimal getAccountBalance(){
        return parseBalance (waitVisible(accountBalance).getText());
    }

    private BigDecimal parseBalance(String stringBalance){
        try {
            String parsed = stringBalance.replace("R$", "")
                                    .replace(".", "")
                                    .replace(",", ".")
                                    .trim();
            return new BigDecimal(parsed);
        } catch(NumberFormatException e){
             throw new RuntimeException("Erro ao converter saldo para BigDecimal. Valor recebido: '" + stringBalance + "'", e);
        }
    }

    private String getUsername(){
       return waitVisible(userName)
            .getText()
            .replace("Olá", "")
            .replace(",", "")
            .trim();
    }
}