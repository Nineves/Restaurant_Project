package Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LocalDateInputHelper {
    public static LocalDate validateInput(DateTimeFormatter dateTimeFormatter){
        Scanner sc= new Scanner(System.in);
        boolean valid = false;
        String result;
        LocalDate date=LocalDate.now();
        while (!valid){
            try{
                result=sc.nextLine();
                date=LocalDate.parse(result,dateTimeFormatter);
                valid=true;
            }
            catch (DateTimeParseException e){
                System.out.println("Input is not a valid date. Please enter again: ");
            }
            //sc.nextLine(); //clears the buffer
        }
        return date;
    }
}
