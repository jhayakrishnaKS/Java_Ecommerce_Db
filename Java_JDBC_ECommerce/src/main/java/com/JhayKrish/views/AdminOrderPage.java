package com.JhayKrish.views;

import com.JhayKrish.models.Order;
import com.JhayKrish.utils.StringUtil;

import java.util.ArrayList;

import static com.JhayKrish.utils.AppOutput.println;

public class AdminOrderPage {
    public void viewOrders(ArrayList<Order> orders) {
        println(StringUtil.ORDER_MESSAGE);

        for (Order order : orders) {

            println(order.getId() + ". " + order.getProduct().getProductName() + ", "+"Ordered By: "+order.getUser().getEmail()+ ", " + order.getDate() + ", ₹." + order.getProduct().getPrice());

        }
    }
}
