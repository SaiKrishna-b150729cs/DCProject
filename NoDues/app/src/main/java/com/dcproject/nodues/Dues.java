package com.dcproject.nodues;


import java.sql.Timestamp;
import java.util.Date;

public class Dues {
    private String id;
    private String reason;
    public int due;
    Date date;

    public Dues(){

    }

    public void setid(String id){this.id=id;}

    public void setdue(Integer due){this.due=due;}

    public void setreason(String reason){this.reason=reason;}

    public Dues(String reason, int due, Date d1){
        this.reason=reason;
        this.due=due;
        this.date=d1;
    }

    public String getreason(){return reason;}

    public Integer getdue(){return due;}

}
