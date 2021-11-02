package Entities;

import Enums.Gender;
import Enums.JobTitle;

public class Staff{
    private int staffID;
    private String name;
    private Gender gender;
    private JobTitle jobTitle;

    public Staff(int staffID, String name, JobTitle jobTitle, Gender gender){
        this.staffID = staffID;
        this.name = name;
        this.jobTitle = jobTitle;
        this.gender = gender;
    }
    public int returnStaffID(){
        return staffID;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setStaffID(int staffID){
        this.staffID = staffID;
    }

    public JobTitle getJobTitle(){
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle){
        this.jobTitle = jobTitle;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public void printStaffInfo(){
        System.out.println("Staff name: "+name);
        System.out.println("Gender: "+gender);
        System.out.println("Job Title: "+jobTitle);
    }
}