package com.bugbank.bugbank_selenium_tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.bugbank.bugbank_selenium_tests.model.User;
import io.qameta.allure.Step;

public class TransferPage extends BasePage{

    public TransferPage(WebDriver driver) {
        super(driver);
    }

    private final String  urlTransferPage = "https://bugbank.netlify.app/transfer";

    private final By accountNumberField = By.name("accountNumber");

    private final By accountDigitField = By.name("digit");

    private final By transferValueField = By.name("transferValue");

    private final By transferDescriptionField = By.name("description");

    private final By transferButton = By.xpath("//button[normalize-space()='Transferir agora']");

    private final By successModalText = By.id("modalText");

    private final By modalCloseButton = By.id("btnCloseModal");

    private final By backToAccountPageButton = By.id("btnBack");

    private void fillAccountNumberField(String accountNumber){
        type(accountNumberField, accountNumber);
    }

    private void fillAccountDigitField(String accountDigit){
        type(accountDigitField, accountDigit);
    }

    private void fillTransferValueField(String transferValue){
        type(transferValueField, transferValue);
    }

    private void fillDescriptionField(String description){
        type(transferDescriptionField, description);
    }

    @Step("Verifica que o usuário está na página de transferência")
    private TransferPage loadAccountPage(){
        driver.getCurrentUrl().equals(urlTransferPage);
        waitVisible(accountNumberField);
        return this;
    }

    @Step("Preenche formulário de transferência")
    public TransferPage fillTransferForm(User userToTransfer, String transferValue){
        fillAccountNumberField(userToTransfer.getAccountNumber());
        fillAccountDigitField(userToTransfer.getDigit());
        fillTransferValueField(transferValue);
        fillDescriptionField("almoço");
        click(transferButton);
        return this;
    }

    @Step("Obtém mensagem de sucesso do modal e fecha modal")
    public String getSucessMessage(){
        String actualMessage =  waitVisible(successModalText).getText();
        closeModal();
        return actualMessage;
    }

    private void closeModal(){
        click(modalCloseButton);
    }

    private void navigateToHomepage(){
        click(backToAccountPageButton);
    }
}