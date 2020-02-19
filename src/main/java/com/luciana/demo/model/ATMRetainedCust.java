package com.luciana.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Entity
public class ATMRetainedCust {

    @Column(name = "custName")
    private String custname;

    @Column(name = "atmNo")
    private String atmno;

    @Column(name = "mobileNo")
    private String mobileno;

    @Column(name = "email")
    private String email;

    @Column(name = "acctNo")
    private String acctno;

    @Column(name = "date")
    private String date;

    @Column(name = "message")
    private String message;

    @Column(name = "datafound")
    private String datafound;

    public ATMRetainedCust(String custname, String atmno, String mobileno, String email, String acctno,
                           String date, String message, String datafound) {
        this.custname = custname;
        this.atmno = atmno;
        this.mobileno = mobileno;
        this.email = email;
        this.acctno = acctno;
        this.date = date;
        this.message = message;
        this.datafound = datafound;
    }

    public ATMRetainedCust(){}

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getAtmno() {
        return atmno;
    }

    public void setAtmno(String atmno) {
        this.atmno = atmno;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAcctno() {
        return acctno;
    }

    public void setAcctno(String acctno) {
        this.acctno = acctno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatafound() {
        return datafound;
    }

    public void setDatafound(String datafound) {
        this.datafound = datafound;
    }

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    @Override
    public String toString() {
        return "{" +
                "\"custname\":\"" + custname + "\"" +
                ",\"atmNo\":\"" + atmno + "\"" +
                ",\"mobileno\":\"" + mobileno + "\"" +
                ",\"email\":\"" + email + "\"" +
                ",\"date\":\"" + date + "\"" +
                ",\"message\":\"" + message + "\"" +
                ",\"datafound\":\"" + datafound + "\"" +
                ",\"date_app\":\"" + dtf.format(now) + "\"" +
                "}";
    }
}
