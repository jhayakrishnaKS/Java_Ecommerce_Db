package com.JhayKrish.controllers;

import com.JhayKrish.dao.CartDao;
import com.JhayKrish.dao.ProductDao;
import com.JhayKrish.models.Cart;
import com.JhayKrish.models.Product;
import com.JhayKrish.models.User;
import com.JhayKrish.utils.AppException;
import com.JhayKrish.utils.StringUtil;
import com.JhayKrish.views.CartPage;

import java.util.ArrayList;

import static com.JhayKrish.utils.AppInput.enterInteger;
import static com.JhayKrish.utils.AppOutput.println;
import static com.JhayKrish.utils.UserUtil.getLoggedInUser;

public class CartController {

    private final CartPage cartPage;
    private final CartDao cartDao;
    private final ProductDao productDao;

    private final HomeController homeController;
    private final OrderController orderController;

    public CartController(HomeController homeController,OrderController orderController) {
        this.cartPage = new CartPage();
        this.cartDao = new CartDao();
        this.homeController = homeController;
        this.productDao = new ProductDao();
        this.orderController = new OrderController(homeController);
    }

    private static ArrayList<Cart> carts = new ArrayList<>();

    public void AddtoCart() {


        int choice;
        try {
            choice = enterInteger(StringUtil.PRODUCT_CHOICE);
            boolean isProduct = false;
            for (Product product : productDao.getProducts()) {
                if (product.getId() == choice) {
                    isProduct = true;
                    break;
                }
            }
            if (isProduct) {

                setProductToCart(choice);
            } else {
                invalidException(new AppException(StringUtil.INVALID_PRODUCT));
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        }

    }

    private void setProductToCart(int productId) {

        User user = getLoggedInUser();
        carts = cartDao.getCartItems(user.getId());
        boolean isCartProduct = false;
        for (Cart cart : carts) {
            if (cart.getProduct().getId() == productId && cart.getUser().getId() == user.getId()) {
                isCartProduct = true;
                break;
            }
        }
        if (isCartProduct) {
            UpdateCartProduct(productId);
        } else {
            AddCartProduct(productId);
        }

        println(StringUtil.CART_SUCCESSFUL);
        homeController.printMenu();


    }

    private void AddCartProduct(int productId) {
        User user = getLoggedInUser();
        cartDao.AddItemToCart(productId, user.getId());

    }

    private void UpdateCartProduct(int productId) {

        User user = getLoggedInUser();
        cartDao.UpdateCartItem(productId, user.getId());
    }

    private void invalidException(AppException e) {
        println(e.getMessage());
        homeController.printMenu();
    }

    public void printCartItems() {
        User user = getLoggedInUser();
        carts = cartDao.getCartItems(user.getId());
        if (!carts.isEmpty()) {
            cartPage.printCart(carts);
            println(StringUtil.STATIC_CHOICES);
            println(StringUtil.CHECK_OUT);
            println(StringUtil.BACK);
            int choice;
            try {
                choice = enterInteger(StringUtil.CHOICE);
                if (choice == 1) {
                    orderController.orderedItems();
                    homeController.printMenu();
                }
                else if(choice==100)
                {
                    homeController.printMenu();
                }
                else {
                    invalidCheckoutException(new AppException(StringUtil.INVALID_CHOICE));
                }
            } catch (AppException e) {
                invalidCheckoutException(e);
            }
        } else {
            println(StringUtil.CART_EMPTY);
            homeController.printMenu();
        }

    }
    private void invalidCheckoutException(AppException e) {
        println(e.getMessage());
        printCartItems();
    }

}
