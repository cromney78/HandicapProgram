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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Report;
import util.DBConnection;
import util.Query;

/**
 * FXML Controller class
 *
 * @author Chad Romney
 */
public class ReportsController implements Initializable {

    @FXML    private Button btnClose;
    @FXML    private Tab tabHandicaps;
    @FXML    private TableView<Report> tblViewHandicaps;
    @FXML    private TableColumn<Report, String> colGolferName;
    @FXML    private TableColumn<Report, String> colHandicap;
    @FXML    private TableColumn<Report, String> colNumberScores;

    private String fullName, handicap, numScores;
    private ObservableList<Report> handicapList;

    
    private void getHandicapReport() throws SQLException {
        
        handicapList = FXCollections.observableArrayList();
        String tFullName, tNumScores;
        Double tHandicap;
        
        String q = "SELECT CONCAT(g.golferLastName, \", \", g.golferFirstName) AS fullName, "
                + "g.golferHandicap, count(s.score) AS scores "
                + "FROM golfer_table g, score_table s "
                + "WHERE g.golferID = s.golferID "
                + "GROUP BY fullName ASC;";
        
        Statement stmt = DBConnection.getConnection().createStatement();
        Query.makeQuery(q);
        ResultSet result = Query.getQueryResult();
        
        while (result.next()){
            tFullName = result.getString("fullName");
            tHandicap = result.getDouble("golferHandicap");
            tNumScores = Integer.toString(result.getInt("scores"));
            
            String hdcp = String.format("%.1f", tHandicap);
            
            handicapList.add(new Report(tFullName, hdcp, tNumScores));
            
            tblViewHandicaps.setItems(handicapList);

        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getHandicapReport();
        } catch (SQLException ex) {
            System.out.println("Error getting handicapReport: " + ex);
        }
        colGolferName.setCellValueFactory(new PropertyValueFactory<>("one"));
        colHandicap.setCellValueFactory(new PropertyValueFactory<>("two"));
        colNumberScores.setCellValueFactory(new PropertyValueFactory<>("three"));
        
        
    }    
    
    @FXML
    void btnClosePressed(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.setTitle("Options");
        Parent root = FXMLLoader.load(getClass().getResource("/view/Options.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
}
