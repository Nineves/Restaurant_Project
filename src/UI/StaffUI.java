package UI;

import Controller.StaffManager;
import Enums.Gender;
import Enums.JobTitle;

import java.util.Scanner;

public class StaffUI {
    public static void displayOptions(){
        System.out.println("===== Staff Management ===== ");
        System.out.println("1. Add New Staff Information ");
        System.out.println("2. Update Staff Information ");
        System.out.println("3. Remove Staff Information ");
        System.out.println("4. View Staff Information ");
        System.out.println("0. Exit Staff Management");
    }

    public static void staffUI(){
        displayOptions();
        Scanner sc=new Scanner(System.in);
        int choice=sc.nextInt();
//        while (choice<1||choice>4){
//            if (choice==0){
//                return;
//            }
//            System.out.println("Invalid choice. Please enter again. (Enter 0 to stop)");
//            choice=sc.nextInt();
//        }
        while (choice>=1&&choice<=4){
            switch (choice){
                case 1:
                    addStaff();
                    break;
                case 2:
                    updateStaff();
                    break;
                case 3:
                    removeStaff();
                    break;
                case 4:
                    viewStaff();
                    break;
                default:
                    break;
            }
            displayOptions();
            choice=sc.nextInt();
        }
        sc.close();
    }

    public static void addStaff(){
        Scanner sc=new Scanner(System.in);

        System.out.println("Please enter the name of the staff: ");
        String name=sc.nextLine();

        Gender gender=genderSelection();

        JobTitle jobTitle=jobTitleSelection();

        StaffManager.addNewStaff(name,gender,jobTitle);
        sc.close();
    }

    public static void updateStaff(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Choose a staff person: ");
        StaffManager.printStaffList();
        int choiceStaff=sc.nextInt();
        System.out.println("Select an option: ");
        System.out.println("[1] Update name ");
        System.out.println("[2] Update gender");
        System.out.println("[3] Update job title");
        int selection=sc.nextInt();
        while (selection<0||selection>4){
            System.out.println("Invalid input. Please select again: ");
            selection=sc.nextInt();
        }
        switch (selection){
            case 1:
                System.out.println("Enter new name: ");
                String newName=sc.nextLine();
                StaffManager.updateName(choiceStaff,newName);
                break;
            case 2:
                Gender newGender=genderSelection();
                StaffManager.updateGender(choiceStaff,newGender);
                break;
            case 3:
                JobTitle newJobTitle=jobTitleSelection();
                StaffManager.updateJobTitle(choiceStaff,newJobTitle);
                break;
        }
        sc.close();
    }

    public static Gender genderSelection(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Please choose the gender of the staff: ");
        System.out.println("[1] MALE");
        System.out.println("[2] FEMALE");
        int genderChoice=sc.nextInt();
        Gender gender;
        while(genderChoice!=2&&genderChoice!=1){
            System.out.println("Invalid choice. Please choose again.");
            System.out.println("[1] MALE");
            System.out.println("[2] FEMALE");
            genderChoice=sc.nextInt();}
        if (genderChoice==1){
            gender=Gender.MALE;
        }
        else
            gender=Gender.FEMALE;
        sc.close();
        return gender;
    }

    public static JobTitle jobTitleSelection(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Please choose the job title: ");
        System.out.println("[1] WAITER");
        System.out.println("[2] SUPERVISOR");
        System.out.println("[3] MANAGER");
        int jobChoice=sc.nextInt();
        JobTitle jobTitle=JobTitle.WAITER;
        while (jobChoice<1||jobChoice>3){
            System.out.println("Invalid choice. Please choose again.");
            jobChoice=sc.nextInt();
        }
        switch (jobChoice){
            case 1:
                jobTitle=JobTitle.WAITER;
                break;
            case 2:
                jobTitle=JobTitle.SUPERVISOR;
                break;
            case 3:
                jobTitle=JobTitle.MANAGER;
                break;
            default:
                break;
        }
        sc.close();
        return jobTitle;

    }

    public static void removeStaff(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Choose one to remove: ");
        StaffManager.printStaffList();
        int choice=sc.nextInt();
        StaffManager.removeStaff(choice);
    }

    public static void viewStaff(){
        StaffManager.printStaffList();
    }
}
