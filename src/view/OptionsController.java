/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chad Romney
 */
public class OptionsController implements Initializable {

    @FXML    private Button btnGolfers;
    @FXML    private Button btnScores;
    @FXML    private Button btnCourses;
    @FXML    private Button btnReports;
    @FXML    private Button btnExit;
    @FXML    private Label  lblTitle;

    
    @FXML
    void btnCoursesPressed(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.setTitle("Courses");
        Parent root = FXMLLoader.load(getClass().getResource("/view/Courses.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnGolfersPressed(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.setTitle("Golfers");
        Parent root = FXMLLoader.load(getClass().getResource("/view/Golfer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnReportsPressed(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.setTitle("Reports");
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnScoresPressed(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.setTitle("Scores");
        Parent root = FXMLLoader.load(getClass().getResource("/view/Scores.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnExitPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit Program?");
        alert.setContentText("Click ok to confirm");
        
        //Lambda used to make alert more effecient and readable
        alert.showAndWait().ifPresent(btn -> {
            if(btn == ButtonType.OK) {
                System.exit(0);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
