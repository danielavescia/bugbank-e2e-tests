package com.bugbank.bugbank_selenium_tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.bugbank.bugbank_selenium_tests.model.User;

import io.qameta.allure.Step;
public class RegisterComponent extends BasePage {

    private WebElement root;

    private final By form = By.xpath("//form[.//button[text()='Cadastrar']]");

    private final By emailField = By.name("email");

    private final By nameField = By.name("name");

    private final By passwordField = By.name("password");

    private final By passwordConfirmationField = By.name("passwordConfirmation");

    private final By toggleAddBalance =  By.xpath("//p[normalize-space()='Criar conta com saldo ?']/following::label[1]");

    private final By confirmButton = By.xpath("//button[normalize-space()='Cadastrar']");

    private final By closeModalButton = By.id("btnCloseModal");

    private final By modalText = By.id("modalText");

    public RegisterComponent(WebDriver driver){
        super(driver);
        this.root = wait.until(ExpectedConditions.visibilityOfElementLocated(form));
    }

    @Step("Cria conta com saldo para o usuário {user.name}")
    public String createAccountWithBalanceAndGetModalText(User user){
        fillForm(user);
        enableBalanceOption();
        clickRegister();

        String message = getSucessMessage();
        closeModal();
        return message;
    }
    
    @Step("Preenche formulário de cadastro")
    private RegisterComponent fillForm(User user){
        typeInside(nameField, user.getName());
        typeInside(emailField, user.getEmail());
        typeInside(passwordField, user.getPassword());
        typeInside(passwordConfirmationField, user.getPassword());
        return this;
    }

    @Step("Ativa opção de criar conta com saldo")
    private void enableBalanceOption(){
         click(toggleAddBalance);
    }

    @Step("Clica em Cadastrar")
    private void clickRegister(){
        click(confirmButton);
    }

    @Step("Obtém mensagem de sucesso do modal")
    private String getSucessMessage(){
        return waitVisible(modalText).getText();
    }

    @Step("Fecha o modal de sucesso")
    private  void closeModal(){
        click(closeModalButton);
    }
    
    private void typeInside(By locator, String text){
        WebElement el = root.findElement(locator);
        el.clear();
        el.sendKeys(text);
    }
}