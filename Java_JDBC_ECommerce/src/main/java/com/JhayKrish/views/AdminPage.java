package com.JhayKrish.views;

import com.JhayKrish.utils.StringUtil;

import static com.JhayKrish.utils.AppOutput.println;

public class AdminPage {
    public void welcomeMsg() {
        println(StringUtil.ADMIN_WELCOME);
    }

    public void adminMenu() {
        println(StringUtil.ADMIN_CHOICE);
    }
}
