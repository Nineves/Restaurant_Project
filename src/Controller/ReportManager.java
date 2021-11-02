package Controller;

import Entities.Order;
import Entities.Restaurant;
import Entities.MenuItem;

import java.time.LocalDate;
import java.util.*;

public class ReportManager {

    public static void generateByDay(LocalDate date){
        System.out.println("Report for " + date);
        System.out.println("MenuItem | Qty");
        ArrayList<MenuItem> allOrderItems = new ArrayList<MenuItem>();
        Map<String,Integer> allQuantityMap = new HashMap<String,Integer>();
        double totalSales = 0;
        ArrayList<Order> orderList= Restaurant.orderList;
        for (int i = 0; i < orderList.size(); i++) {
            Order o = orderList.get(i);
            if (o.date.equals(date)) { // for orders in that day
                ArrayList<MenuItem> orderItems = o.getOrderItems();
                Map<String, Integer> qtymap = o.getQuantityMap();
                for (int j = 0; j < orderItems.size(); j++) { //for items in order
                    MenuItem oi = orderItems.get(j);
                    String itemName = oi.getName();
                    int qty = qtymap.get(itemName);
                    if (allQuantityMap.get(itemName) != null) { //orderitem exists in allOrderItems and allQuantityMap
                        allQuantityMap.put(itemName, allQuantityMap.get(itemName)+ qty);
                    }
                    else { //order item does not exist in allOrderItems and allQuantityMap
                        allOrderItems.add(oi);
                        allQuantityMap.put(itemName, qty);
                    }
                }
                totalSales = totalSales + o.calculatePrice();
            }
        }
        for (int i = 0; i < allOrderItems.size(); i++) {
            MenuItem oi = allOrderItems.get(i);
            System.out.println(oi.getName() + " | " +  allQuantityMap.get(oi.getName()));
        }
        System.out.println("Total sales: " + totalSales);
    }

    public static void generateByMonth(){

    }

}
