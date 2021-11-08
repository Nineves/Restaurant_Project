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
            System.out.println("Please indicate the date (in YYYY-MM-DD format): ");
            sc.nextLine();
            String dateString=sc.nextLine();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date=LocalDate.parse(dateString,dateFormatter);
            ReportManager.generateByDay(date);
        }
        else if(choice==2){
            System.out.println("Please indicate the month: (integer from 1 to 12)");
            int month = sc.nextInt();
            while (month<1||month>12){
                System.out.println("Invalid input. Please enter again. ");
                month = sc.nextInt();
            }
            System.out.println("Please indicate the year:");
            int year=sc.nextInt();
            ReportManager.generateByMonth(month,year);
        }
        else {
            System.out.println("Invalid input.");
        }
    }
}
