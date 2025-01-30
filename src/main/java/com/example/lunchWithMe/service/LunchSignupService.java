package com.example.lunchWithMe.service;

import com.example.lunchWithMe.model.LunchSignup;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class LunchSignupService {
    private final Map<String, LunchSignup> signupCache = new ConcurrentHashMap<>();

    public boolean isSignupTime() {
        LocalTime now = LocalTime.now();
//        return now.isAfter(LocalTime.of(7, 0))
//                && now.isBefore(LocalTime.of(11, 0));
        return true;
    }

    public boolean addSignup(String userId, String menu) {
        if (!isSignupTime()) {
            return false;
        }

        signupCache.put(userId, new LunchSignup(userId, menu, LocalDateTime.now()));
        return true;
    }

    public void cancelSignup(String userId) {
        signupCache.remove(userId);
    }

    public LunchSignup getSignupByUserId(String userId) {
        return signupCache.get(userId);
    }

    public List<LunchSignup> getAllSignups() {
        return new ArrayList<>(signupCache.values());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void clearCache() {
        signupCache.clear();
    }
}
