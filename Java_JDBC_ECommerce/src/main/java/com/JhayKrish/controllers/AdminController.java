package com.JhayKrish.controllers;

import com.JhayKrish.utils.AppException;
import com.JhayKrish.utils.StringUtil;
import com.JhayKrish.views.AdminPage;

import static com.JhayKrish.utils.AppInput.enterInteger;
import static com.JhayKrish.utils.AppOutput.println;

public class AdminController {


    private final AdminCategoryController adminCategoryController;
    private final AdminProductController adminProductController;
    private final AdminOrderController adminOrderController;
    private final AdminPage adminPage;
    public AdminController() {
        this.adminOrderController = new AdminOrderController(this);
        this.adminProductController = new AdminProductController(this);
        this.adminCategoryController = new AdminCategoryController(this);
        this.adminPage = new AdminPage();
    }

    public void printMenu() {
        adminPage.adminMenu();
        int choice;
        try {
            choice = enterInteger(StringUtil.CHOICE);
            if (choice == 1) {
                adminCategoryController.viewCategories();

            } else if (choice == 2) {
                adminProductController.viewProducts();
            }
            else if(choice==3)
            {
                adminOrderController.viewOrders();
            }
            else if(choice==4)
            {
                println(StringUtil.THANK_YOU_MESSAGE);
            }
            else {
                invalidException(new AppException(StringUtil.INVALID_CHOICE));
            }
        } catch (AppException e) {
            invalidException(e);

        }

    }

    private void invalidException(AppException e) {
        println(e.getMessage());
        printMenu();
    }
    public void welcome() {
        adminPage.welcomeMsg();
        printMenu();
    }
}
