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
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import model.Course;
import util.DBConnection;
import util.Query;
/**
 * FXML Controller class
 *
 * @author Chad Romney
 */
public class CoursesController implements Initializable {

    @FXML    private TableView<Course> tblViewCourses;
    @FXML    private TableColumn<Course, String> colCourseName;
    @FXML    private Label lblCourseID;
    @FXML    private TextField txtFieldCourseID;
    @FXML    private Label lblName;
    @FXML    private TextField txtFieldCourseName;
    @FXML    private Label lblPhone;
    @FXML    private TextField txtFieldCoursePhone;
    @FXML    private Label lblSlope;
    @FXML    private TextField txtFieldCoursePar;
    @FXML    private Button btnSave;
    @FXML    private Button btnEdit;
    @FXML    private Button btnDelete;
    @FXML    private Button btnCancel;

    private boolean exists;
    private Course course;
    private int courseIndex;
    private String name, phone, par;
    private ObservableList<Course> gCoursesList;
    
    
    @FXML
    void btnDeletePressed(ActionEvent event) throws SQLException {
        course = tblViewCourses.getSelectionModel().getSelectedItem();
        
        if (course == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Golf Course not selected");
            alert.setContentText("Select a Golf Course to continue.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete?");
            alert.setHeaderText("Delete this Course?");
            alert.setContentText("Click OK to Delete");
            
            alert.showAndWait().ifPresent(buttonResult -> {
                String q = "DELETE FROM course_table WHERE courseID = " + course.getCourseID();
                try {
                   Statement stmt = DBConnection.getConnection().createStatement();
                    Query.makeQuery(q);
                    populateTable();
                } catch (SQLException e) {
                    System.out.println("SQLException = " + e);
                }
            });
        }
    }

    @FXML
    void btnEditPressed(ActionEvent event) {
        //exists changes functionality of the save button
        exists = true;
        course = tblViewCourses.getSelectionModel().getSelectedItem();
        
        if (course == null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Golf Course not selected");
                alert.setContentText("Select a Golf Course to continue.");
                alert.showAndWait();
            }
        else {
            txtFieldCourseName.setText(course.getCourseName());
            txtFieldCoursePhone.setText(course.getCoursePhone());
            txtFieldCoursePar.setText(Integer.toString(course.getCoursePar()));
        }
    }

    @FXML
    void btnSavePressed(ActionEvent event)throws Exception, SQLException  {
        String query = "";
        
        if(validateFields()) {
            if(!exists) {
                query = "INSERT INTO course_table SET " +
                        "courseName = '" + name + "'," +
                        "coursePhone = '" + phone + "'," +
                        "coursePar = '" + par + "';";
            } else {
                int id = course.getCourseID();
                query = "UPDATE course_table SET " +
                        "courseName = '" + name + "'," +
                        "coursePhone = '" + phone + "'," +
                        "coursePar = '" + par + "'" + 
                        "WHERE courseID = " + id + ";";
            }
        }
        Statement stmt = DBConnection.getConnection().createStatement();
        Query.makeQuery(query);
        ResultSet result = Query.getQueryResult();
            
        exists = false; //reset back to false so you can add again
        populateTable();
        clearFields();
    }
    
    
    public void populateTable() throws SQLException {
        getAllGolfCourses();
        tblViewCourses.setItems(gCoursesList);
    }
    
    public void getAllGolfCourses() throws SQLException {
        gCoursesList = FXCollections.observableArrayList();
        int tID;
        String tName;
        String tPhone;
        int tPar;
        
        String q = "SELECT * FROM course_table ORDER BY courseName;";
        Statement stmt = DBConnection.getConnection().createStatement();
        Query.makeQuery(q);
        ResultSet result = Query.getQueryResult();
        
        while (result.next()){
            tID = result.getInt("courseID");
            tName = result.getString("courseName");
            tPhone = result.getString("coursePhone");
            tPar = result.getInt("coursePar");
            
            gCoursesList.add(new Course(tID, tName, tPhone, tPar));
        }
    }
    
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        
        //populate the table
        try {
            populateTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }    
    
    public void clearFields() {
        txtFieldCourseName.clear();
        txtFieldCoursePhone.clear();
        txtFieldCoursePar.clear();
    }
    
    public boolean validateFields() {
        name =  txtFieldCourseName.getText().trim();
        phone = txtFieldCoursePhone.getText().trim();
        par = txtFieldCoursePar.getText().trim();
        
        String error = "";
        if (name.equals("")){error += ("Name cannot be blank\n");}
        if (par.equals("")){error += ("Par cannot be blank\n");}
        if (checkIfInt(par) != true){error += ("Par must be a number\n");}
        if (par.length() != 2){error += ("Par should be in the following format 72\n");}
        if (phone.equals("")){error += ("Phone cannot be blank\n");}
        if (phone.length() > 14 || phone.length() < 10){error += ("Phone should be in the following format.  000-000-0000");}
        
        if(error.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error adding course");
            alert.setHeaderText("Cannot add course");
            alert.setContentText(error); //display the error mssg
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }
    
    public boolean checkIfInt(String s){
//        return Integer.parseInt(s);
        try 
        { 
            Integer.parseInt(s); 
            return true;
        }  
        catch (NumberFormatException e)  
        { 
            return false;
        } 
    }
    
}
