package com.example.lunchWithMe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LunchSignup {

    private String userId;
    private String menu;
    private LocalDateTime signupTime;

    public LunchSignup(String userId, String menu, LocalDateTime now) {
        this.userId = userId;
        this.menu = menu;
        this.signupTime = now;
    }
}
