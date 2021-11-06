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
        if (Restaurant.tablelist.size()==0){
            System.out.println("Table list is empty!");
            return null;
        }
        ArrayList<Table> availableTableList = new ArrayList<Table>();
        for(int i=0;i< Restaurant.tablelist.size();i++){
            Table curTable=Restaurant.tablelist.get(i);
            if(!curTable.checkAvailability()){
                //not occupied
                availableTableList.add(curTable);
            }
        }
        if(availableTableList.size()==0){
            System.out.println("All tables are occupied now.");
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

    public static boolean validate(int numOfPax,int idx){
        Table t=Restaurant.tablelist.get(idx-1);
        if (t.getCapacity()>=numOfPax)
            return true;
        else
            return false;
    }

    public static ArrayList<Table> getToReserveTables(LocalDate date,LocalTime time){ //get reserved tables in a time interval
        if (Restaurant.tablelist.size()==0){
            System.out.println("Table list is empty!");
            return null;
        }
        ArrayList<Table> toReserveTableList = new ArrayList<Table>();
        int flag;
        for (int i=0;i<Restaurant.tablelist.size();i++){
            Table curTable=Restaurant.tablelist.get(i);
            flag=0;
            if(curTable.isReserved()){
                ArrayList<LocalDateTime> rList=curTable.getReservationList();
                for (LocalDateTime dt:rList){
                    LocalDate d=dt.toLocalDate();
                    LocalTime t=dt.toLocalTime();
                    if (d.equals(date)&&t.equals(time)){
                        flag=1;
                        break;
                    }
                }

            }
            if (flag==0){
                toReserveTableList.add(curTable);
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
