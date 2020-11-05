/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static util.DBConnection.conn;

/**
 *
 * @author Chad Romney
 */
public class Query {
    private static String query;
    private static Statement stmt;
    private static ResultSet result;
    
    public static void makeQuery(String q) {
        query = q;
        
        try {
            //create statement object
            stmt = conn.createStatement();
            
            //determine query execution
            if (query.toLowerCase().startsWith("select")) {
                result = stmt.executeQuery(query);
                //System.out.println("Query executeQuery");
            }
            if (query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("update") || query.toLowerCase().startsWith("insert")) {
                stmt.executeUpdate(query);
                //System.out.println("Query executeUpdate");
            }
            
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public static ResultSet getQueryResult() {
        return result;
    }
}
