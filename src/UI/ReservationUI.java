package UI;

import Controller.ReservationManager;
import Entities.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ReservationUI {
    public static void displayOptions(){
        System.out.println("===== Reservation Management ===== ");
        System.out.println("1. Add New Reservation ");
        System.out.println("2. Update Reservation ");
        System.out.println("3. Remove Reservation");
        System.out.println("4. View Reservations");
        System.out.println("0. Exit Order Management");
    }

    public static void reservationUI(){
        Scanner sc = new Scanner(System.in);
        int option;
        option=sc.nextInt();
        while (option<=4&&option>=1){
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
                default:
                    return;

            }
            option=sc.nextInt();
        }
        sc.close();
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

    }

    public static void removeReservation(){

    }
    public static void viewReservations(){
        ReservationManager.printAllReservations();
    }
}
