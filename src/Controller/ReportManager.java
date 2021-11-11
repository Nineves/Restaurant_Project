package Controller;

import Entities.Order;
import Entities.Restaurant;
import Entities.MenuItem;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public class ReportManager {

    public static void generateByDay(LocalDate date){
        System.out.println(String.format("*****************Report for %d %s %d*****************", date.getDayOfMonth(), date.getMonth(), date.getYear()));
        System.out.println(String.format("%-50s | %-10s", "Menu Item", "Qty"));
        ArrayList<MenuItem> allOrderItems = new ArrayList<MenuItem>();
        Map<String,Integer> allQuantityMap = new HashMap<String,Integer>();
        double totalSales = 0;
        ArrayList<Order> orderList= Restaurant.getOrderList();
        for (int i = 0; i < orderList.size(); i++) {
            Order o = orderList.get(i);
            if (o.getTimeStamp().toLocalDate().equals(date) && o.isCompleted()) { // for orders in that day
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
                if (allOrderItems.size()==0){
                    System.out.println("No order is made on this date!");
                }
            }
        }
        for (int i = 0; i < allOrderItems.size(); i++) {
            MenuItem oi = allOrderItems.get(i);
            System.out.println(String.format("%-50s | %-10d", oi.getName(), allQuantityMap.get(oi.getName())));
        }
        System.out.println(String.format("********************Total sales: $%.2f********************", totalSales));
    }

    public static void generateByMonth(int month,int year){
        Month mt=Month.of(month);
        String monthName=mt.getDisplayName(TextStyle.FULL,Locale.ENGLISH);
        System.out.println(String.format("*****************Report for %s in %d*****************", monthName, year));
        System.out.println(String.format("%-50s | %-10s", "Menu Item", "Qty"));
        ArrayList<MenuItem> allOrderItems = new ArrayList<MenuItem>();
        Map<String,Integer> allQuantityMap = new HashMap<String,Integer>();
        double totalSales = 0;
        ArrayList<Order> orderList= Restaurant.getOrderList();
        for(int i=0;i<orderList.size();i++){
            Order o = orderList.get(i);
            if(o.getMonth()==month&&o.getYear()==year && o.isCompleted()){
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
            System.out.println(String.format("%-50s | %-10d", oi.getName(), allQuantityMap.get(oi.getName())));
        }
        System.out.println(String.format("********************Total sales: $%.2f********************", totalSales));
        if (allOrderItems.size()==0){
            System.out.println("No order is made in this month!");
        }
}
}
