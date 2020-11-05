/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Course;
import model.Golfer;
import model.Score;
import util.DBConnection;
import util.Query;

/**
 * FXML Controller class
 *
 * @author Chad Romney
 */
public class ScoresController implements Initializable {

    @FXML    private Tab tabGolfers;
    @FXML    private TableView<Golfer> tblViewGolfers;
    @FXML    private TableColumn<Golfer, String> colLastName;
    @FXML    private TableColumn<Golfer, String> colFirstName;
    @FXML    private Tab tabScores;
    @FXML    private TableView<Score> tblViewScores;
    @FXML    private TableColumn<Score, String> colScoreLastName;
    @FXML    private TableColumn<Score, Integer> colRecentScore;
    @FXML    private Label lblGolferName;
    @FXML    private TextField txtFieldGolferName;
    @FXML    private Label lblGolfCourse;
    @FXML    private ComboBox<String> comboBoxCourses;
    @FXML    private Label lblDate;
    @FXML    private Label lblScore;
    @FXML    private TextField txtFieldScore;
    @FXML    private Button btnSave;
    @FXML    private Button btnEdit;
    @FXML    private Button btnDelete;
    @FXML    private Button btnCancel;
    @FXML    private Label lblHandicap;
    @FXML    private TextField txtFieldHandicap;
    
    private boolean exists;
    private Score score;
    private Golfer golfer;
    private String mName, mScore, mCourse;
    private int mCourseID, mPar, mStrokesOverPar;
    private Double mHandicap = 0.0;
    private ObservableList<Golfer> golferList;
    private ObservableList<Course> courseList;
    private ObservableList<Score> scoreList;
    private ObservableList<String> courseDropDownList;
    
    
    
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
        score = tblViewScores.getSelectionModel().getSelectedItem();
        
