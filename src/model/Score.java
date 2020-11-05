/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author Chad Romney
 */
public class Score {
    private int scoreID;
    private int golferID;
    private int courseID;
    private int score;
    private int scoreOverPar;
    private String fullName;

    public Score(int scoreID, int golferID, int courseID, int score) {
        this.scoreID = scoreID;
        this.golferID = golferID;
        this.courseID = courseID;
        this.score = score;
    }

    public Score(int scoreID, int golferID, int courseID, int score, String fullName) {
        this.scoreID = scoreID;
        this.golferID = golferID;
        this.courseID = courseID;
        this.score = score;
        this.fullName = fullName;
    }

    public int getScoreOverPar() {
        return scoreOverPar;
    }

    public void setScoreOverPar(int scoreOverPar) {
        this.scoreOverPar = scoreOverPar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getScoreID() {
        return scoreID;
    }

    public void setScoreID(int scoreID) {
        this.scoreID = scoreID;
    }

    public int getGolferID() {
        return golferID;
    }

    public void setGolferID(int golferID) {
        this.golferID = golferID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
}
