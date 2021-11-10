package Controller;

import Entities.ALaCarte;
import Entities.MenuItem;
import Entities.Restaurant;
import Entities.SetPackage;
import Enums.FoodType;

import java.util.*;
//import javax.lang.model.util.ElementScanner14;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.attribute.ResolutionSyntax;

public class MenuItemManager {
    //add a lar carte item
    public static void addNewItem(String name, String description, double price, FoodType ft){
        MenuItem newItem=new ALaCarte(name,description,price,ft);
        if(checkDuplicity(name)){
            System.out.println("Item already exists!");
            return;
        }
        int numOfMain = 0, numOfDrinks = 0, numOfDesserts = 0;
        for (MenuItem mi: Restaurant.menulist) {
            if (mi instanceof ALaCarte) {
                if (((ALaCarte) mi).getFoodType().equals(FoodType.MAIN)) numOfMain++;
                else if (((ALaCarte) mi).getFoodType().equals(FoodType.DRINKS)) numOfDrinks++;
                else numOfDesserts++;
            }
        }
        if (ft.equals(FoodType.MAIN)) Restaurant.menulist.add(numOfMain, newItem);
        else if (ft.equals(FoodType.DRINKS)) Restaurant.menulist.add(numOfMain + numOfDrinks, newItem);
        else if (ft.equals(FoodType.DESSERT)) Restaurant.menulist.add(numOfMain + numOfDrinks + numOfDesserts, newItem);
        else Restaurant.menulist.add(newItem);
        System.out.println("A lar carte Item "+ name+" added successfully!");

    }

    //add new set package
    public static void addNewItem(String name, String description, double price){
        MenuItem newItem=new SetPackage(name,description,price);
        if(checkDuplicity(name)){
            System.out.println("Item already exists!");
            return;
        }
        addItemToSetPackage((SetPackage) newItem);
        Restaurant.menulist.add(newItem);
        System.out.println("Set package Item "+ name+" added successfully!");
    }

    public static boolean checkDuplicity(String name){
        for(int i=0;i<Restaurant.menulist.size();i++){
            MenuItem currentItem=Restaurant.menulist.get(i);
            String curName=currentItem.getName();
            if(curName.equals(name)){
                return true;
            }
        }
        return false;
    }

    public static void deleteMenuItem(int index){
        if(index>0&&index<=Restaurant.menulist.size()){
            MenuItem itemToBeRemoved=Restaurant.menulist.get(index-1);
            String name=itemToBeRemoved.getName();
            Restaurant.menulist.remove(index-1);
            System.out.println("Item "+ name+" removed successfully!");
        }
        else {
            System.out.println("Invalid index.");
        }
    }

    public static void addItemToSetPackage(SetPackage setPackage){
        System.out.println("Choose a item to add: (press '0' to stop)");
        printALaCarteMenu();
        Scanner sc=new Scanner(System.in);
        int choice;
        choice=sc.nextInt();
        while (choice>0&&choice<=Restaurant.menulist.size()){
            MenuItem item=Restaurant.menulist.get(choice-1);
            if (item instanceof ALaCarte){
                setPackage.addItem(item);
                System.out.println(item.getName()+" added successfully.");
            }
            else {
                System.out.println("Not in a la carte menu.");
            }
            System.out.println("Choose a item to add: (press '0' to stop)");
            choice=sc.nextInt();
        }
    }

    public static void removeItemFromSetPackage(SetPackage setPackage){
        System.out.println("Choose a item to remove: (press '0' to stop)");
        setPackage.printItems();
        Scanner sc=new Scanner(System.in);
        int choice;
        choice=sc.nextInt();
        while (choice>0&&choice<=setPackage.getPackageList().size()){
            setPackage.removeItem(choice);
            System.out.println("Item removed successfully.");
            System.out.println("Choose a item to remove: (press '0' to stop)");
            setPackage.printItems();
            choice=sc.nextInt();
        }
    }

    public static void updateItem(int index){
        MenuItem itemToUpdate=Restaurant.menulist.get(index-1);
        if (itemToUpdate instanceof ALaCarte){
            updateALC(itemToUpdate);
        }
    }

    public static void updateALC(MenuItem item){

    }

