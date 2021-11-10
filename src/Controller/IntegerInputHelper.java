package Controller;

import java.util.InputMismatchException;
import java.util.Scanner;

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
}
