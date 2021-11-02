package Entities;

import Enums.FoodType;

public class ALaCarte extends MenuItem{
    private FoodType foodType;
    public ALaCarte(){}
    public ALaCarte(String name, String description, double price, FoodType foodtype){
        super(name, description, price);
        this.foodType=foodtype;
    }

    public void setType(FoodType foodtype) {
        this.foodType = foodtype;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void printInfo(){
        System.out.println("*** A LAR CARTE ***");
        System.out.println("Name: "+this.getName());
        System.out.println("Description: "+this.getDescription());
        System.out.println("Price: "+this.getPrice());
        System.out.println("Food type: "+this.getFoodType());
    }
}
