package com.example.testcodeex;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Lunch {

    @Id
    @GeneratedValue
    private Long id;

    private String type;

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
