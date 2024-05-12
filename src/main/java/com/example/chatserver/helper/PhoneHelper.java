package com.example.chatserver.helper;

public class PhoneHelper {
    public static String getPhoneNumber(String phoneNumber, String countryCode) {
        return "+" + countryCode + " " + phoneNumber;
    }
}
