package com.example.chatserver.helper;

public class GenerateOTP { 
        public static String generateOTP()  
        {   
            int randomPin   =(int) (Math.random()*900000)+100000; 
            String otp  = String.valueOf(randomPin); 
            return otp;
        } 
    }