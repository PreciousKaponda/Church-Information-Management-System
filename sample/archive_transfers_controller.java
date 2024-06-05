package sample;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.*;

/**
 * Created by Kaponda on 17-May-2021.
 */

public class archive_transfers_controller implements Initializable {
    @FXML
    TableView<archive_transfers_tabular> archive_transfers_table;
    @FXML
    TableColumn<archive_transfers_tabular, Integer> card_no_col;
    @FXML
    TableColumn<archive_transfers_tabular, String> first_name_col, last_name_col, outgoing_date_col, outgoing_location_col, incoming_date_col, incoming_location_col;

    clerk_home callClerk;
    ObservableList<archive_transfers_tabular> archive_transfers_data;
    Connection conn;
    ResultSet rs;
    String select_query;
    int card_no;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //the value in PropertyValueFactory is the variable declared in the archive_transfers_tabular class
        card_no_col.setCellValueFactory(new PropertyValueFactory<>("card_no"));
        first_name_col.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        last_name_col.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        outgoing_date_col.setCellValueFactory(new PropertyValueFactory<>("outgoing_date"));
        outgoing_location_col.setCellValueFactory(new PropertyValueFactory<>("outgoing_location"));
        incoming_date_col.setCellValueFactory(new PropertyValueFactory<>("incoming_date"));
        incoming_location_col.setCellValueFactory(new PropertyValueFactory<>("incoming_location"));

        archive_transfers_data = FXCollections.observableArrayList();

        try {
            conn = db.java_db();
            select_query = "SELECT a.card_no, a.first_name, a.last_name, t.outgoing_date, t.outgoing_location, t.incoming_date, t.incoming_location\n" +
                    "FROM archives a, archive_transfers t\n" +
                    "WHERE a.card_no = t.card_no\n" +
                    "GROUP BY t.card_no;";
            rs = conn.createStatement().executeQuery(select_query);

            while (rs.next()){
                archive_transfers_data.add(new archive_transfers_tabular(rs.getInt("card_no"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("outgoing_date"), rs.getString("outgoing_location"), rs.getString("incoming_date"), rs.getString("incoming_location")));
            }
        } catch (SQLException ex){
            Logger.getLogger(archive_transfers_controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        archive_transfers_table.setItems(archive_transfers_data);
        //setting a selection model to allow selection of only one item at a time
        archive_transfers_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    public void back_function() {
        callClerk = new clerk_home();
        callClerk.clerk();
    }
}

