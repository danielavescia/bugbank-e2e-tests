package com.bugbank.bugbank_selenium_tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.bugbank.bugbank_selenium_tests.model.User;
public class RegisterComponent extends BasePage {

    private WebElement root;

    private final By form = By.xpath("//form[.//button[text()='Cadastrar']]");

    private final By emailField = By.name("email");

    private final By nameField = By.name("name");

    private final By passwordField = By.name("password");

    private final By passwordConfirmationField = By.name("passwordConfirmation");

    private final By toggleAddBalance = By.xpath("//div[contains(@class,'ContainerToggle')]//span");

    private final By confirmButton = By.xpath("//button[normalize-space()='Cadastrar']");

    private final By closeModalButton = By.id("btnCloseModal");

    private final By modalText = By.id("modalText");

    public RegisterComponent(WebDriver driver){
        super(driver);
        this.root = wait.until(ExpectedConditions.visibilityOfElementLocated(form));
    }

    private void typeInside(By locator, String text){
        WebElement el = root.findElement(locator);
        el.clear();
        el.sendKeys(text);
    }

    private  void closeModal(){
        click(closeModalButton);
    }
    
    private String getModalText(){
        return waitVisible(modalText).getText();
    }

    private RegisterComponent createAccountWithBalance(User user){
        typeInside(emailField, user.getEmail());
        typeInside(nameField, user.getName());
        typeInside(passwordField, user.getPassword());
        typeInside(passwordConfirmationField, user.getPassword());
        click(toggleAddBalance);
        click(confirmButton);
        return this;
    }

    public String createAccountWithBalanceAndGetModalText(User user){
        createAccountWithBalance(user);
        String text = getModalText();
        closeModal();
        return text;
    }
}