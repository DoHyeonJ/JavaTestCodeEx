package com.example.testcodeex;

public class Lunch {
    public String getLunch(String type) {
        String result = "";

        if (type.equals("throw")) {
            throw new IllegalArgumentException("예외처리 확인");
        }

        if (true) {
            result = "Complete";
        } else {
            result = "Fail";
        }

        return result;
    }
}
