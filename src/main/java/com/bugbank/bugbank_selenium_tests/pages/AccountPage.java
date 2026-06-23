package com.bugbank.bugbank_selenium_tests.pages;

import java.math.BigDecimal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.bugbank.bugbank_selenium_tests.model.User;

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
        
        try{
            if(!user.getName().equals(getUsername())){
                throw new IllegalStateException("Usuário esperado: " + user.getName() + ", mas encontrou: " + getUsername());
            }

            user.setAccountNumber(getAccountNumber(user));
            user.setBalance(getAccountBalance());

        }catch(IllegalStateException ex){
            throw ex;

        }catch(Exception e ){
            throw new RuntimeException("Erro ao capturar os dados da conta do usuário", e);
        }

        return user;
    }

    private void loadAccountPage(){
        driver.getCurrentUrl().equals(urlAccountPage);
    }

    private String getAccountNumber(User user){
       return waitVisible(accountNumber).getText();
    }

    private String getUsername(){
       return waitVisible(userName)
            .getText()
            .replace("Olá", "")
            .replace(",", "")
            .trim();
    }

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
}
