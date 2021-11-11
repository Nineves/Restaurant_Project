package Controller;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import Entities.Table;

public class IntegerInputHelper {
    public static int validateInput(int start, int end){
        Scanner sc= new Scanner(System.in);
        boolean valid = false;
        int result=-1;
        while (!valid){
            try{
                result=sc.nextInt();

                if(result>=start&&result<=end){
                    valid=true;
                }
                else {
                    System.out.println("Input integer is not in the given range.");
                }
            }
            catch (InputMismatchException e){
                System.out.println("Input is not an integer. Please enter again: ");
            }
            sc.nextLine(); //clears the buffer
        }
        return result;
    }

    public static int validateInput(ArrayList<Integer> tableNums){
        Scanner sc= new Scanner(System.in);
        boolean valid = false;
        int result=-1;
        while (!valid){
            try{
                result=sc.nextInt();

                if (tableNums.contains(result)) {
                    valid = true;
                }
                else {
                    System.out.println("Input integer is not in the given range.");
                }
            }
            catch (InputMismatchException e){
                System.out.println("Input is not an integer. Please enter again: ");
            }
            sc.nextLine(); //clears the buffer
        }
        return result;
    }
}
