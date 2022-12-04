package com.ndt.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordValidationTest {
    @Test
    public void passwordValidation() {
        String pwd1 = "aaZZa44@1";
        String pwd2 = "abcdasdf";
        String pwd3 = "abcd1234";
        String pwd4 = "Abcd@123";
        Assertions.assertTrue(PasswordValidation.isPasswordValid(pwd1));
        Assertions.assertFalse(PasswordValidation.isPasswordValid(pwd2));
        Assertions.assertFalse(PasswordValidation.isPasswordValid(pwd3));
        Assertions.assertTrue(PasswordValidation.isPasswordValid(pwd4));
    }
}
