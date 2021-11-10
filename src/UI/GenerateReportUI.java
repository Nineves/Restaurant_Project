package UI;

import Controller.LocalDateInputHelper;
import Controller.ReportManager;
import Controller.IntegerInputHelper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GenerateReportUI {
    public static void generateReportUI(){
        System.out.println("Would you like to view the report by day or month?");
        System.out.println("[1] DAY");
        System.out.println("[2] MONTH");
        int choice=IntegerInputHelper.validateInput(1,2);
        Scanner sc= new Scanner(System.in);
        if(choice==1){
            System.out.println("Please indicate the date (in YYYY-MM-DD format): ");
            //String dateString=sc.nextLine();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date= LocalDateInputHelper.validateInput(dateFormatter);
            ReportManager.generateByDay(date);
        }
        else if(choice==2){
            System.out.println("Please indicate the month: (integer from 1 to 12)");
            int month = IntegerInputHelper.validateInput(1,12);
            System.out.println("Please indicate the year: (2010-2021) ");
            int year=IntegerInputHelper.validateInput(2020,LocalDate.now().getYear());
            ReportManager.generateByMonth(month,year);
        }
        else {
            System.out.println("Invalid input.");
        }
    }
}
