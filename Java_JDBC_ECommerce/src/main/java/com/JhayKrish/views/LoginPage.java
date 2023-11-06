package com.JhayKrish.views;

import com.JhayKrish.utils.StringUtil;

import static com.JhayKrish.utils.AppOutput.println;

public class LoginPage {
    public void printInvalidCredentials() {
        println(StringUtil.INVALID_CREDENTIALS);
    }

    public void loginSuccess() {
        println(StringUtil.LOGIN_SUCCESS);
    }
}
