package com.dcproject.nodues;


import java.sql.Timestamp;
import java.util.Date;

public class Dues {
    public String rollno,reason;
    public int due;
    Date date;
    Timestamp t1;

    public Dues(){

    }

    public Dues(String reason, int due, Date d1){
        this.reason=reason;
        this.due=due;
        this.date=d1;
    }

    public Dues(String rollno,String reason,int due){
        this.rollno=rollno;
        this.reason=reason;
        this.due=due;
    }

}
