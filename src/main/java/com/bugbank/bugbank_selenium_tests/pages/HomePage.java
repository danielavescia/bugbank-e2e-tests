package com.bugbank.bugbank_selenium_tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.bugbank.bugbank_selenium_tests.model.User;
import io.qameta.allure.Step;

public class HomePage extends BasePage {

    private final By emailField = By.name("email");

    private final By passwordField = By.name("password");

    private final By loginButton = By.xpath("//button[normalize-space()='Acessar']");

    private final By registerButton = By.xpath("(//button[normalize-space()='Registrar'])[1]");

    public HomePage(WebDriver driver){
        super(driver);
    }

    @Step("Navega para página de criação de conta")
    public RegisterComponent navigateToRegister() {
        click(registerButton);
        return new RegisterComponent(driver);
    }

    @Step("Carrega página inicial")
    public HomePage load(){
        load("/");
        return this;
    }

    @Step("Digita o e-mail:{email}")
    private HomePage fillEmail(String email){
        type(emailField, email);
        return this;
    }

    @Step("Digita a senha:")
    private HomePage fillPassword(String password){
        type(passwordField, password);
        return this;
    }

    @Step("Clica em Acessar")
    private HomePage clickLogin(){
        click(loginButton);
        return this;
    }

    @Step("Realiza login com usuário {user.name}")
    public AccountPage login(User user){
        fillEmail(user.getEmail());
        fillPassword(user.getPassword());
        clickLogin();
        waitForUrlContains("/home");
        return new AccountPage(driver);
    }
}