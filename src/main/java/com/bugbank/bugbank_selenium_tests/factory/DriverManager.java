package com.bugbank.bugbank_selenium_tests.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
    
    public WebDriver initializeDriver(){
        ChromeOptions options = new ChromeOptions();

        if(isCI()){
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        return driver;
    }

    private boolean isCI(){
        return System.getenv("CI") != null;
    }
}
