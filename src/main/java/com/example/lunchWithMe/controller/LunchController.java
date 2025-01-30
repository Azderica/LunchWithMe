package com.example.lunchWithMe.controller;

import com.example.lunchWithMe.config.PathConstant;
import com.example.lunchWithMe.service.LunchSignupService;
import com.example.lunchWithMe.model.LunchSignup;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(PathConstant.PATH_LUNCH)
public class LunchController {

    private final LunchSignupService lunchSignupService;

    public LunchController(LunchSignupService lunchSignupService) {
        this.lunchSignupService = lunchSignupService;
    }

    @GetMapping
    public String lunchPage(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return PathConstant.REDIRECT_LOGIN;
        }

        model.addAttribute("isSignupTime", lunchSignupService.isSignupTime());
        model.addAttribute("allSignups", lunchSignupService.getAllSignups());

        LunchSignup mySignup = lunchSignupService.getSignupByUserId(userId);
        model.addAttribute("hasSignup", mySignup != null);
        model.addAttribute("mySignup", mySignup);

        return PathConstant.VIEW_LUNCH_MAIN;
    }

    @PostMapping("/submit")
    public String submitLunch(@RequestParam String menu,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return PathConstant.REDIRECT_LOGIN;
        }

        if (!lunchSignupService.isSignupTime()) {
            redirectAttributes.addFlashAttribute("error", "신청 가능 시간이 아닙니다.");
            return PathConstant.REDIRECT_LUNCH;
        }

        if (lunchSignupService.getSignupByUserId(userId) != null) {
            redirectAttributes.addFlashAttribute("error", "이미 신청하셨습니다.");
            return PathConstant.REDIRECT_LUNCH;
        }

        lunchSignupService.addSignup(userId, menu);
        redirectAttributes.addFlashAttribute("success", "신청이 완료되었습니다.");
        return PathConstant.REDIRECT_LUNCH;
    }

    @PostMapping("/cancel")
    public String cancelSignup(HttpSession session,
                               RedirectAttributes redirectAttributes) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return PathConstant.REDIRECT_LOGIN;
        }

        if (!lunchSignupService.isSignupTime()) {
            redirectAttributes.addFlashAttribute("error", "취소 가능 시간이 아닙니다.");
            return PathConstant.REDIRECT_LUNCH;
        }

        if (lunchSignupService.getSignupByUserId(userId) == null) {
            redirectAttributes.addFlashAttribute("error", "신청 내역이 없습니다.");
            return PathConstant.REDIRECT_LUNCH;
        }

        lunchSignupService.cancelSignup(userId);
        redirectAttributes.addFlashAttribute("success", "신청이 취소되었습니다.");
        return PathConstant.REDIRECT_LUNCH;
    }
}