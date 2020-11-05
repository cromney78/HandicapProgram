 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import util.DBConnection;
import util.Query;

/**
 *
 * @author Chad Romney
 */
public class User {
    private int userID;
    private String userName;
    private String password;
    private String createDate;
    
    private static User activeUser;
    
    public static boolean userLogin(String user, String pass) throws SQLException, Exception {
        
        //query to find if there is a match
        String q = "SELECT * FROM user "
                 + "WHERE userName = '" + user + "' "
                 + "AND password = '" + pass + "'";
        
        //create statement
        Statement stmt;
        stmt = DBConnection.getConnection().createStatement();
        
        Query.makeQuery(q);
        ResultSet result = Query.getQueryResult();
        
        //check if user exists and if so set active user to userName
        if (result.next()) {
            
            activeUser = new User();
            activeUser.setUserName(result.getString("userName"));
            
            //System.out.println("User login success");
            return true;
            
        } else {
            return false;
        }    
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User activeUser) {
        User.activeUser = activeUser;
    }
    
    
}
