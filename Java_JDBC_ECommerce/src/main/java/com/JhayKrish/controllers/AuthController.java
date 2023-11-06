package com.JhayKrish.controllers;

import com.JhayKrish.dao.UserDao;
import com.JhayKrish.models.User;
import com.JhayKrish.utils.AppException;
import com.JhayKrish.utils.StringUtil;
import com.JhayKrish.utils.UserUtil;
import com.JhayKrish.views.AuthPage;
import com.JhayKrish.views.LoginPage;
import com.JhayKrish.views.RegisterPage;

import static com.JhayKrish.utils.AppInput.enterInteger;
import static com.JhayKrish.utils.AppInput.enterString;
import static com.JhayKrish.utils.AppOutput.println;

public class AuthController {

    private final AuthPage authPage;
    private final LoginPage loginPage;
    private final RegisterPage registerPage;

    private final HomeController homeController;

    private final UserDao userDao;
    private final AdminController adminController;

    public AuthController() {
        this.authPage = new AuthPage();
        this.loginPage = new LoginPage();
        this.registerPage = new RegisterPage();
        this.userDao = new UserDao();
        this.homeController = new HomeController();
        this.adminController = new AdminController();
    }

    public void authMenu() {
        authPage.authMenu();
        int choice;
        try {
            choice = enterInteger(StringUtil.CHOICE);
            if (choice == 1) {
                login();
            } else if (choice == 2) {
                register();
            } else {
                invalidException(new AppException(StringUtil.INVALID_CHOICE));
            }
        } catch (AppException e) {
            invalidException(e);

        }
    }

    private void register() {
        String  email, password, confirm_password;
        email = enterString(StringUtil.ENTER_EMAIL);
        password = enterString(StringUtil.ENTER_PASSWORD);
        confirm_password = enterString(StringUtil.ENTER_CONFIRM_PASSWORD);
        if (password.equals(confirm_password))
        {
            boolean isExistingUser = userDao.getUser(email);
            if(!isExistingUser)
            {
                userDao.registerUser(email,password);
                registerPage.registrationSuccessful();
                authMenu();

            }
            else
            {
                registerPage.userExists();
                authMenu();
            }

        }
        else
        {
            registerPage.passwordMisMatch();
            authMenu();
        }

    }

    private void login() {
        String email = enterString(StringUtil.ENTER_EMAIL);
        String password = enterString(StringUtil.ENTER_PASSWORD);

        User user = userDao.validateUser(email, password);

        if (user!=null) {
            if(user.getRole().getRole().equals("Admin"))
            {
                adminController.welcome();
            }
            else
            {
                loginPage.loginSuccess();
                UserUtil.setLoggedInUser(user);
                homeController.printMenu();
            }


        } else {
            loginPage.printInvalidCredentials();
            authMenu();
        }
        
    }

    private void invalidException(AppException e) {
        println(e.getMessage());
        authMenu();
    }
}