        if (score == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Score not selected");
            alert.setContentText("Select a score to continue.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete?");
            alert.setHeaderText("Delete Score?");
            alert.setContentText("Click OK to Delete");
            
            alert.showAndWait().ifPresent(buttonResult -> {
                String q = "DELETE FROM score_table WHERE scoreID = " + score.getScoreID();
                try {
                   Statement stmt = DBConnection.getConnection().createStatement();
                    Query.makeQuery(q);
                    populateTable();
                } catch (SQLException e) {
                    System.out.println("SQLException = " + e);
                }
            });
            updateHandicap();
        }
    }

    @FXML
    void btnEditPressed(ActionEvent event) {
        System.out.println("btnEditPressed");
        
        exists = true;
        score = tblViewScores.getSelectionModel().getSelectedItem();
        
        if (score == null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Score not selected");
                alert.setContentText("Select a score to continue.");
                alert.showAndWait();
            }
        else {
            txtFieldGolferName.setText(score.getFullName());
/***
 * TODO Double Check below is working correctly
 */
            comboBoxCourses.getSelectionModel().select(score.getCourseID());
            txtFieldScore.setText(Integer.toString(score.getScore()));
        }
    }

    @FXML
    void btnSavePressed(ActionEvent event) throws SQLException {
        System.out.println("btnSavePressed");
        
        String q = "";
        if(validateFields()){
            if(!exists){
                q = "INSERT INTO score_table SET " +
                    "golferID = " + golfer.getGolferID() + ", " +
                    "courseID = " + mCourseID + ", " +
                    "scoreDate = current_timestamp(), " +
                    "strokesOverPar = " + mStrokesOverPar + ", " +
                    "score = " + Integer.parseInt(mScore) + ";";
            } else {
                
                int id = score.getScoreID();
                
                for(Golfer golfer : golferList){
                    if(golfer.getGolferID() == score.getGolferID()) {
                        this.golfer = golfer;
                    }
                }
                
                q = "UPDATE score_table SET " +
                    "golferID = " + golfer.getGolferID() + ", " +
                    "courseID = " + mCourseID + ", " +
                    "scoreDate = current_timestamp(), " +
                    "strokesOverPar = " + mStrokesOverPar + ", " +
                    "score = " + Integer.parseInt(mScore) + " " + 
                    "WHERE scoreID = " + id + ";";
            }
            Statement stmt = DBConnection.getConnection().createStatement();
            Query.makeQuery(q);
            ResultSet result = Query.getQueryResult();
            
            updateHandicap();

            exists = false;
            populateTable();
            clearFields();
        }
    }
    
    @FXML
    void setGolferName(MouseEvent event) throws SQLException {
        System.out.println("setGolferName()");
                
        golfer = tblViewGolfers.getSelectionModel().getSelectedItem();
        updateHandicap();
        txtFieldGolferName.setText(golfer.getGolferFirstName() + " " + golfer.getGolferLastName());
        txtFieldHandicap.setText(Double.toString(mHandicap));
    }
        

    private void populateTable() throws SQLException {
        getAllGolfers();
        getAllScores();
        tblViewGolfers.setItems(golferList);
        tblViewScores.setItems(scoreList);
    }

    private void updateHandicap() throws SQLException {
        System.out.println("updateHandicap()");
        
        int numScores = 0;
        int sumOverPar = 0;
        
        String q = "SELECT * FROM score_table WHERE golferID = " + golfer.getGolferID() + ";";
        Statement stmt = DBConnection.getConnection().createStatement();
        Query.makeQuery(q);
        ResultSet result = Query.getQueryResult();
        
        while (result.next()){
            sumOverPar += result.getInt("strokesOverPar");
            numScores += 1;
        }
        mHandicap = (sumOverPar * 1.0)/numScores;    
        
        setGolferHandicap();
                
    }
    
    void setGolferHandicap() throws SQLException{
        System.out.println("updateHandicap() golferID: " + golfer.getGolferID());
        
        String q = "UPDATE golfer_table SET " +
                   "golferHandicap = " + mHandicap + 
                   "WHERE golferID = " + golfer.getGolferID() + ";";
        
        Statement stmt = DBConnection.getConnection().createStatement();
        Query.makeQuery(q);
        ResultSet result = Query.getQueryResult();
    }

    //populates all courses and dropdown list
    private void getAllCourses() throws SQLException {
        
        System.out.println("getAllCourses()");
        
        courseDropDownList = FXCollections.observableArrayList();
        courseList = FXCollections.observableArrayList();
        
        int tID;
        String tName;
        String tPhone;
        int tPar;
        
        String q = "SELECT * FROM course_table ORDER BY courseName;";
        Statement stmt = DBConnection.getConnection().createStatement();
        Query.makeQuery(q);
        ResultSet result = Query.getQueryResult();
        
        while (result.next()){
            tName = result.getString("courseName");
            courseDropDownList.add(tName);
        }
        while (result.next()){
            tID = result.getInt("courseID");
            tName = result.getString("courseName");
            tPhone = result.getString("coursePhone");
            tPar = result.getInt("coursePar");
            
            courseList.add(new Course(tID, tName, tPhone, tPar));
        }
    }
    
    private void getAllGolfers() throws SQLException {
        System.out.println("getAllGolfers");
        
        golferList = FXCollections.observableArrayList();
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
            
            golferList.add(new Golfer(tID, tFirstName, tLastName, tPhone, tEmail, tHandicap));
        }
    }
    
    private void getAllScores() throws SQLException {
        System.out.println("getAllScores()");
        
        scoreList = FXCollections.observableArrayList();
        
        int tScoreID;
        int tGolferID;
        int tCourseID;
        int tScore;
        String tFullName;
        
        String q = "SELECT s.scoreID, s.golferID, s.courseID, s.score, " +
                    "CONCAT(g.golferFirstName, \" \", g.golferLastName) AS fullName  " +
                    "FROM score_table s, golfer_table g " +
                    "WHERE s.golferID = g.golferID ORDER BY scoreDate DESC;";
        
        Statement stmt = DBConnection.getConnection().createStatement();
        Query.makeQuery(q);
        ResultSet result = Query.getQueryResult();
        
        while (result.next()){
            tScoreID = result.getInt("scoreID");
            tGolferID = result.getInt("golferID");
            tCourseID = result.getInt("courseID");
            tScore = result.getInt("score");
            tFullName = result.getString("fullName");
            
//            System.out.println("tFullName: " + tFullName);

            scoreList.add(new Score(tScoreID, tGolferID, tCourseID, tScore, tFullName));
        }
    }
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initialize");
        try {
            getAllCourses();
            populateTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        colLastName.setCellValueFactory(new PropertyValueFactory<>("golferLastName"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("golferFirstName"));
        colScoreLastName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colRecentScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        
        comboBoxCourses.setItems(courseDropDownList);
        comboBoxCourses.setValue(null);
    }

    private boolean validateFields() throws SQLException {

        int courseIndex = comboBoxCourses.getSelectionModel().getSelectedIndex();
        System.out.println("courseIndex: " + courseIndex);
        mName = txtFieldGolferName.getText().trim();
        mScore = txtFieldScore.getText().trim();
        System.out.println("validateFields mName: " + mName);
        
        
        String error = "";
        if (mName.equals("")){error += ("Please select a golfer from the list. If no golfers are available please return to the golfer screen and enter one.\n\n");}
        if (courseIndex == -1){error += ("Golf Course cannot be empty. If no golf courses are available please return to the golfer screen and enter one.\n\n");}
        if (checkIfInt(mScore) != true){error += ("Score must be a integer.\n");}
        if (mScore.equals("")){error += ("Score cannot be blank.\n");}
        
        if(error.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Cannot add score");
            alert.setContentText(error);
            alert.showAndWait();
            return false;
        } else {
            
            mCourse = comboBoxCourses.getValue();
            
            getGolfCourseInfo();            

            //calculate Strokes over par for handicap
            mStrokesOverPar = Integer.parseInt(mScore) - mPar;
            System.out.println("mStrokesOverPar: " + mStrokesOverPar);

//            System.out.println("mScore: " + mScore);
//            System.out.println("mPar: " + mPar);
//            System.out.println("mStrokesOverPar: " + mStrokesOverPar);

            return true;
        }
    }
    
    public boolean checkIfInt(String s){
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

    private void clearFields() {
        txtFieldGolferName.clear();
        comboBoxCourses.setValue(null);
        txtFieldScore.clear();
        txtFieldHandicap.clear();
    }

    private void getGolfCourseInfo() throws SQLException {
        
        /***  Get CourseID from server with matching course name  ***/
        String q = "SELECT * FROM course_table ORDER BY courseName;";
        Statement stmt = DBConnection.getConnection().createStatement();
        Query.makeQuery(q);
        ResultSet result = Query.getQueryResult();

        while (result.next()){
            System.out.println("validateFields() while mCourse: " + mCourse);

            if (mCourse.equals(result.getString("courseName"))) {

                mCourseID = result.getInt("courseID");
                mPar = result.getInt("coursePar");
            }
        }

    }

    private void getGolfer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
