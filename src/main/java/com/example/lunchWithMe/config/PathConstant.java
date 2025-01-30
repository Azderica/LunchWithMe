package com.example.lunchWithMe.config;

public class PathConstant {
    // View paths (templates 폴더 기준 상대 경로)
    public static final String VIEW_LOGIN = "auth/login";
    public static final String VIEW_LUNCH_MAIN = "lunch/main";

    // Request paths
    public static final String PATH_ROOT = "/";
    public static final String PATH_LOGIN = "/login";
    public static final String PATH_LOGOUT = "/logout";
    public static final String PATH_LUNCH = "/lunch";
    public static final String PATH_LUNCH_SUBMIT = "/lunch/submit";
    public static final String PATH_LUNCH_CANCEL = "/lunch/cancel";

    // Redirect paths
    public static final String REDIRECT_LOGIN = "redirect:" + PATH_LOGIN;
    public static final String REDIRECT_LUNCH = "redirect:" + PATH_LUNCH;
}