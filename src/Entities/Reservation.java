package Entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private String name;
    private String contactNumber;
    private int noOfPax;
    private LocalDate localdate;
    private LocalTime localtime;
    private Table table;
    private boolean hasExpired;
    //constructor
    public Reservation(String name, int noOfPax, String contact, Table table, LocalDate localdate, LocalTime localtime, boolean hasExpired) {
        this.localdate = localdate;
        this.localtime = localtime;
        this.contactNumber=contact;
        this.noOfPax = noOfPax;
        this.name = name;
        this.table = table;
        this.hasExpired = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getNoOfPax() {
        return noOfPax;
    }

    public void setNoOfPax(int noOfPax) {
        this.noOfPax = noOfPax;
    }

    public LocalDate getLocaldate() {
        return localdate;
    }

    public void setLocaldate(LocalDate localdate) {
        this.localdate = localdate;
    }

    public LocalTime getLocaltime() {
        return localtime;
    }

    public void setLocaltime(LocalTime localtime) {
        this.localtime = localtime;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public boolean getHasExpired() {
        return hasExpired;
    }

    public void setHasExpired(boolean hasExpired) {
        this.hasExpired = hasExpired;
    }

    public void printInfo(){
        System.out.println("Customer name: "+name);
        System.out.println("Contact number: "+contactNumber);
        System.out.println("Number of pax: "+noOfPax);
        System.out.println("Date: "+this.getLocaldate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("Time: "+this.getLocaltime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println("Table ID: "+table.getTableID());
        System.out.println("Order expired: "+hasExpired);
        System.out.println();
    }
}