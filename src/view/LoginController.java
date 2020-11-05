/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import util.DBConnection;
import util.Query;

/**
 * FXML Controller class
 *
 * @author Chad Romney
 */
public class LoginController implements Initializable {

    @FXML    private Label lblTitle;
    @FXML    private Button submitBtn;
    @FXML    private Button cancelBtn;
    @FXML    private Text txtUsername;
    @FXML    private TextField usernameTxtField;
    @FXML    private Text txtPassword;
    @FXML    private TextField passwordTxtField;
    
    ResourceBundle rb;
    private String error, errTitle;  //used to populate errors in err mssg
    
    
    @FXML
    void cancelBtnPressed(ActionEvent event) throws Exception {
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

    @FXML
    void submitBtnPressed(ActionEvent event) throws Exception {
        String userName = usernameTxtField.getText();
        String userPassword = passwordTxtField.getText();
        
        //check if user is valid
        boolean goodUserPass = User.userLogin(userName, userPassword);
        
        if (goodUserPass == true) {

            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            stage.setTitle("Options Screen");
            Parent root = FXMLLoader.load(getClass().getResource("/view/Options.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setContentText("Please check your username and password and try again.");
            a.setHeaderText("Invalid credentials");
            a.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
}
