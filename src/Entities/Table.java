package Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Table{
    private int numberOfPax = 0;
    private int tableID;
    private int capacity;
    private boolean isOccupied;
    private boolean isReserved;
    private ArrayList<LocalDateTime> reservationList;

    @Override
    public String toString() {
        return String.valueOf(tableID);
    }

    //constructor
    public Table(int table_id, int capacity){
        this.tableID = table_id;
        this.isOccupied = false;
        this.capacity = capacity;
        this.isReserved = false;
        this.reservationList=new ArrayList<LocalDateTime>();
    }

    //reset the table
    public void resetTable(){
        numberOfPax = 0;
        isOccupied = false;
    }

    //reserve the number of people for this table
    public int setNoOfPax(int number){
            this.numberOfPax = number;
            this.isOccupied = true;
            return 0;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public int getTableID(){return this.tableID;}

    public void setNumberOfPax(int numberOfPax) {
        this.numberOfPax = numberOfPax;
    }

    public int getNoOfPax(){return numberOfPax;}
    public boolean checkAvailability(){return isOccupied;}
    public void printInfo(){
        System.out.println("Table ID: "+getTableID());
        System.out.println("Capacity: "+capacity);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public void addToReservationList(LocalDateTime t){
        reservationList.add(t);
    }

    public ArrayList<LocalDateTime> getReservationList(){
        return reservationList;
    }

    public void removeReservation(LocalDateTime t){
        int i=0;
        for(LocalDateTime r:reservationList){
            if (r.equals(t)){
                reservationList.remove(i);
                System.out.println("Reservation removed from Table "+getTableID()+" successfully.");
                return;
            }
            i++;
        }
        System.out.println("Reservation timing not found for Table " + getTableID());
    }

    public boolean checkReserved(LocalDateTime dt){
        for (LocalDateTime dateTime:reservationList){
            if(dt.isAfter(dateTime.minusMinutes(30))&&dt.isBefore(dateTime.plusMinutes(30))){
                return true;
            }
        }
        return false;
    }

}