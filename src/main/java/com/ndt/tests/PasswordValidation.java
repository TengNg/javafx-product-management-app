package com.ndt.tests;

public class PasswordValidation {

    public static boolean isPasswordValid(String password) {
        // (?=.*[0-9]) ==> a digit must occur at least once
        // (?=.*[a-z]) ==> a lower case character must occur at least once
        // (?=.*[0-9]) ==> a upper case character must occur at least once
        // .{5,10}     ==> password length [min:5, max:10]
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{5,10}";
        return password.matches(pattern);
    }
}