    public static void updateName(int itemIdx,String name){
        MenuItem item=Restaurant.menulist.get(itemIdx-1);
        item.setName(name);
    }

    public static void updateDescription(int itemIdx,String des){
        MenuItem item=Restaurant.menulist.get(itemIdx-1);
        item.setDescription(des);
    }

    public static void updatePrice(int itemIdx,double price){
        MenuItem item=Restaurant.menulist.get(itemIdx-1);
        item.setPrice(price);
    }

    public static void updateFoodType(int itemIdx,FoodType ft){
        ALaCarte item= (ALaCarte) Restaurant.menulist.get(itemIdx-1);
        item.setType(ft);
        Restaurant.menulist.remove(item);
        addNewItem(item.getName(), item.getDescription(), item.getPrice(), ft);
    }

    public static void printFullMenu(){
        if (Restaurant.menulist.size()==0){
            System.out.println("Menu is empty!");
            return;
        }
        System.out.println("***********************A LA CARTE*********************");
        printALaCarteMenu();
        System.out.println();
        System.out.println("***********************SET PACKAGES*********************");
        printSetPackageMenu();
        System.out.println();
    }

    public static void printALaCarteMenu(){
        if (Restaurant.menulist.size()==0){
            System.out.println("Menu is empty!");
            return;
        }
        ArrayList<MenuItem> main = new ArrayList<MenuItem>();
        ArrayList<MenuItem> drinks = new ArrayList<MenuItem>();
        ArrayList<MenuItem> dessert = new ArrayList<MenuItem>();
        main = getMainItems();
        drinks = getDrinkItems();
        dessert = getDessertItems();
        System.out.println("**********MAINS***********");
        int temp, i = 0;
        while (i < main.size()) {
            MenuItem mi = main.get(i);
            System.out.print("INDEX "+ (i+1));
            mi.printInfo();
            System.out.println();
            i++;
        }
        temp = i;
        System.out.println("**********DRINKS***********");
        while (i < temp + drinks.size()) {
            MenuItem mi = drinks.get(i-main.size());
            System.out.print("INDEX "+ (i+1));
            mi.printInfo();
            System.out.println();
            i++;
        }
        temp = i;
        System.out.println("**********DESSERTS***********");
        while (i < temp + dessert.size()) {
            MenuItem mi = dessert.get(i - main.size() - drinks.size());
            System.out.print("INDEX "+ (i+1));
            mi.printInfo();
            System.out.println();
            i++;
        }
    }

    // System.out.print("INDEX "+i+1);
    // currentItem.printInfo();
    // System.out.println();

    public static void printSetPackageMenu(){
        if (Restaurant.menulist.size()==0){
            System.out.println("Menu is empty!");
            return;
        }
        for(int i = 0; i < Restaurant.menulist.size(); i++) {
            MenuItem mi = Restaurant.menulist.get(i);
            if(mi instanceof SetPackage){
                System.out.print("INDEX "+ (i + 1));
                mi.printInfo();
                System.out.println();}
        }
    }

    public static ArrayList<MenuItem> getMainItems(){
        ArrayList<MenuItem> mainItems=new ArrayList<MenuItem>();
        for(MenuItem item:Restaurant.menulist){
            if(item instanceof ALaCarte){
                if(((ALaCarte) item).getFoodType().equals(FoodType.MAIN)){
                    mainItems.add(item);
                }
            }
        }
        return mainItems;
    }


    public static ArrayList<MenuItem> getDessertItems(){
        ArrayList<MenuItem> dessertItems=new ArrayList<MenuItem>();
        for(MenuItem item:Restaurant.menulist){
            if(item instanceof ALaCarte){
                if(((ALaCarte) item).getFoodType().equals(FoodType.DESSERT)){
                    dessertItems.add(item);
                }
            }
        }
        return dessertItems;

    }

    public static ArrayList<MenuItem> getDrinkItems(){
        ArrayList<MenuItem> drinkItems=new ArrayList<MenuItem>();
        for(MenuItem item:Restaurant.menulist){
            if(item instanceof ALaCarte){
                if(((ALaCarte) item).getFoodType().equals(FoodType.DRINKS)){
                    drinkItems.add(item);
                }
            }
        }
        return drinkItems;

    }

}
