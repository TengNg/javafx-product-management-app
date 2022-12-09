package com.ndt.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PasswordValidationTest {
    @ParameterizedTest
    @CsvSource({"aaZZa44@1, true", "abcdasdf, false", "abcd1234, false", "Abcd@123, true"})
    public void passwordValidation(String password, boolean isValid) {
        Assertions.assertEquals(PasswordValidation.isPasswordValid(password), isValid);
    }
}
