package UI;

import Controller.ReportManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GenerateReportUI {
    public static void generateReportUI(){
        System.out.println("Would you like to view the report by day or month?");
        System.out.println("[1] DAY");
        System.out.println("[2] MONTH");
        Scanner sc=new Scanner(System.in);
        int choice=sc.nextInt();
        if(choice==1){
            System.out.println("Please indicate the date: (in YYYY-MM-DD format)");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString=sc.nextLine();
            LocalDate date=LocalDate.parse(dateString,dateFormatter);
            ReportManager.generateByDay(date);
        }
        else if(choice==2){
            ReportManager.generateByMonth();
        }
        else {
            System.out.println("Invalid input.");
        }
    }
}
