package com.camsys.carmonic.model;

public class HistoryItem {
    String jobDate;
    String mechanic;
    String amount;
    int numberOfStars;


    public void setJobDate(String jobDate){
        this.jobDate = jobDate;
    }

    public String getJobDate(){
        return jobDate;
    }

    public void setMechanic(String mechanic){
        this.mechanic = mechanic;
    }

    public String getMechanic(){
        return mechanic;
    }

    public void  setAmount(String amount){
        this.amount = amount;
    }

    public String getAmount(){
        return  amount;
    }

    public void setNumberOfStars(int numberOfStars){
        this.numberOfStars = numberOfStars;
    }

    public int getNumberOfStars(){
        return numberOfStars;
    }
}
