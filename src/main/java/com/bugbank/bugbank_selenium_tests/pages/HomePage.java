package com.bugbank.bugbank_selenium_tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.bugbank.bugbank_selenium_tests.model.User;

public class HomePage extends BasePage {

    private final By emailField = By.name("email");

    private final By passwordField = By.name("password");

    private final By loginButton = By.xpath("//button[normalize-space()='Acessar']");

    private final By registerButton = By.xpath("(//button[normalize-space()='Registrar'])[1]");

    public HomePage(WebDriver driver){
        super(driver);
    }

    public HomePage load(){
        load("/");
        return this;
    }

    public RegisterComponent navigateToRegister() {
        click(registerButton);
        return new RegisterComponent(driver);
    }

    public AccountPage login(User user){
        type(emailField, user.getEmail());
        type(passwordField, user.getPassword());
        click(loginButton);
        return new AccountPage(driver);
    }
}
