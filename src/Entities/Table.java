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
        if(isOccupied){
            System.out.println("Sorry, this table has been occupied by someone else.");
            return -1;
        }
        if(number <= capacity){
            this.numberOfPax = number;
            this.isOccupied = true;
            return 0;
        }
        else{
            System.out.printf("No. of pax exceeds table capacity!\n");
            return -1;
        }
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

}