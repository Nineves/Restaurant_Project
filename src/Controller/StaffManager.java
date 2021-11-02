package Controller;

import Entities.IDGenerator;
import Entities.Restaurant;
import Entities.Staff;
import Enums.Gender;
import Enums.JobTitle;

public class StaffManager {

    public static void addNewStaff(String name, Gender gender, JobTitle jobTitle){
        int staffID= IDGenerator.generateUniqueId();
        Staff newStaff=new Staff(staffID,name,jobTitle,gender);
        Restaurant.stafflist.add(newStaff);
        System.out.println("Staff added successfully!");
        return;

    }
    public static void printStaffList(){
        if(Restaurant.stafflist.size()==0){
            System.out.println("Staff list is empty!");
            return;
        }
        for(int i=0;i< Restaurant.stafflist.size();i++){
            Staff curStaff=Restaurant.stafflist.get(i);
            System.out.println("INDEX "+i+1);
            curStaff.printStaffInfo();
        }
    }

    public static void updateName(int idx, String newName){
        if (idx<=0||idx>Restaurant.stafflist.size()){
            System.out.println("Index out of range.");
            return;
        }
        Staff s=Restaurant.stafflist.get(idx-1);
        s.setName(newName);
        System.out.println("Staff name is updated.");
    }

    public static void updateGender(int idx, Gender gender){
        if (idx<=0||idx>Restaurant.stafflist.size()){
            System.out.println("Index out of range.");
            return;
        }
        Staff s=Restaurant.stafflist.get(idx-1);
        s.setGender(gender);
        System.out.println("Staff gender is updated.");
    }

    public static void updateJobTitle(int idx,JobTitle jobTitle){
        if (idx<=0||idx>Restaurant.stafflist.size()){
            System.out.println("Index out of range.");
            return;
        }
        Staff s=Restaurant.stafflist.get(idx-1);
        s.setJobTitle(jobTitle);
        System.out.println("Staff job title is updated.");
    }

    public static void removeStaff(int idx){
        if (idx<=0||idx>Restaurant.stafflist.size()){
            System.out.println("Index out of range.");
            return;
        }
        Restaurant.stafflist.remove(idx-1);
        System.out.println("Staff info is removed.");
    }
}
