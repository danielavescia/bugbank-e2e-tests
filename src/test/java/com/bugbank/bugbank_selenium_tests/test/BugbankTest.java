package com.bugbank.bugbank_selenium_tests.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.bugbank.bugbank_selenium_tests.test.base.BaseTest;

public class BugbankTest extends BaseTest{

    @Test
    @DisplayName("Initial Test setup")
    public void firstTest(){
        startDriver();
        quitDriver();
    }
}
