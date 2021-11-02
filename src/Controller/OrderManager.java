package Controller;

import Entities.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderManager {

    public static void addNewOrder(int staffChoice, int numOfPax, int tableChoice, Timestamp timeCreated,boolean membership){
        Staff s=Restaurant.stafflist.get(staffChoice-1);
        Table t=Restaurant.tablelist.get(tableChoice-1);
        t.setOccupied(true);
        t.setNoOfPax(numOfPax);
        int id=IDGenerator.generateUniqueId();
        Order newOrder= new Order(id,s,t,membership,numOfPax);
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
        sc.close();
    }

    public static void printAllOrders(){
        if(Restaurant.orderList.size()==0){
            System.out.println("No order is in the list.");
            return;
        }
        for (int i=0;i<Restaurant.orderList.size();i++){
            Order curOrder=Restaurant.orderList.get(i);
            System.out.println("INDEX "+i+1);
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
}
