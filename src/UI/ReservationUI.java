package UI;

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
        Scanner sc = new Scanner(System.in);
        int option;
        option=sc.nextInt();
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
            option=sc.nextInt();
        }
    }
    public static void addNewReservation(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter name of the customer: ");
        String name=sc.nextLine();
        System.out.println("Enter contact number: ");
        String contactNum=sc.nextLine();
        System.out.println("Enter number of pax: (min 2 pax, max 10 pax)");
        int numOfPax=sc.nextInt();
        while (numOfPax<2||numOfPax>10){
            System.out.println("Invalid input. Please enter again. (min 2 pax, max 10 pax) ");
            numOfPax=sc.nextInt();
        }
        System.out.println("Enter reservation date: (in YYYY-MM-DD format)");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sc.nextLine();
        String dateString=sc.nextLine();
        LocalDate date=LocalDate.parse(dateString,dateFormatter);
        System.out.println("Please choose time slot: ");
        System.out.println("[1] 11:00-12:00 ");
        System.out.println("[2] 12:00-13:00 ");
        System.out.println("[3] 13:00-14:00 ");
        System.out.println("[4] 17:00-18:00 ");
        System.out.println("[5] 18:00-19:00 ");
        System.out.println("[6] 19:00-20:00 ");
        int choiceOfTime=sc.nextInt();
        LocalTime time= generateTime(choiceOfTime);
        int result=ReservationManager.addNewReservation(name,contactNum,numOfPax,date,time);
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
        ReservationManager.printAllReservations();
        int choice=sc.nextInt();
        if (choice>=0&&choice<Restaurant.reservationList.size()){
            Reservation curReservation=Restaurant.reservationList.get(choice-1);
            System.out.println("Select an option: ");
            System.out.println("[1] Update customer name ");
            System.out.println("[2] Update contact number ");
            System.out.println("[3] Change date and time");
            System.out.println("[0] Exit ");
            int selection=sc.nextInt();
            while (selection<0||selection>3){
                System.out.println("Invalid choice. Please choose again.");
                selection=sc.nextInt();
            }
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
        System.out.println("Current Date"+r.getLocaldate());
        System.out.println("Current Time"+r.getLocaltime());
        System.out.println("Enter new reservation date: (in YYYY-MM-DD format)");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString=sc.nextLine();
        LocalDate newDate=LocalDate.parse(dateString,dateFormatter);

        System.out.println("Please choose a new time slot: ");
        System.out.println("[1] 11:00-12:00 ");
        System.out.println("[2] 12:00-13:00 ");
        System.out.println("[3] 13:00-14:00 ");
        System.out.println("[4] 17:00-18:00 ");
        System.out.println("[5] 18:00-19:00 ");
        System.out.println("[6] 19:00-20:00 ");
        int choiceOfTime=sc.nextInt();
        LocalTime newTime= generateTime(choiceOfTime);
        Table previousTable=r.getTable();
        ReservationManager.updateTable(r,newDate,newTime);
    }

    public static void removeReservation(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the index of the reservation you would like to cancel: ");
        ReservationManager.printAllReservations();
        int choice=sc.nextInt();
        ReservationManager.cancelReservation(choice);


    }
    public static void viewReservations(){
        ReservationManager.printAllReservations();
    }

    public static void viewExpiredReservations(Queue<Reservation> q) {
        if (q.size() == 0)
            System.out.println("No recent expired reservations");
        else {
            for (Reservation r : q) {
                System.out.println("Reservation expired for: " + r.getName());
                System.out.println("Contact num: " + r.getContactNumber());
                System.out.println("Num Of Pax: " + r.getNoOfPax());
                System.out.println("Table: " + r.getTable());
                System.out.println("Date: " + r.getLocaldate());
                System.out.println("Time: " + r.getLocaltime());
                System.out.println();
            }
        }
    }
}
