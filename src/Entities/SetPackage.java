package Entities;

import java.util.ArrayList;
import Enums.FoodType;
import java.util.Scanner;
public class SetPackage extends MenuItem{

    //list to store the food in a set
    private ArrayList<MenuItem> list;
    Scanner sc = new Scanner(System.in);

    public SetPackage(){

    }

    //constructor
    public SetPackage(String name, String description, double price) {
        super(name, description, price);
        this.list = new ArrayList<MenuItem>();
    }
    //returns current list
    public ArrayList<MenuItem> getPackageList() {
        return this.list;
    }

    //adds food items to the set package
    public void addItem(MenuItem food) {
        this.list.add(food);
    }

    //remove food items from the set package
    public void removeItem(int choice){
        String removed = this.list.get(choice - 1).getName();
        this.list.remove(choice-1);
        System.out.println("Item "+ removed + " successfully removed!");
    }

    public void printInfo(){
        System.out.println("*** SET PACKAGE ***");
        System.out.println("Name: "+this.getName());
        System.out.println("Description: "+this.getDescription());
        System.out.println("Price: "+this.getPrice());
        printItems();
    }

    public void printItems(){
        if (list.size()==0){
            System.out.println("Package is empty!");
            return;
        }
        System.out.println("*** Items in Set Package "+getName()+" ***");
        for (int i=0;i<list.size();i++){
            ALaCarte p= (ALaCarte) list.get(i);
            System.out.print("INDEX "+(i+1));
            System.out.println(" Name: "+p.getName());
            System.out.println(" Description: "+p.getDescription());
            System.out.println(" Price: "+ String.format("%.2f", p.getPrice()));
            System.out.println(" Food Type: "+p.getFoodType());
            System.out.println();
        }
        System.out.println();
    }


//    public void removeItem() {
//        int index = 0;
//        int choice;
//        if(this.list.size() < 1) {
//            System.out.println("The set package is currently empty!");
//            return;
//        }
//        System.out.println("The set package is"+ this.getName());
//        System.out.println("Which item would you like to remove?");
//        for(MenuItem food: this.list) {
//            System.out.println("["+(index++ + 1)+ "] - "+ food.getName());
//        }
//        choice = sc.nextInt();
//
//        String removed = this.list.get(choice - 1).getName();
//        this.list.remove(choice-1);
//        System.out.println("Item "+ removed + " successfully removed!");
//
//    }

//    public void addFood(ArrayList<MenuItem> menuList) {
//        int index = 0;
//        int i = 0;
//        ArrayList<Integer> mapping = new ArrayList<Integer>();
//
//        if (menuList.size() < 1) {
//            System.out.println("Unable to add items: No items in the menuList currently!");
//            return;
//        }
//
//        System.out.println();
//        System.out.println("Enter the index of item to add it into the order");
//
//        for(; i < menuList.size(); i++) {
//            if (menuList.get(i) instanceof MenuItem) {
//                System.out.println("[" + (index++ + 1) + "] - " + menuList.get(i).getName());
//                mapping.add(i);
//            }
//        }
//
//        System.out.println();
//        Scanner sc = new Scanner(System.in);
//        int choice = sc.nextInt();
//
//        MenuItem foodToAdd = (MenuItem) menuList.get(mapping.get(choice - 1));
//        this.list.add(foodToAdd);
//        System.out.println(foodToAdd.getName() + " was successfully add to " + this.getName() + " Set Package");
//
//    }

}