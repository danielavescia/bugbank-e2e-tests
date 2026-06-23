package com.bugbank.bugbank_selenium_tests.utils;

import java.util.Locale;
import com.bugbank.bugbank_selenium_tests.model.User;
import net.datafaker.Faker;

public class DataGenerator {
    
    private static final Faker faker = new Faker (new Locale("pt", "BR"));

    public static String generateEmail(){
        return faker.internet().emailAddress();
    }

    public static String genereteName(){
        return faker.name().fullName();
    }
    
    public static String generetePassword(){
        return faker.internet().password(6, 9);
    }

    public static User generateUser(){
        return User.builder()
                .name(genereteName())
                .email(generateEmail())
                .password(generetePassword()).build();
    }
}