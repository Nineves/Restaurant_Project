package Controller;

import Entities.Reservation;
import Entities.Restaurant;
import Entities.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class TableManager {
    public static ArrayList<Table> getAvailableTables(){
        if (Restaurant.getTablelist().size()==0){
            System.out.println("Table list is empty!");
            return null;
        }
        ArrayList<Table> availableTableList = new ArrayList<Table>();
        for(int i=0;i< Restaurant.getTablelist().size();i++){
            Table curTable=Restaurant.getTablelist().get(i);
            if(!curTable.checkAvailability()&&!curTable.checkReserved(LocalDateTime.now())) //the table is not occupied and not reserved in advance
                //not occupied
                availableTableList.add(curTable);
            }

        if(availableTableList.size()==0){
            System.out.println("All tables are occupied now.");
            return null;
        }
        return availableTableList;
    }

    public static ArrayList<Table> getAvailableTables(int numOfPax){
        if (Restaurant.getTablelist().size()==0){
            System.out.println("Table list is empty!");
            return null;
        }
        ArrayList<Table> availableTableList = new ArrayList<Table>();
        for(int i=0;i< Restaurant.getTablelist().size();i++){
            Table curTable=Restaurant.getTablelist().get(i);
            if(!curTable.checkAvailability()&&!curTable.checkReserved(LocalDateTime.now()) && curTable.getCapacity() >= numOfPax) //the table is not occupied and not reserved in advance
                //not occupied
                availableTableList.add(curTable);
            }

        if(availableTableList.size()==0){
            System.out.println("No available tables.");
            return null;
        }
        return availableTableList;
    }

    public static ArrayList<Table> getAvailableTablesLessCurrent(int numOfPax, int currentTableID){
        if (Restaurant.getTablelist().size()==0){
            System.out.println("Table list is empty!");
            return null;
        }
        ArrayList<Table> availableTableList = new ArrayList<Table>();
        for(int i=0;i< Restaurant.getTablelist().size();i++){
            Table curTable=Restaurant.getTablelist().get(i);
            if (curTable.getTableID() == currentTableID) continue;
            if(!curTable.checkAvailability()&&!curTable.checkReserved(LocalDateTime.now()) && curTable.getCapacity() >= numOfPax) //the table is not occupied and not reserved in advance
                //not occupied
                availableTableList.add(curTable);
            }
        if(availableTableList.size()==0){
            System.out.println("No available tables.");
            return null;
        }
        return availableTableList;
    }

    public static void printAvailableTables(){
        // int flag=0;
        // if (Restaurant.tablelist.size()==0){
        //     System.out.println("Table list is empty!");
        //     return;
        // }
        // for(int i=0;i<Restaurant.tablelist.size();i++){
        //     Table curTable=Restaurant.tablelist.get(i);
        //     if (!curTable.checkAvailability()){
        //         System.out.println("INDEX "+i+1);
        //         flag=1;
        //         curTable.printInfo();}
        // }
        // if (flag==0){
        //     System.out.println("All tables are occupied now.");
        // }
        ArrayList<Table> availableTableList = getAvailableTables();
        if (availableTableList != null) {
          for (int i = 0; i < availableTableList.size(); i++)
            availableTableList.get(i).printInfo();
        }
    }

    public static void printAvailableTables(ArrayList<Table> tables){
        for (int i = 0; i < tables.size(); i++) {
            tables.get(i).printInfo();
        }
    }

    public static boolean validate(int numOfPax,int idx){
        Table t=Restaurant.getTablelist().get(idx-1);
        if (t.getCapacity()>=numOfPax)
            return true;
        else
            return false;
    }

    public static ArrayList<Table> getToReserveTables(LocalDate date,LocalTime time){ //get reserved tables in a time interval
        if (Restaurant.getTablelist().size()==0){
            System.out.println("Table list is empty!");
            return null;
        }
        ArrayList<Table> toReserveTableList = new ArrayList<Table>();
        int flag;
        for (int i=0;i<Restaurant.getTablelist().size();i++){
            Table curTable=Restaurant.getTablelist().get(i);
            System.out.println(i);
            flag=0;
            if (time.isAfter(LocalTime.now().plusHours(1)) || time.isBefore((LocalTime.now().minusHours(1)))) {// if resvn time is not within 1h of current time, just check resvn list
                if(curTable.isReserved()){
                    ArrayList<LocalDateTime> rList=curTable.getReservationList();
                    for (LocalDateTime dt:rList){
                        LocalDate d=dt.toLocalDate();
                        LocalTime t=dt.toLocalTime();
                        if (d.equals(date) && t.minusHours(1).isBefore(time) && t.plusHours(1).isAfter(time)){
                            flag=1;
                            break;
                        }
                        System.out.println(dt);
                    }
    
                }
                if (flag==0){
                    toReserveTableList.add(curTable);
                }
            }
            else {
                if (!curTable.checkAvailability()) { // every time reservation is within 1 hour of current time, check if table is available also
                    if(curTable.isReserved()){
                        ArrayList<LocalDateTime> rList=curTable.getReservationList();
                        for (LocalDateTime dt:rList){
                            LocalDate d=dt.toLocalDate();
                            LocalTime t=dt.toLocalTime();
                            if (d.equals(date) && t.minusHours(1).isBefore(time) && t.plusHours(1).isAfter(time)){
                                flag=1;
                                break;
                            }
                        }
        
                    }
                    if (flag==0){
                        toReserveTableList.add(curTable);
                    }
                }
            }
        }
        return toReserveTableList;
    }

    public static boolean checkTableReserve(LocalDate d,LocalTime t,Table table){
        LocalDateTime dt=LocalDateTime.of(d,t);
        for(LocalDateTime item: table.getReservationList()){
            if (item.equals(dt)){
                return false;
            }
        }
        return true;
    }

    public static void removeReservation(LocalDate d,LocalTime t,Table table){
        table.removeReservation(LocalDateTime.of(d,t));
        if (table.getReservationList().size()==0){
            table.setReserved(false);
        }
        System.out.println("Reservation removed successfully.");
    }
}
