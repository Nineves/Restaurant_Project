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
        Staff s=Restaurant.stafflist.get(staffChoice-1);
        Table t=Restaurant.tablelist.get(tableChoice-1);
        t.setOccupied(true);
        t.setNoOfPax(numOfPax);
        int id=IDGenerator.generateUniqueId();
        Order newOrder= new Order(id,s,t,membership,numOfPax,timeCreated);
        addItemsToOrder(newOrder);
        newOrder.setTimeStamp(timeCreated);
        Restaurant.orderList.add(newOrder);
        System.out.println("Order "+ id +" added successfully!");
    }

    public static void addItemsToOrder(Order order){
        System.out.println("Choose an item to add: (press 0 to stop)");
        MenuItemManager.printFullMenu();
        ArrayList<MenuItem> itemList=order.getOrderItems();
        Scanner sc=new Scanner(System.in);
        int choice=sc.nextInt();
        while (choice>0&&choice<=Restaurant.menulist.size()){
            MenuItem curItem=Restaurant.menulist.get(choice-1);
            System.out.println("Enter quantity: ");
            int q=sc.nextInt();
            while(q<=0){
                System.out.println("Invalid input. Please enter again.");
                q=sc.nextInt();
            }
            order.addFood(curItem,q);
            System.out.println("Choose an item to add: (press 0 to stop)");
            choice=sc.nextInt();
        }

    }

    public static void printAllOrders(){
        if(Restaurant.orderList.size()==0){
            System.out.println("No order is in the list.");
            return;
        }
        for (int i=0;i<Restaurant.orderList.size();i++){
            Order curOrder=Restaurant.orderList.get(i);
            System.out.println("INDEX "+(i+1));
            curOrder.printInfo();
        }
    }

    public static void updateStaff(Order order,int index){
        Staff newStaff=Restaurant.stafflist.get(index-1);
        order.setStaff(newStaff);
    }

    public static void updateTable(Order order, int index){
        Table curTable=order.getTable();
        Table newTable=Restaurant.tablelist.get(index-1);
        newTable.setNoOfPax(curTable.getNoOfPax());
        curTable.resetTable();
        order.setTable(newTable);
    }

    public static void removeItemFromOrder(Order order){
        if(order.getOrderItems().size()==0){
            System.out.println("Nothing to be removed from order.");
            return;
        }
        System.out.println("Choose an item to remove: (press 0 to stop)");
        order.printItemsInOrder();
        Scanner sc=new Scanner(System.in);
        int choice;
        choice=sc.nextInt();
        while (choice>0&&choice<=order.getOrderItems().size()){
            System.out.println("Remove quantity: ");
            int quantity=sc.nextInt();
            while(quantity<=0){
                System.out.println("Invalid input. Please enter again. ");
                quantity=sc.nextInt();
            }
            order.removeItem(choice,quantity);
            System.out.println("Choose a item to remove: (press '0' to stop)");
            order.printItemsInOrder();
            choice=sc.nextInt();
        }
        sc.close();
    }

    public static void updateMembership(Order order,boolean m)
    {
        order.setMembership(m);
        System.out.println("Membership updated successfully!");
    }

    public static void removeOrder(int index){
        if(index>0&&index<=Restaurant.orderList.size()){
            Order orderToBeRemoved=Restaurant.orderList.get(index-1);
            int orderID=orderToBeRemoved.getOrderID();
            Restaurant.orderList.remove(index-1);
            System.out.println("Order "+ orderID +" removed successfully!");
        }
        else {
            System.out.println("Invalid index.");
        }
    }

    public static void printInvoice(int idx){
        if(idx<1||idx>Restaurant.orderList.size()){
            System.out.println("Order does not exist.");
        }
        Order order=Restaurant.orderList.get(idx-1);
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
