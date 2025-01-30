package com.example.lunchWithMe.service;

import com.example.lunchWithMe.model.LunchSignup;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LunchSignupService {

    // 로컬 캐시로 ConcurrentHashMap 사용
    private final Map<String, LunchSignup> signupCache = new ConcurrentHashMap<>();

    public boolean isSignupTime() {
        LocalTime now = LocalTime.now();
        return now.isAfter(LocalTime.of(9, 0))
                && now.isBefore(LocalTime.of(11, 0));
    }

    public boolean addSignup(String userId, String menu) {
        if (!isSignupTime()) {
            return false;
        }

        signupCache.put(userId, new LunchSignup(userId, menu, LocalDateTime.now()));
        return true;
    }

    public List<LunchSignup> getAllSignups() {
        return new ArrayList<>(signupCache.values());
    }

    // 매일 자정에 캐시 초기화
    @Scheduled(cron = "0 0 0 * * *")
    public void clearCache() {
        signupCache.clear();
    }
}
