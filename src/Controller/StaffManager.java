package Controller;

import Entities.Restaurant;
import Entities.Staff;

public class StaffManager {
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
}
