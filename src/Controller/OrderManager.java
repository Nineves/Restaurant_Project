package Controller;

import Entities.*;
import Enums.FoodType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class OrderManager {

    public static void addNewOrder(int staffChoice, int numOfPax, int tableChoice, LocalDateTime timeCreated, boolean membership){
        Staff s=Restaurant.getStafflist().get(staffChoice-1);
        Table t=Restaurant.getTablelist().get(tableChoice-1);
        t.setOccupied(true);
        t.setNoOfPax(numOfPax);
        int id=IDGenerator.generateUniqueId();
        Order newOrder= new Order(id,s,t,membership,numOfPax,timeCreated);
        addItemsToOrder(newOrder);
        newOrder.setTimeStamp(timeCreated);
        Restaurant.addOrderList(newOrder);
        System.out.println("Order "+ id +" added successfully!");
    }

    public static void addItemsToOrder(Order order){
        MenuItemManager.printFullMenu();
        System.out.println("Choose an item to add: (press 0 to stop)");
        //ArrayList<MenuItem> itemList=order.getOrderItems();
        int choice = IntegerInputHelper.validateInput(0, Restaurant.getMenulist().size());
        while (choice>0){
            MenuItem curItem=Restaurant.getMenulist().get(choice-1);
            System.out.println("Enter quantity: ");
            int maxQty = 10;
            int q = IntegerInputHelper.validateInput(1, maxQty);
            order.addFood(curItem,q);
            MenuItemManager.printFullMenu();
            System.out.println("Choose an item to add: (press 0 to stop)");
            choice = IntegerInputHelper.validateInput(0, Restaurant.getMenulist().size());
        }

    }

    public static void printAllOrders(){
        if(Restaurant.getOrderList().size()==0){
            System.out.println("No order is in the list.");
            return;
        }
        for (int i=0;i<Restaurant.getOrderList().size();i++){
            Order curOrder=Restaurant.getOrderList().get(i);
            System.out.println("INDEX "+(i+1));
            curOrder.printInfo();
        }
    }

    public static void updateStaff(Order order,int index){
        Staff newStaff=Restaurant.getStafflist().get(index-1);
        order.setStaff(newStaff);
    }

    public static void updateTable(Order order, int index){
        Table curTable=order.getTable();
        Table newTable=Restaurant.getTablelist().get(index-1);
        newTable.setNoOfPax(curTable.getNoOfPax());
        curTable.resetTable();
        order.setTable(newTable);
    }

    public static void removeItemFromOrder(Order order){
        if(order.getOrderItems().size()==0){
            System.out.println("Nothing to be removed from order.");
            return;
        }
        order.printItemsInOrder();
        System.out.println("Choose an item to remove: (press 0 to stop)");
        int choice = IntegerInputHelper.validateInput(0, order.getOrderItems().size());
        while (choice>0){
            System.out.println("Remove quantity: ");
            ArrayList<MenuItem> menuItems = order.getOrderItems();
            String itemName = menuItems.get(choice-1).getName();
            int quantity = IntegerInputHelper.validateInput(1, order.getQuantityMap().get(itemName));
            order.removeItem(choice,quantity);
            order.printItemsInOrder();
            System.out.println("Choose a item to remove: (press '0' to stop)");
            choice = IntegerInputHelper.validateInput(0, order.getOrderItems().size());
        }
    }

    public static void updateMembership(Order order,boolean m)
    {
        order.setMembership(m);
        System.out.println("Membership updated successfully!");
    }

    public static void removeOrder(int index){
        if(index>0&&index<=Restaurant.getOrderList().size()){
            Order orderToBeRemoved=Restaurant.getOrderList().get(index-1);
            int orderID=orderToBeRemoved.getOrderID();
            Restaurant.removeOrder(index-1);
            System.out.println("Order "+ orderID +" removed successfully!");
        }
        else {
            System.out.println("Invalid index.");
        }
    }

    public static void printInvoice(int idx){
        if(idx<1||idx>Restaurant.getOrderList().size()){
            System.out.println("Order does not exist.");
            return;
        }
        Order order=Restaurant.getOrderList().get(idx-1);
        if(order.isCompleted()==true){
            System.out.println("The payment has already been made.");
            return;
        }
        order.setCompleted();
        Table curTable=order.getTable();
        curTable.setOccupied(false);
        ArrayList<MenuItem> itemList=order.getOrderItems();
        Map<String,Integer> map=order.getQuantityMap();
        double totalPrice=order.calculatePrice();
        System.out.println("-------------------------------------");
        System.out.println("OrderID : "+ order.getOrderID());
        System.out.println("Served by: "+ order.getStaff().getName());
        System.out.println("Date: "+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println("");
        System.out.println(String.format("%-10s %-20s %s","Qty","item name", "price"));
        System.out.println("-------------------------------------");
        for (int i=0;i<itemList.size();i++){
            MenuItem curItem=order.getOrderItems().get(i);
            String itemName=curItem.getName();
            int itemQuantity = map.get(itemName);
            double itemTotalPrice=itemQuantity*curItem.getPrice();
            System.out.println(String.format("%-10d %-20s %.2f",itemQuantity,itemName, itemTotalPrice));
        }
        double GST = totalPrice-totalPrice/1.07;
        totalPrice=totalPrice+GST;
        System.out.println("-------------------------------------");
        System.out.println(String.format("%-31s %.2f","7% GST",GST));
        if(order.getMembership()) {
            System.out.println(String.format("%-31s %s","Membership Discount","-5%"));
            totalPrice = totalPrice*0.95;
        }
        System.out.println("-------------------------------------");
        System.out.println(String.format("%-31s %.2f", "Total", totalPrice));
        System.out.println();

    }




}
