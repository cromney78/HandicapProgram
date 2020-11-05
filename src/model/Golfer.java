/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Chad Romney
 */
public class Golfer {
    private int golferID;
    private String golferFirstName;
    private String golferLastName;
    private String golferPhone;
    private String golferEmail;
    private Double golferHandicap;

    public Golfer(int golferID, String golferFirstName, String golferLastName, String golferPhone, String golferEmail, Double golferHandicap) {
        this.golferID = golferID;
        this.golferFirstName = golferFirstName;
        this.golferLastName = golferLastName;
        this.golferPhone = golferPhone;
        this.golferEmail = golferEmail;
        this.golferHandicap = golferHandicap;
    }
       
    public int getGolferID() {
        return golferID;
    }

    public void setGolferID(int golferID) {
        this.golferID = golferID;
    }

    public String getGolferFirstName() {
        return golferFirstName;
    }

    public void setGolferFirstName(String golferFirstName) {
        this.golferFirstName = golferFirstName;
    }

    public String getGolferLastName() {
        return golferLastName;
    }

    public void setGolferLastName(String golferLastName) {
        this.golferLastName = golferLastName;
    }

    public String getGolferPhone() {
        return golferPhone;
    }

    public void setGolferPhone(String golferPhone) {
        this.golferPhone = golferPhone;
    }

    public String getGolferEmail() {
        return golferEmail;
    }

    public void setGolferEmail(String golferEmail) {
        this.golferEmail = golferEmail;
    }

    public Double getGolferHandicap() {
        return golferHandicap;
    }

    public void setGolferHandicap(Double golferHandicap) {
        this.golferHandicap = golferHandicap;
    }
}
