package Entities;
import Enums.FoodType;

public class MenuItem {
    private String name;
    private String description;
    private double price;
    //private FoodType foodtype;

    public MenuItem(){}
    public MenuItem(String name, String description, double price) {
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
        //this.setType(foodtype);
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(double price) {
        this.price = price;
    }
//    public void setType(FoodType foodtype) {
//        this.foodtype = foodtype;
//    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public double getPrice() {
        return this.price;
    }
//    public FoodType getType() {
//        return this.foodtype;
//    }
    public void printInfo(){}

}
