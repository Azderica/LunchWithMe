package com.demo.lunchWithMe.controller;

import com.demo.lunchWithMe.service.LunchSignupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LunchController {

    private final LunchSignupService lunchSignupService;

    public LunchController(LunchSignupService lunchSignupService) {
        this.lunchSignupService = lunchSignupService;
    }

    @GetMapping("/")
    public String showSignupForm(Model model) {
        model.addAttribute("isSignupTime", lunchSignupService.isSignupTime());
        model.addAttribute("signups", lunchSignupService.getAllSignups());
        return "lunch-signup";
    }

    @PostMapping("/submit-lunch")
    @ResponseBody
    public String submitLunch(@RequestParam String userId,
                              @RequestParam String menu) {
        if (!lunchSignupService.isSignupTime()) {
            return "<p class='text-danger'>신청 시간이 아닙니다 (09:00 ~ 11:00)</p>";
        }

        boolean success = lunchSignupService.addSignup(userId, menu);
        if (success) {
            return "<p class='text-success'>" + userId +
                    "님의 점심 신청이 완료되었습니다.<br>선택하신 메뉴: " +
                    menu + "</p>";
        } else {
            return "<p class='text-danger'>신청에 실패했습니다.</p>";
        }
    }
}
