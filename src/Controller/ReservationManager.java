package Controller;

import Entities.Reservation;
import Entities.Restaurant;
import Entities.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class ReservationManager {

    public static int addNewReservation(String name, String contact, int numOfPax,LocalDate date, LocalTime time, boolean hasExpired){
        boolean resultD=validateDateTime(date,time);
        if (!resultD){
            System.out.println("The date and time is before current time.");
            return -1;
        }
        Table resultT=getReservationTable(date,time,numOfPax);
        if (resultT==null){
            System.out.println("Table assignment failed.");
            return -1;
        }
        resultT.setReserved(true);
        resultT.addToReservationList(LocalDateTime.of(date,time));
        Reservation newR= new Reservation(name,numOfPax,contact,resultT,date,time,hasExpired);
        Restaurant.addReservationList(newR);
        return 1;
    }

    public static Table lookForNewTable(LocalDate date,LocalTime time,int numOfPax){
        Table resultT=ReservationManager.getReservationTable(date,time,numOfPax);
        if (resultT==null){
            System.out.println("No table is available. Cannot change table.");
            return null;
        }
        return resultT;
    }

    public static void updateTable(Reservation r,LocalDate newDate,LocalTime newTime){
        if(!TableManager.checkTableReserve(newDate,newTime,r.getTable())){ //need change table
            Table newTable=ReservationManager.lookForNewTable(newDate,newTime,r.getNoOfPax());
            if(newTable==null){
                System.out.println("No new table is available.");
                return;
            }
            //New Table found. Need to remove reservation in the previous table.
            TableManager.removeReservation(r.getLocaldate(),r.getLocaltime(),r.getTable());
            r.setTable(newTable);
            r.setLocaldate(newDate);
            r.setLocaltime(newTime);
            r.setHasExpired(false);
            System.out.println("New table assigned.");
            return;
        }
        //no need to change table
        TableManager.removeReservation(r.getLocaldate(),r.getLocaltime(),r.getTable());
        r.getTable().addToReservationList(LocalDateTime.of(newDate,newTime));
        r.setLocaldate(newDate);
        r.setLocaltime(newTime);
        r.setHasExpired(false);
        System.out.println("Date and time updated successfully.");

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

    public static void printValidReservations() {
        if (Restaurant.getReservationList().size()==0){
            System.out.println("Reservation list is empty.");
            return;
        }
        System.out.println("All reservations: ");
        int lastTable = 0, valid = 0;
        for (int i=0;i<Restaurant.getReservationList().size();i++){
            Reservation curR=Restaurant.getReservationList().get(i);
            if (curR.getHasExpired()==true){
                continue;
            }
            valid = 1;
            if (curR.getTable().getTableID() != lastTable) {
                lastTable = curR.getTable().getTableID();
                System.out.println(String.format("************Table: %d************", curR.getTable().getTableID()));
            }
            System.out.println("INDEX "+(i+1));
            curR.printInfo();
        }
        if (valid == 0) System.out.println("No non-expired reservations found.");
    }

    public static void updateName(Reservation r, String name){
        r.setName(name);
        System.out.println("Customer name is updated successfully!");
    }

    public static void updateContact(Reservation r, String contact){
        r.setContactNumber(contact);
        System.out.println("Contact number is updated successfully!");
    }

    // public static void removeReservationFromList(Reservation r){
    //     int i=0;
    //     if(Restaurant.reservationList.size()==0){
    //         System.out.println("Reservation list is empty!");
    //         return;
    //     }
    //     for(Reservation reserv:Restaurant.reservationList){
    //         if (reserv.getName().equals(r.getName()) && reserv.getLocaldate().equals(r.getLocaldate()) && reserv.getLocaltime().equals(r.getLocaltime())) {
    //             Restaurant.reservationList.remove(i);
    //             System.out.println("Reservation is removed from list successfully!");
    //             return;
    //         }
    //         i++;
    //     }
    // }

    public static void cancelReservation(int index){
        if(Restaurant.getReservationList().size()==0){
            System.out.println("Error! No reservation is in the list.");
            return;
        }
        Reservation rToRemove=Restaurant.getReservationList().get(index-1);
        TableManager.removeReservation(rToRemove.getLocaldate(),rToRemove.getLocaltime(),rToRemove.getTable());
        Restaurant.removeReservation(index-1);
        System.out.println("Reservation is cancelled.");
    }

    public static boolean validateDateTime(LocalDate date,LocalTime time){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime setDateTime = LocalDateTime.of(date,time);
        if(setDateTime.isBefore(now)){
            return false;
        }
        return true;

    }

    public static boolean checkReservationExpiry(Table t, LocalDateTime dt) {
		if(LocalDateTime.now().isAfter(dt.plusSeconds(20))) {
			t.removeReservation(dt);	//Free up table for walk-in customers
            return true;
	    }
        return false;
	}

	public static Reservation getReservation(String contactNumber){
        for(Reservation r:Restaurant.getReservationList()){
            if(r.getContactNumber().equals(contactNumber)){
                return r;
            }
        }
        System.out.println("Reservation not found!");
        return null;
    }
	
	public static void clearExpiredReservations(ArrayList<Table> tables, Queue<Reservation> q) {
		for(int i=0;i<tables.size();i++) {
            Table t = tables.get(i);
            ArrayList<LocalDateTime> tableReservationList = t.getReservationList();
			if(tableReservationList != null) {
                for (int j = 0; j < tableReservationList.size(); j++) {
                    LocalDateTime dt = tableReservationList.get(j);
                    if (checkReservationExpiry(t, dt)) { // if reservation is due to expire
                        int k = 0;
                        Reservation foundR = null;
                        for (Reservation r : Restaurant.getReservationList()) {
                            if ((r.getLocaldate().equals(dt.toLocalDate())) && (r.getLocaltime().equals(dt.toLocalTime()) && (r.getTable() == t))) { // find reservation in reservationList
                                //Restaurant.reservationList.remove(index);
                                if (q.size() == 3) { // add reservation to queue
                                    q.remove();
                                    q.add(r);
                                }
                                else q.add(r);
                                r.setHasExpired(true);
                                foundR = r;
                                break;
                            }
                            k++;
                        }
                        if (foundR != null) {
                            ArrayList<Reservation> reservationList = Restaurant.getReservationList();
                            reservationList.remove(k);
                            reservationList.add(reservationList.size(), foundR);   
                        }
                    }
                }
            }
		}

	}


}
