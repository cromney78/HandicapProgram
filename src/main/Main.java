package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DBConnection;

/**
 *
 * @author Chad Romney
 */
public class Main extends Application {
    
    Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception, IOException {
        this.stage = stage;
        this.stage.setTitle("Handicap Program Login");
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException, Exception {
        DBConnection.makeConnection();
        System.out.println(Locale.getDefault());
        launch(args);
        DBConnection.closeConnection();
    }
    
}
