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
        Map<String,Integer> allQuantityMap = new HashMap<String,Integer>();
        double totalSales = 0;
        ArrayList<Order> orderList= Restaurant.orderList;
        for (int i = 0; i < orderList.size(); i++) {
            Order o = orderList.get(i);
            if (o.getTimeStamp().toLocalDate().equals(date)) { // for orders in that day
                ArrayList<MenuItem> orderItems = o.getOrderItems();
                Map<String, Integer> qtymap = o.getQuantityMap();
                for (int j = 0; j < orderItems.size(); j++) { //for items in order
                    String itemName = orderItems.get(j).getName();
                    int qty = qtymap.get(itemName);
                    if (allQuantityMap.get(itemName) != null) { //orderitem exists in allOrderItems and allQuantityMap
                        allQuantityMap.put(itemName, allQuantityMap.get(itemName)+ qty);
                    }
                    else { //order item does not exist in allOrderItems and allQuantityMap
                        allQuantityMap.put(itemName, qty);
                    }
                }
                totalSales = totalSales + o.calculatePrice();
            }
        }
        final Set<Map.Entry<String, Integer>> entries = allQuantityMap.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(itemName + " | " + quantity);
        }
        System.out.println("Total sales: " + totalSales);
    }

    public static void generateByMonth(){
        System.out.println("Report for " + LocalDate.now().getMonth() + " " + LocalDate.now().getYear());
        System.out.println("MenuItem | Qty");
        Map<String,Integer> allQuantityMap = new HashMap<String,Integer>();
        double totalSales = 0;
        ArrayList<Order> orderList= Restaurant.orderList;
        for (int i = 0; i < orderList.size(); i++) {
            Order o = orderList.get(i);
            if (o.getTimeStamp().getMonthValue() == LocalDate.now().getMonthValue()) { // for orders in that day
                ArrayList<MenuItem> orderItems = o.getOrderItems();
                Map<String, Integer> qtymap = o.getQuantityMap();
                for (int j = 0; j < orderItems.size(); j++) { //for items in order
                    String itemName = orderItems.get(j).getName();
                    int qty = qtymap.get(itemName);
                    if (allQuantityMap.get(itemName) != null) { //orderitem exists in allOrderItems and allQuantityMap
                        allQuantityMap.put(itemName, allQuantityMap.get(itemName)+ qty);
                    }
                    else { //order item does not exist in allOrderItems and allQuantityMap
                        allQuantityMap.put(itemName, qty);
                    }
                }
                totalSales = totalSales + o.calculatePrice();
            }
        }
        final Set<Map.Entry<String, Integer>> entries = allQuantityMap.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(itemName + " | " + quantity);
        }
        System.out.println("Total sales: " + totalSales);
    }
}
