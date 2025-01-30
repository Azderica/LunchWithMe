package com.example.lunchWithMe.controller;


import com.example.lunchWithMe.config.PathConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class LoginController {

    @GetMapping(PathConstant.PATH_ROOT)
    public String root(HttpSession session) {
        return session.getAttribute("userId") == null ?
                PathConstant.REDIRECT_LOGIN : PathConstant.REDIRECT_LUNCH;
    }

    @GetMapping(PathConstant.PATH_LOGIN)
    public String loginPage(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            return PathConstant.REDIRECT_LUNCH;
        }
        return PathConstant.VIEW_LOGIN;
    }

    @PostMapping(PathConstant.PATH_LOGIN)
    public String login(@RequestParam String userId,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        // todo. 실제 환경에서는 proper 인증 로직 구현 필요
        if (password == null || password.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "비밀번호를 입력해주세요.");
            return PathConstant.REDIRECT_LOGIN;
        }

        session.setAttribute("userId", userId);
        return PathConstant.REDIRECT_LUNCH;
    }

    @GetMapping(PathConstant.PATH_LOGOUT)
    public String logout(HttpSession session) {
        session.invalidate();
        return PathConstant.REDIRECT_LOGIN;
    }
}