package UI;

import Controller.MenuItemManager;
import Controller.OrderManager;
import Controller.StaffManager;
import Controller.TableManager;
import Entities.Order;
import Entities.Restaurant;
import Entities.Staff;

import java.sql.Timestamp;
import java.util.Scanner;

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
        Scanner sc = new Scanner(System.in);
        int option;
        option=sc.nextInt();
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
            option=sc.nextInt();
        }
        sc.close();
    }

    public static void addNewOrder(){
        System.out.println("Choose the staff who is creating this order: ");
        StaffManager.printStaffList();
        Scanner sc = new Scanner(System.in);
        int staffChoice;
        staffChoice=sc.nextInt();
        while (staffChoice<=0||staffChoice> Restaurant.stafflist.size()){
            System.out.println("Staff does not exist. Please enter again: ");
            staffChoice=sc.nextInt();
        }
        System.out.println("Enter number of pax: ");
        int numOfPax;
        numOfPax=sc.nextInt();
        while(numOfPax<=0){
            System.out.println("Invalid input. Please enter an integer larger than 0.");
            numOfPax=sc.nextInt();
        }
        System.out.println("Choose the table of this order: ");
        if(TableManager.getAvailableTables()==null){
            return;
        }
        TableManager.printAvailableTables();
        int tableChoice;
        tableChoice=sc.nextInt();
        boolean result=TableManager.validate(numOfPax,tableChoice);
        while (result==false){
            System.out.println("This table cannot be chosen. Please make another choice: ");
            tableChoice=sc.nextInt();
            result=TableManager.validate(numOfPax,tableChoice);
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Does the customer has membership? ('1' for 'Yes','0' for 'No') ");
        boolean membership=false;
        int m=sc.nextInt();
        while (m!=1&&m!=0){
            System.out.println("Invalid input. Please enter an integer value of 0 or 1.");
            m=sc.nextInt();
        }
        if (m==1){
            membership=true;
        }
        else if(m==0){
            membership=false;
        }
        OrderManager.addNewOrder(staffChoice,numOfPax,tableChoice,timestamp,membership);


    }

    public static void updateOrder(){
        System.out.println("Which order do you want to update?");
        OrderManager.printAllOrders();
        if(Restaurant.orderList.size()==0)
            return;
        Scanner sc = new Scanner(System.in);
        int choice=sc.nextInt();
        if(choice>=0&&choice<=Restaurant.orderList.size()){
            Order curOrder=Restaurant.orderList.get(choice-1);
            System.out.println("Select an option");
            System.out.println("1. update staff ");
            System.out.println("2. update table ");
            System.out.println("3. update ordered items ");
            System.out.println("4. update membership ");
            System.out.println("0. Exit ");
            switch (choice){
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


    }

    public static void changeStaff(Order order){
        System.out.println("Select a staff: ");
        StaffManager.printStaffList();
        Scanner sc=new Scanner(System.in);
        int choice=sc.nextInt();
        while (choice<=0||choice>Restaurant.stafflist.size()){
            System.out.println("Invalid selection. Please select again.");
            choice=sc.nextInt();
        }
        OrderManager.updateStaff(order,choice);
        System.out.println("Staff updated successfully!");
    }

    public static void changeTable(Order order){
        System.out.println("Select a table: ");
        TableManager.printAvailableTables();
        Scanner sc=new Scanner(System.in);
        int choice=sc.nextInt();
        while (choice<=0||choice>Restaurant.tablelist.size()){
            System.out.println("Invalid selection. Please select again.");
            choice=sc.nextInt();
        }
        if (Restaurant.tablelist.get(choice-1).checkAvailability()){
            System.out.println("The table is occupied");
            return;
        }
        if(!TableManager.validate(order.getNumOfPax(), choice)){
            System.out.println("The table is not big enough.");
            return;
        };
        OrderManager.updateTable(order,choice);
        System.out.println("Staff updated successfully!");
        sc.close();
    }

    public static void updateItems(Order order){
        System.out.println("Select an option: ");
        System.out.println("1. add new item");
        System.out.println("2. remove item");
        Scanner sc=new Scanner(System.in);
        int selection=sc.nextInt();
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
        int m=sc.nextInt();
        while (m!=1&&m!=0){
            System.out.println("Invalid input. Please enter an integer value of 0 or 1.");
            m=sc.nextInt();
        }
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
        Scanner sc=new Scanner(System.in);
        choice=sc.nextInt();
        OrderManager.removeOrder(choice);
    }

    public static void viewOrders(){
        OrderManager.printAllOrders();
    }


}
