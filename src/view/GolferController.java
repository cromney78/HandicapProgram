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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Golfer;
import util.DBConnection;
import util.Query;

/**
 * FXML Controller class
 *
 * @author Chad Romney
 */
public class GolferController implements Initializable {

    @FXML    private TableView<Golfer> tblViewGolfers;
    @FXML    private TableColumn<Golfer, String> colGolferLastName;
    @FXML    private TableColumn<Golfer, String> colGolferFirstName;
    @FXML    private TableColumn<Golfer, Double> colGolferHandicap;
    @FXML    private Label lblGolferId;
    @FXML    private TextField txtFieldGolferID;
    @FXML    private Label lblFirstName;
    @FXML    private TextField txtFieldFirstName;
    @FXML    private Label lblLastName;
    @FXML    private TextField txtFieldLastName;
    @FXML    private Label lblPhone;
    @FXML    private TextField txtFieldPhone;
    @FXML    private Label lblEmail;
    @FXML    private TextField txtFieldEmail;
    @FXML    private Label lblHandicap;
    @FXML    private TextField txtFieldHandicap;
    @FXML    private Button btnSave;
    @FXML    private Button btnEdit;
    @FXML    private Button btnDelete;
    @FXML    private Button btnCancel;

    private boolean exists;
    private Golfer golfer;
    private String firstName, lastName, phone, email;
    private Double handicap;
    private ObservableList<Golfer> golfersList;
    
    
    @FXML
    void btnCancelPressed(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.setTitle("Options");
        Parent root = FXMLLoader.load(getClass().getResource("/view/Options.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnDeletePressed(ActionEvent event) throws SQLException {
        golfer = tblViewGolfers.getSelectionModel().getSelectedItem();
        
        if (golfer == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Golfer not selected");
            alert.setContentText("Select a golfer to continue.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete this Golfer??");
            alert.setHeaderText("This will also delete all associated scores");
            alert.setContentText("Click OK to Delete");
            
            alert.showAndWait().ifPresent(buttonResult -> {
                String q = "DELETE FROM golfer_table WHERE golferID = " + golfer.getGolferID();
                //delete all the scores from choosen golfer
                String q2 = "DELETE FROM score_table WHERE golferID = " + golfer.getGolferID(); 
                try {
                   Statement stmt = DBConnection.getConnection().createStatement();
                    Query.makeQuery(q);
                    Query.makeQuery(q2); 
                    populateTable();
                } catch (SQLException e) {
                    System.out.println("SQLException = " + e);
                }
            });
        }
    }

    @FXML
    void btnEditPressed(ActionEvent event) {
        exists = true;
        golfer = tblViewGolfers.getSelectionModel().getSelectedItem();
        
        if (golfer == null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Golfer not selected");
                alert.setContentText("Select a Golfer to continue.");
                alert.showAndWait();
            }
        else {
            txtFieldFirstName.setText(golfer.getGolferFirstName());
            txtFieldLastName.setText(golfer.getGolferLastName());
            txtFieldPhone.setText(golfer.getGolferPhone());
            txtFieldEmail.setText(golfer.getGolferEmail());
            txtFieldHandicap.setText(Double.toString(golfer.getGolferHandicap()));
        }
    }

    @FXML
    void btnSavePressed(ActionEvent event) throws SQLException {
        String query = "";
        
        if(validateFields()) {
            if(!exists) {
                query = "INSERT INTO golfer_table SET " +
                        "golferFirstName = '" + firstName + "'," +
                        "golferLastName = '" + lastName + "'," +
                        "golferPhone = '" + phone + "'," +
                        "golferEmail = '" + email + "';";
            } else {
                int id = golfer.getGolferID();
                query = "UPDATE golfer_table SET " +
                        "golferFirstName = '" + firstName + "'," +
                        "golferLastName = '" + lastName + "'," +
                        "golferPhone = '" + phone + "'," +
                        "golferEmail = '" + email + "'" +
                        "WHERE golferID = " + id + ";";
            }
        }
        Statement stmt = DBConnection.getConnection().createStatement();
        Query.makeQuery(query);
        ResultSet result = Query.getQueryResult();
            
        exists = false; //reset back to false so you can add again
        populateTable();
        clearFields();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colGolferLastName.setCellValueFactory(new PropertyValueFactory<>("golferLastName"));
        colGolferFirstName.setCellValueFactory(new PropertyValueFactory<>("golferFirstName"));
        colGolferHandicap.setCellValueFactory(new PropertyValueFactory<>("golferHandicap"));     
        
        try {
            populateTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void populateTable() throws SQLException {
        getAllGolfers();
        tblViewGolfers.setItems(golfersList);
    }

    private void clearFields() {
        txtFieldFirstName.clear();
        txtFieldLastName.clear();
        txtFieldPhone.clear();
        txtFieldEmail.clear();
        txtFieldHandicap.clear();
    }

    private boolean validateFields() {
        firstName =  txtFieldFirstName.getText().trim();
        lastName =  txtFieldLastName.getText().trim();
        phone = txtFieldPhone.getText().trim();
        email = txtFieldEmail.getText().trim();
        
        String error = "";
        if (firstName.equals("")){error += ("First name cannot be blank\n");}
        if (lastName.equals("")){error += ("Last name cannot be blank\n");}
        if (phone.equals("")){error += ("Phone cannot be blank\n");}
        if (phone.length() > 14 || phone.length() < 10){error += ("Phone should be in the following format.  000-000-0000");}
        if (email.equals("")){error += ("Email cannot be blank\n");}
        
        if(error.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error adding golfer");
            alert.setHeaderText("Cannot add golfer");
            alert.setContentText(error); //display the error mssg
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    private void getAllGolfers() throws SQLException {
        golfersList = FXCollections.observableArrayList();
        int tID;
        String tFirstName;
        String tLastName;
        String tPhone;
        String tEmail;
        Double tHandicap;
        
        String q = "SELECT * FROM golfer_table ORDER BY golferLastName;";
        Statement stmt = DBConnection.getConnection().createStatement();
        Query.makeQuery(q);
        ResultSet result = Query.getQueryResult();
        
        while (result.next()){
            tID = result.getInt("golferID");
            tFirstName = result.getString("golferFirstName");
            tLastName = result.getString("golferLastName");
            tPhone = result.getString("golferPhone");
            tEmail = result.getString("golferEmail");
            tHandicap = result.getDouble("golferHandicap");
            
            String hdcp = String.format("%.1f", tHandicap);
            tHandicap = Double.parseDouble(hdcp);
            
            golfersList.add(new Golfer(tID, tFirstName, tLastName, tPhone, tEmail, tHandicap));
        }
    }
}
