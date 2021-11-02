import Controller.OrderManager;
import Controller.TableManager;
import Entities.Order;
import Entities.Reservation;
import Entities.Restaurant;
import UI.MainUI;
import UI.MenuUI;
import UI.OrderUI;
import UI.ReservationUI;

import java.util.Scanner;

public class RRPSSApp {
    public static void main(String[] args){

        System.out.println("====== Welcome to Restaurant Reservation and Point of Sale System! ====== ");
        System.out.println("System initializing....");

        Restaurant.initialRestaurant();   //initialize restaurant

        MainUI.displayOptions();
        int option=0;
        Scanner sc= new Scanner(System.in);
        option=sc.nextInt();
        while(option>=1&&option<=6){
            switch (option){
                case 1:
                    MenuUI.menuUI();
                    break;
                case 2:
                    OrderUI.orderUI();
                    break;
                case 3:
                    ReservationUI.reservationUI();
                    break;
                case 4:
                    TableManager.printAvailableTables();
                    break;
                case 5:
                    OrderUI.printOrderInvoice();
                    break;
                case 6:


            }
            option=sc.nextInt();
        }


    }

}
