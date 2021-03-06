package Entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Order{
    private boolean membership = false;
    private int orderID = -1;
    private int numOfPax;
    private double price;
    private Staff staff;
    private ArrayList<MenuItem> orderItems;
    private Map<String,Integer> quantityMap;
    //private ArrayList<MenuItem> itemInfo;		//itemInfo is to store one food item into a array list
    //private Timestamp timeStamp;				//record the time for updating the order
    private Table table;
    private boolean isCompleted = false;
    // private LocalDate date;
    // public LocalTime time;
    private LocalDateTime timestamp;


    public Order(int orderID, Staff staff, Table table, boolean membership,int numOfPax, LocalDateTime timestamp){

        this.orderID = orderID;
        this.table = table;
        this.membership = membership;
        this.staff = staff;
        this.orderItems = new ArrayList<MenuItem>();
        this.quantityMap = new HashMap<String,Integer>();
        this.price=0;
        this.numOfPax=numOfPax;
        this.timestamp = timestamp;
        isCompleted = false;
        table.setOccupied(true);
        //itemInfo = null;
    }

    public int getOrderID(){return orderID;}
    public Staff getStaff(){return staff;}

    public void setNumOfPax(int numOfPax) {
        this.numOfPax = numOfPax;
    }

    public int getNumOfPax() {
        return numOfPax;
    }
    public void setTimeStamp(LocalDateTime s){
        this.timestamp=s;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public ArrayList<MenuItem> getOrderItems(){return orderItems;}
    public Map<String, Integer> getQuantityMap() {
        return quantityMap;
    }
    public boolean getMembership(){return membership;}

    public void setMembership(boolean membership) {
        this.membership = membership;
    }

    public boolean isCompleted() {return isCompleted;}

    public void setTable(Table table) {
        this.table = table;
    }

    public Table getTable() {return table;}
    public LocalDateTime getTimeStamp(){return timestamp;}

    public void setOrderItems(ArrayList<MenuItem> orderItems){this.orderItems = orderItems;}
    public void setCompleted(){
        isCompleted = true;
        
    }

    public double calculatePrice() {
        double totalPrice = 0;
        for(int i =0;i<orderItems.size();i++){
            MenuItem temp = orderItems.get(i);
            int qty = quantityMap.get(temp.getName());
            double tempPrice = (double)temp.getPrice()*qty;
            totalPrice += tempPrice;
        }
        double gst = totalPrice - totalPrice/1.07;
        totalPrice += gst;
        if(membership) {
            totalPrice = totalPrice*0.95;
        }
        price=totalPrice;
        return price;
    }

    public void addFood(MenuItem food, int quantity) {
          String itemName=food.getName();
          if (quantityMap.get(itemName)!=null){  //if an item already exists in the item list
              quantityMap.put(itemName,quantityMap.get(itemName)+quantity);
              calculatePrice();
              System.out.println("Item "+itemName+"| quantity updated from"+quantityMap.get(itemName)+" to "+quantityMap.get(itemName)+quantity+" successfully.");
          }
          else{
              quantityMap.put(itemName,quantity);
              orderItems.add(food);
              calculatePrice();
              System.out.println("Item "+itemName+"| quantity "+quantity+" added successfully.");
          }
//        int index = 0;
//        ArrayList<Integer> mapping = new ArrayList<Integer>();
//        ArrayList<MenuItem> addedItem=new ArrayList<MenuItem>();
//
//        if (menuList.size() < 1) {
//            System.out.println("Unable to add items: No items in the menuList currently!"); //can add an exception
//            return;
//        }
//
//        System.out.println();
//        System.out.println("Enter the index of item to add it into the order");
//
//        //print the menu list
//        for(int i=0; i < menuList.size(); i++) {
//            if (menuList.get(i) instanceof MenuItem) {
//                System.out.println("[" + (index++ + 1) + "] - " + menuList.get(i).getName());
//                mapping.add(i);
//            }
//        }
//        System.out.println();
//        Scanner sc = new Scanner(System.in);
//        int choice = sc.nextInt(1, index);
//
//        MenuItem foodToAdd = (MenuItem) menuList.get(mapping.get(choice - 1));
//        addedItem.add(foodToAdd);
    }

    public void removeItem(int idx, int quantity){
        MenuItem itemToRemove=orderItems.get(idx-1);
        int curQ=quantityMap.get(itemToRemove.getName());
        if(curQ>quantity){
            quantityMap.put(itemToRemove.getName(),curQ-quantity);
            System.out.println("Item "+itemToRemove.getName()+" quantity is changed from "+curQ+" to "+(curQ-quantity));
        }
        else if(curQ==quantity){
            quantityMap.put(itemToRemove.getName(),curQ-quantity);
            orderItems.remove(idx-1);
            System.out.println("Item "+itemToRemove.getName()+" is removed.");
        }
        else{
            System.out.println("Error. Not Enough quantity.");
            return;
        }
    }

    public void printInfo(){
        System.out.println("Order ID: "+ orderID);
        System.out.println("Table number: "+table.getTableID());
        System.out.println("Membership status: "+membership);
        System.out.println("Timestamp: "+ timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("IsCompleted: "+isCompleted);
        System.out.println("Total price: "+ String.format("%.2f", price));
        System.out.println("Staff info: ");
        printItemsInOrder();
        staff.printStaffInfo();
    }

    public void printItemsInOrder(){
        if(orderItems.size()==0){
            System.out.println("No item is in the order.");
            return;
        }
        System.out.println("----- Items in order -----");
        System.out.println(String.format("%-50s | %-10s", "Menu Item", "Qty"));
        for(int i=0;i<orderItems.size();i++){
            MenuItem curItem=orderItems.get(i);
            int qty=this.getQuantityMap().get(curItem.getName());
            System.out.println("INDEX " + (i+1));
            System.out.println(String.format("%-50s | %-10d", curItem.getName(), qty));
            System.out.println();
        }
        System.out.println("-----------------");
    }

    public int getMonth(){
        return timestamp.getMonthValue();
    }

    public int getYear(){
        return timestamp.getYear();
    }

}