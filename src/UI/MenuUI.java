package UI;

import Controller.IntegerInputHelper;
import Controller.MenuItemManager;
import Entities.ALaCarte;
import Entities.MenuItem;
import Entities.Restaurant;
import Entities.SetPackage;
import Enums.FoodType;

import java.util.Scanner;

public class MenuUI {
    public static void displayOptions(){
        System.out.println("===== Menu Management ===== ");
        System.out.println("1. Add New Menu Item ");
        System.out.println("2. Update Menu Item ");
        System.out.println("3. Remove Menu Item");
        System.out.println("4. View Menu Items");
        System.out.println("0. Exit Order Management");
    }

    public static void menuUI(){
        displayOptions();
        int option= IntegerInputHelper.validateInput(0,4);
        while (option<=4&&option>=1){
            switch (option){
                case 1:
                    addNewItem();
                    break;
                case 2:
                    updateItem();
                    break;
                case 3:
                    removeItem();
                    break;
                case 4:
                    viewMenu();
                    break;
                default:
                    return;

            }
            displayOptions();
            //sc.nextLine();
            option=IntegerInputHelper.validateInput(0,4);
        }
    }

    public static void addNewItem(){
        Scanner sc2 = new Scanner(System.in);
        int selection;
        System.out.println("Select '1' for a la carte item, Select '2' for set package item, select '0' to return.");
        selection=IntegerInputHelper.validateInput(0,2);
        switch (selection){
            case 0:
                return;
            case 1:
                System.out.println("Please enter the name of the a la carte item: ");
                String name=sc2.nextLine();
                System.out.println("Please enter the description of the a la carte item: ");
                String description=sc2.nextLine();
                System.out.println("Please enter the price of the a la carte item: ");
                double price=sc2.nextDouble();
                System.out.println("Please choose the food type of the a la carte item : ");
                int k=0;
                FoodType foodType;
                int i=1;
                for(FoodType type:FoodType.values()){
                    System.out.print("["+i+"]");
                    System.out.println(type);
                    i++;
                }
                k=sc2.nextInt();
                if(k>=1&&k<=FoodType.values().length){
                    foodType=FoodType.values()[k-1];
                }
                else {
                    System.out.println("Invalid input. Item is not created successfully.");
                    return;
                }
                MenuItemManager.addNewItem(name,description,price,foodType);
                break;
            case 2:
                System.out.println("Please enter the name of the set package item: ");
                name=sc2.nextLine();
                System.out.println("Please enter the description of the set package item: ");
                description=sc2.nextLine();
                System.out.println("Please enter the price of the set package item: ");
                price=sc2.nextDouble();
                MenuItemManager.addNewItem(name,description,price);
                break;
            default:
                System.out.println("Invalid input. Return to Menu UI.");
                return;
        }
    }

    public static void updateItem(){
        MenuItemManager.printFullMenu();
        System.out.println("Which menu item do you want to update? ");
        if(Restaurant.getMenulist().size()==0)
            return;
        Scanner sc=new Scanner(System.in);
        int choice;
        choice=IntegerInputHelper.validateInput(1,Restaurant.getMenulist().size());
        if (choice>0&&choice<=Restaurant.getMenulist().size()){
            MenuItem cur=Restaurant.getMenulist().get(choice-1);
            System.out.println("Select an option");
            System.out.println("1. update name ");
            System.out.println("2. update description ");
            System.out.println("3. update price ");
            if(cur instanceof ALaCarte)
                System.out.println("4. update food type");
            else if(cur instanceof SetPackage)
            {System.out.println("4. add item to this setPackage");
                System.out.println("5. remove item from this setPackage");}
            int selection;
            selection=IntegerInputHelper.validateInput(1,5);
            switch (selection){
                case 1:
                    System.out.println("Enter new name: ");
                    // sc.nextLine();
                    String newName=sc.nextLine();
                    MenuItemManager.updateName(choice,newName);
                    System.out.println("Item updated successfully.");
                    break;
                case 2:
                    System.out.println("Enter new description: ");
                    // sc.nextLine();
                    String newDes=sc.nextLine();
                    MenuItemManager.updateDescription(choice,newDes);
                    System.out.println("Item updated successfully.");
                    break;
                case 3:
                    System.out.println("Enter new price: ");
                    double price=sc.nextDouble();
                    MenuItemManager.updatePrice(choice,price);
                    System.out.println("Item updated successfully.");
                    break;
                case 4:
                    if (cur instanceof ALaCarte) {
                        System.out.println("Select new food type: ");
                        FoodType foodType=FoodType.MAIN;
                        for(int i = 0; i < FoodType.values().length; i++){
                            System.out.println("[" + (i+1) + "] " + FoodType.values()[i]);
                        }
                        int k=IntegerInputHelper.validateInput(1,FoodType.values().length);
                        if(k>=1&&k<=FoodType.values().length){
                            foodType=FoodType.values()[k-1];
                        }
                        MenuItemManager.updateFoodType(choice,foodType);
                        System.out.println("Item updated successfully.");
                        break;
                    }
                    MenuItemManager.addItemToSetPackage((SetPackage) cur);
                    break;
                case 5:
                    if (cur instanceof SetPackage) {
                        MenuItemManager.removeItemFromSetPackage((SetPackage) cur);
                        break;
                    }
                    System.out.println("Invalid value entered");
                    break;
                default:
                    System.out.println("Invalid value entered");
            }

        }
    }

    public static void removeItem(){
        MenuItemManager.printFullMenu();
        System.out.println("Select an item to remove: ");
        int choice;
        choice=IntegerInputHelper.validateInput(1,Restaurant.getMenulist().size());
        MenuItemManager.deleteMenuItem(choice);
    }

    public static void viewMenu(){
        MenuItemManager.printFullMenu();
    }
    
}
