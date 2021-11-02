package Controller;

import Entities.Reservation;
import Entities.Restaurant;
import Entities.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReservationManager {

    public static int addNewReservation(String name, String contact, int numOfPax,LocalDate date, LocalTime time){
        Table resultT=ReservationManager.getReservationTable(date,time,numOfPax);
        if (resultT==null){
            System.out.println("Table assignment failed.");
            return -1;
        }
        resultT.setReserved(true);
        resultT.addToReservationList(LocalDateTime.of(date,time));
        LocalDateTime t=LocalDateTime.of(date, time);
        resultT.addToReservationList(t);
        Reservation newR= new Reservation(name,numOfPax,contact,resultT,date,time);
        Restaurant.reservationList.add(newR);
        return 1;
    }
    public static Table getReservationTable(LocalDate date, LocalTime time,int numOfPax){
        ArrayList<Table> availableTables=TableManager.getToReserveTables(date,time);
        if(availableTables.size()==0){
            System.out.println("No table is available to be reserved in your chosen time and date.");
            return null;
        }
        int bestCapacity=999,idx=-1;
        for (int i=0;i<availableTables.size();i++){
            Table curTable=availableTables.get(i);
            if(curTable.getCapacity()<bestCapacity && curTable.getCapacity()>=numOfPax){
                bestCapacity=curTable.getCapacity(); //choose the best-fit table
                idx=i;
            }
        }
        if (idx==-1){
            System.out.println("No table is big enough to be reserved in your chosen time and date.");
            return null;
        }
        return availableTables.get(idx);
    }

    public static void printAllReservations() {
        if (Restaurant.reservationList.size()==0){
            System.out.println("Reservation list is empty.");
            return;
        }
        System.out.println("All reservations: ");
        for (int i=0;i<Restaurant.reservationList.size();i++){
            Reservation curR=Restaurant.reservationList.get(i);
            System.out.println("INDEX "+i);
            curR.printInfo();
        }
    }
}
