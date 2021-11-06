import Controller.OrderManager;
import Controller.TableManager;
import Controller.ReservationManager;
import Entities.Order;
import Entities.Reservation;
import Entities.Restaurant;
import Entities.Table;
import UI.*;

import java.util.*;

public class RRPSSApp {
    public static void main(String[] args){

        System.out.println("====== Welcome to Restaurant Reservation and Point of Sale System! ====== ");
        System.out.println("System initializing....");

        Restaurant.initialRestaurant();   //initialize restaurant

        Queue<Reservation> q = new LinkedList<Reservation>(); // initialise queue to store recently expired reservations
        
        TimerTask task = new TimerTask() { // to check expiry
            public void run() {
                ReservationManager.clearExpiredReservations(Restaurant.tablelist, q);
            }
        };

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(task, 0, 1000); // check expiry every second
        

        MainUI.displayOptions();
        int option=0;
        Scanner sc= new Scanner(System.in);
        option=sc.nextInt();
        while(option>=1&&option<=7){
            switch (option){
                case 1:
                    MenuUI.menuUI();
                    break;
                case 2:
                    OrderUI.orderUI();
                    break;
                case 3:
                    ReservationUI.reservationUI(q);
                    break;
                case 4:
                    TableManager.printAvailableTables();
                    break;
                case 5:
                    OrderUI.printOrderInvoice();
                    break;
                case 6:
                    GenerateReportUI.generateReportUI();
                    break;
                case 7:
                    StaffUI.staffUI();
                    break;
                default:
                    System.out.println("Thank you for using this system.");
                    break;


            }
            MainUI.displayOptions();
            option=sc.nextInt();
        }


    }

}
