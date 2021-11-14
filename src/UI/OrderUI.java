package UI;

import Controller.*;
import Entities.Order;
import Entities.Reservation;
import Entities.Restaurant;
import Entities.Staff;
import Entities.Table;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.ArrayList;

public class OrderUI {

    public static void displayOptions(){
        System.out.println("===== Order Management ===== ");
        System.out.println("1. Add New Order ");
        System.out.println("2. Update Order ");
        System.out.println("3. Remove Order");
        System.out.println("4. View Order");
        System.out.println("0. Exit order Management");
    }

    public static void orderUI(){
        displayOptions();
        Scanner sc = new Scanner(System.in);
        int option;
        option=IntegerInputHelper.validateInput(0,4);
        while (option<=4&&option>=1){
            switch (option){
                case 1:
                    addNewOrder();
                    break;
                case 2:
                    updateOrder();
                    break;
                case 3:
                    removeOrder();
                    break;
                case 4:
                    viewOrders();
                    break;
                default:
                    return;

            }
            displayOptions();
            option=IntegerInputHelper.validateInput(0,4);
        }
    }

    public static void addNewOrder(){
        System.out.println("Choose the staff who is creating this order: ");
        StaffManager.printStaffList();
        Scanner sc = new Scanner(System.in);
        int staffChoice;
        staffChoice=IntegerInputHelper.validateInput(1,Restaurant.getOrderList().size());
        // while (staffChoice<=0||staffChoice> Restaurant.stafflist.size()){
        //     System.out.println("Staff does not exist. Please enter again: ");
        //     staffChoice=sc.nextInt();
        // }

        // int condition;
        int numOfPax,tableChoice;
        System.out.println("Enter number of pax: 1-10 ");

        numOfPax = IntegerInputHelper.validateInput(1,10);
        // while (numOfPax <= 0) {
        //     System.out.println("Invalid input. Please enter an integer larger than 0.");
        //     numOfPax = sc.nextInt();
        // }
        System.out.println("Choose the table of this order: ");
        ArrayList<Table> tables = TableManager.getAvailableTables(numOfPax);
        if (tables == null) return;
        TableManager.printAvailableTables(tables);
        tableChoice = IntegerInputHelper.validateInput(tables);
        // boolean result = TableManager.validate(numOfPax, tableChoice);
        // while (result == false) {
        //     System.out.println("This table cannot be chosen. Please make another choice: ");
        //     tableChoice = sc.nextInt();
        //     result = TableManager.validate(numOfPax, tableChoice);
        // }
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime now=LocalDateTime.now();
        System.out.println("Does the customer has membership? ('1' for 'Yes','0' for 'No') ");
        boolean membership=false;
        int m=IntegerInputHelper.validateInput(0,1);
        if (m==1){
            membership=true;
        }
        else if(m==0){
            membership=false;
        }
        OrderManager.addNewOrder(staffChoice,numOfPax,tableChoice,now,membership);
    }

    public static void updateOrder(){
        if(Restaurant.getOrderList().size()==0){
            System.out.println("No order is available in the list.");
            return; }
        System.out.println("Which order do you want to update?");
        OrderManager.printAllOrders();
        Scanner sc = new Scanner(System.in);
        int choice=sc.nextInt();
        if(choice>0&&choice<=Restaurant.getOrderList().size()){
            Order curOrder=Restaurant.getOrderList().get(choice-1);
            System.out.println("Select an option");
            System.out.println("1. update staff ");
            System.out.println("2. update table ");
            System.out.println("3. update ordered items ");
            System.out.println("4. update membership ");
            System.out.println("0. Exit ");
            int selection=IntegerInputHelper.validateInput(0,4);
            switch (selection){
                case 1:
                    changeStaff(curOrder);
                    break;
                case 2:
                    changeTable(curOrder);
                    break;
                case 3:
                    updateItems(curOrder);
                    break;
                case 4:
                    updateMembership(curOrder);
                    break;
                default:
                    break;
            }
        }
        else {
            System.out.println("Invalid input. Returning back to Order UI...");
            return;
        }


    }

    public static void changeStaff(Order order){
        System.out.println("Select a staff: ");
        StaffManager.printStaffList();
        int choice=IntegerInputHelper.validateInput(1,Restaurant.getStafflist().size());
        while (choice<=0||choice>Restaurant.getStafflist().size()){
            System.out.println("Invalid selection. Please select again.");
            choice=IntegerInputHelper.validateInput(1,Restaurant.getStafflist().size());
        }
        OrderManager.updateStaff(order,choice);
        System.out.println("Staff updated successfully!");
    }

    public static void changeTable(Order order){
        System.out.println("Select a table: ");
        ArrayList<Table> tables = TableManager.getAvailableTablesLessCurrent(order.getNumOfPax(), order.getTable().getTableID());
        if (tables == null) return;
        TableManager.printAvailableTables(tables);
        int choice=IntegerInputHelper.validateInput(tables);
        // while (choice<=0||choice>Restaurant.tablelist.size()){
        //     System.out.println("Invalid selection. Please select again.");
        //     choice=IntegerInputHelper.validateInput(1,Restaurant.tablelist.size());
        // }
        // if (Restaurant.tablelist.get(choice-1).checkAvailability()){
        //     System.out.println("The table is occupied");
        //     return;
        // }
        // if(!TableManager.validate(order.getNumOfPax(), choice)){
        //     System.out.println("The table is not big enough.");
        //     return;
        // };
        OrderManager.updateTable(order,choice);
        System.out.println("Table updated successfully!");
    }

    public static void printOrderInvoice(){
        System.out.println("Choose an order: ");
        OrderManager.printAllOrders();
        int choice=IntegerInputHelper.validateInput(1,Restaurant.getOrderList().size());
        OrderManager.printInvoice(choice);

    }

    public static void updateItems(Order order){
        System.out.println("Select an option: ");
        System.out.println("1. add new item");
        System.out.println("2. remove item");
        int selection=IntegerInputHelper.validateInput(0,2);
        switch (selection){
            case 1:
                OrderManager.addItemsToOrder(order);
                break;
            case 2:
                OrderManager.removeItemFromOrder(order);
                break;
            default:
                break;
        }
    }

    public static void updateMembership(Order order){
        Scanner sc=new Scanner(System.in);
        System.out.println("Does the customer has membership? ('1' for 'Yes','0' for 'No') ");
        boolean membership=false;
        int m=IntegerInputHelper.validateInput(0,1);

        if (m==1){
            membership=true;
        }
        else if(m==0){
            membership=false;
        }
        OrderManager.updateMembership(order,membership);
    }

    public static void removeOrder(){
        System.out.println("Select an order to remove: ");
        OrderManager.printAllOrders();
        int choice;
        choice=IntegerInputHelper.validateInput(1,Restaurant.getOrderList().size());
        OrderManager.removeOrder(choice);
    }

    public static void viewOrders(){
        OrderManager.printAllOrders();
    }


}
