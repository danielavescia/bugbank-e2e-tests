package com.bugbank.bugbank_selenium_tests.test.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import com.bugbank.bugbank_selenium_tests.factory.DriverManager;

public class BaseTest {

    private WebDriver driver;

    @BeforeEach 
    public void startDriver(){
        driver = new DriverManager().initializeDriver();
    }

    @AfterEach
    public void quitDriver(){
        driver.quit();
    }
}
