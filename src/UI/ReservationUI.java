package UI;

import Controller.IntegerInputHelper;
import Controller.LocalDateInputHelper;
import Controller.ReservationManager;
import Controller.TableManager;
import Entities.Reservation;
import Entities.Restaurant;
import Entities.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class ReservationUI {
    public static void displayOptions(){
        System.out.println("===== Reservation Management ===== ");
        System.out.println("1. Add New Reservation ");
        System.out.println("2. Update Reservation ");
        System.out.println("3. Remove Reservation");
        System.out.println("4. View Reservations");
        System.out.println("5. View last 3 expired reservations");
        System.out.println("0. Exit Order Management");
    }

    public static void reservationUI(Queue<Reservation> q){
        displayOptions();
        int option;
        option= IntegerInputHelper.validateInput(0,5);
        while (option<=5&&option>=1){
            switch (option){
                case 1:
                    addNewReservation();
                    break;
                case 2:
                    updateReservation();
                    break;
                case 3:
                    removeReservation();
                    break;
                case 4:
                    viewReservations();
                    break;
                case 5:
                    viewExpiredReservations(q);
                    break;
                default:
                    return;

            }
            displayOptions();
            option=IntegerInputHelper.validateInput(0,5);
        }
    }
    public static void addNewReservation(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter name of the customer: ");
        String name=sc.nextLine();
        System.out.println("Enter contact number: ");
        String contactNum=sc.nextLine();
        System.out.println("Enter number of pax: (min 2 pax, max 10 pax)");
        int numOfPax=IntegerInputHelper.validateInput(2,10);
        System.out.println("Enter reservation date: (in YYYY-MM-DD format)");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date= LocalDateInputHelper.validateInput(dateFormatter);
        System.out.println("Please choose time slot: ");
        System.out.println("[1] 11:00-12:00 ");
        System.out.println("[2] 12:00-13:00 ");
        System.out.println("[3] 13:00-14:00 ");
        System.out.println("[4] 17:00-18:00 ");
        System.out.println("[5] 18:00-19:00 ");
        System.out.println("[6] 19:00-20:00 ");
        int choiceOfTime=IntegerInputHelper.validateInput(1,6);
        LocalTime time= generateTime(choiceOfTime);
        int result=ReservationManager.addNewReservation(name,contactNum,numOfPax,date,time,false);
        if(result==1){
            System.out.println("Reservation is added successfully.");
        }

    }

    public static LocalTime generateTime(int choice){
        LocalTime time=LocalTime.of(00,00);
        switch (choice){
            case 1:
                time=LocalTime.of(11,00);
                break;
            case 2:
                time=LocalTime.of(12,00);
                break;
            case 3:
                time=LocalTime.of(13,00);
                break;
            case 4:
                time=LocalTime.of(17,00);
                break;
            case 5:
                time=LocalTime.of(18,00);
                break;
            case 6:
                time=LocalTime.of(19,00);
                break;
            default:
                break;
        }
        return time;
    }

    public static void updateReservation(){
        if(Restaurant.reservationList.size()==0){
            System.out.println("No reservation is in the list!" );
        }
        Scanner sc=new Scanner(System.in);
        System.out.println("Choose a reservation to update:" );
        ReservationManager.printValidReservations();
        int choice=sc.nextInt();
        if (choice>0&&choice<=Restaurant.reservationList.size()){
            Reservation curReservation=Restaurant.reservationList.get(choice-1);
            System.out.println("Select an option: ");
            System.out.println("[1] Update customer name ");
            System.out.println("[2] Update contact number ");
            System.out.println("[3] Change date and time");
            System.out.println("[0] Exit ");
            int selection=IntegerInputHelper.validateInput(0,3);
            switch (selection){
                case 1:
                    updateName(curReservation);
                    break;
                case 2:
                    updateContactNumber(curReservation);
                    break;
                case 3:
                    updateTime(curReservation);
                    break;
                default:
                    break;
            }

        }
        else {
            System.out.println("Invalid choice. Returning back to Reservation UI...");
        }


    }

    public static void updateName(Reservation r){
        Scanner sc= new Scanner(System.in);
        System.out.println("Current customer name: "+r.getName());
        System.out.println("Enter new name: ");
        String newName=sc.nextLine();
        ReservationManager.updateName(r,newName);

    }

    public static void updateContactNumber(Reservation r){
        Scanner sc=new Scanner(System.in);
        System.out.println("Current contact number: "+r.getContactNumber());
        System.out.println("Enter new contact number: ");
        String newContact=sc.nextLine();
        ReservationManager.updateContact(r,newContact);

    }

    public static void updateTime(Reservation r){
        Scanner sc=new Scanner(System.in);
        System.out.println("Current Date: "+r.getLocaldate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("Current Time: "+r.getLocaltime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println("Enter new reservation date: (in YYYY-MM-DD format)");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate newDate=LocalDateInputHelper.validateInput(dateFormatter);

        System.out.println("Please choose a new time slot: ");
        System.out.println("[1] 11:00-12:00 ");
        System.out.println("[2] 12:00-13:00 ");
        System.out.println("[3] 13:00-14:00 ");
        System.out.println("[4] 17:00-18:00 ");
        System.out.println("[5] 18:00-19:00 ");
        System.out.println("[6] 19:00-20:00 ");
        int choiceOfTime=IntegerInputHelper.validateInput(1,6);
        LocalTime newTime= generateTime(choiceOfTime);
        ReservationManager.updateTable(r,newDate,newTime);
    }

    public static void removeReservation(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the index of the reservation you would like to cancel: ");
        ReservationManager.printValidReservations();
        int choice=sc.nextInt();
        ReservationManager.cancelReservation(choice);


    }
    public static void viewReservations(){
        ReservationManager.printValidReservations();
    }

    public static void viewExpiredReservations(Queue<Reservation> q) {
        if (q.size() == 0)
            System.out.println("No recent expired reservations");
        else {
            for (Reservation r : q) {
                System.out.println("Reservation expired for: " + r.getName());
                System.out.println("Contact num: " + r.getContactNumber());
                System.out.println("Num Of Pax: " + r.getNoOfPax());
                System.out.println("Table: " + r.getTable().getTableID());
                System.out.println("Date: " + r.getLocaldate().toString());
                System.out.println("Time: " + r.getLocaltime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                System.out.println();
            }
        }
    }
}
