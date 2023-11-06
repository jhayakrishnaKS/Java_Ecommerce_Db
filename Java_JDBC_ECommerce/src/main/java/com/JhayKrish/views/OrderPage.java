package com.JhayKrish.views;

import com.JhayKrish.models.Order;
import com.JhayKrish.models.User;
import com.JhayKrish.utils.StringUtil;

import java.util.ArrayList;

import static com.JhayKrish.utils.AppOutput.println;
import static com.JhayKrish.utils.UserUtil.getLoggedInUser;

public class OrderPage {
    public void printOrders(ArrayList<Order> orders) {
        println(StringUtil.ORDER_MESSAGE);

        User user = getLoggedInUser();
        for(Order order:orders)
        {
            if(order.getUser().getId()==user.getId())
            {
                println(order.getId()+". " + order.getProduct().getProductName() +", "+ order.getDate() +", ₹."+order.getProduct().getPrice());
            }
        }
    }
}
