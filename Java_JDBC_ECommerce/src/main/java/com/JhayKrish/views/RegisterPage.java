package com.JhayKrish.views;

import com.JhayKrish.utils.StringUtil;

import static com.JhayKrish.utils.AppOutput.println;

public class RegisterPage {
    public void passwordMisMatch() {
        println(StringUtil.PASSWORD_MISMATCH);
    }

    public void userExists() {
        println(StringUtil.USER_EXISTS);
    }

    public void registrationSuccessful() {
        println(StringUtil.REGISTRATION_SUCCESSFUL);
    }
}
