package com.example.freshly.Captcha;

public class CaptchaChecker {
    public static boolean checkInputCaptcha(String Input , int imageCode){
        boolean result = false ;
        switch (imageCode) {
            case 100 -> {//HJ9PV
                if (Input.equalsIgnoreCase("HJ9PV")) {
                    result = true;
                }
            }
            case 101 -> {//JA3V8
                if (Input.equalsIgnoreCase("JA3V8")) {
                    result = true;
                }
            }
            case 102 -> {//3M56R
                if (Input.equalsIgnoreCase("3M56R")) {
                    result = true;
                }
            }
            case 103 -> {//PADTC
                if (Input.equalsIgnoreCase("PADTC")) {
                    result = true;
                }
            }
            case 104 -> {//HWJRC
                if (Input.equalsIgnoreCase("HWJRC")) {
                    result = true;
                }
            }
            case 105 -> {//D35UA
                if (Input.equalsIgnoreCase("D35UA")) {
                    result = true;
                }
            }
            case 106 -> {//4NV3A
                if (Input.equalsIgnoreCase("4NV3A")) {
                    result = true;
                }
            }
            case 107 -> {//HK5B6
                if (Input.equalsIgnoreCase("HK5B6")) {
                    result = true;
                }
            }
            case 108 -> {//P48EK
                if (Input.equalsIgnoreCase("P48EK")) {
                    result = true;
                }
            }
            case 109 -> {//KNYWV
                if (Input.equalsIgnoreCase("KNYWV")) {
                    result = true;
                }
            }
            case 110 -> {//MUX25
                if (Input.equalsIgnoreCase("MUX25")) {
                    result = true;
                }
            }
            case 111 -> {//WKRH5
                if (Input.equalsIgnoreCase("WKRH5")) {
                    result = true;
                }
            }
            case 112 -> {//VETRC
                if (Input.equalsIgnoreCase("VETRC")) {
                    result = true;
                }
            }
        }
        return result;
    }
}
