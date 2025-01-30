package com.example.lunchWithMe.controller;

import com.example.lunchWithMe.service.LunchSignupService;
import com.example.lunchWithMe.model.LunchSignup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Tag(name = "Lunch", description = "점심 신청 API")
public class LunchController {

    private final LunchSignupService lunchSignupService;

    public LunchController(LunchSignupService lunchSignupService) {
        this.lunchSignupService = lunchSignupService;
    }

    @GetMapping("/")
    @Operation(summary = "신청 폼 표시", description = "점심 신청 폼을 표시합니다.")
    public String showSignupForm(Model model) {
        model.addAttribute("isSignupTime", lunchSignupService.isSignupTime());
        model.addAttribute("signups", lunchSignupService.getAllSignups());
        return "lunch-signup";
    }

    @PostMapping("/submit-lunch")
    @ResponseBody
    @Operation(summary = "점심 신청", description = "점심 메뉴를 신청합니다.")
    public String submitLunch(
            @Parameter(description = "사용자 ID") @RequestParam String userId,
            @Parameter(description = "메뉴") @RequestParam String menu) {
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

    // REST API 엔드포인트 추가
    @GetMapping("/api/signups")
    @ResponseBody
    @Operation(summary = "신청 목록 조회", description = "전체 점심 신청 목록을 조회합니다.")
    public List<LunchSignup> getSignups() {
        return lunchSignupService.getAllSignups();
    }
}