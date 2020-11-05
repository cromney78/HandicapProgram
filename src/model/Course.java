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
public class Course {
    
    private int courseID;
    private String courseName;
    private String coursePhone;
    private int coursePar;

    public Course(int courseID, String courseName, String coursePhone, int coursePar) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.coursePhone = coursePhone;
        this.coursePar = coursePar;
    }

    public Course(String courseName) {
        this.courseName = courseName;
    }
   
    
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePhone() {
        return coursePhone;
    }

    public void setCoursePhone(String coursePhone) {
        this.coursePhone = coursePhone;
    }

    public int getCoursePar() {
        return coursePar;
    }

    public void setCoursePar(int coursePar) {
        this.coursePar = coursePar;
    }
    
}
