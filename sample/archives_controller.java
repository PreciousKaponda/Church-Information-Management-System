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
 * Created by Kaponda on 21-Mar-21.
 */

public class archives_controller implements Initializable {
    @FXML
    private TableView<archives_tabular> archive_table;
    @FXML
    private TableColumn<archives_tabular, Integer> card_no_col;
    @FXML
    private TableColumn<archives_tabular, String> first_name_col, last_name_col, house_no_col, gender_col, zone_col, baptised_on_col, baptised_by_col, baptised_at_col;

    clerk_home callClerk;
    ObservableList<archives_tabular> archive_data;
    Connection conn;
    ResultSet rs;
    String select_query, del_trans_query, del_archive_query;
    int card_no, x;
    Boolean confirm_delete;
    TablePosition pos;
    TableColumn col;
    archives_tabular item;
    ObservableList getRow;
    PreparedStatement del_trans_archive, del_archive;
    Alert sql_error, delete_success;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //the value in PropertyValueFactory is the variable declared in the archives_tabular class
        card_no_col.setCellValueFactory(new PropertyValueFactory<>("card_no"));
        first_name_col.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        last_name_col.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        house_no_col.setCellValueFactory(new PropertyValueFactory<>("house_no"));
        gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
        zone_col.setCellValueFactory(new PropertyValueFactory<>("zone"));
        baptised_on_col.setCellValueFactory(new PropertyValueFactory<>("baptised_on"));
        baptised_by_col.setCellValueFactory(new PropertyValueFactory<>("baptised_by"));
        baptised_at_col.setCellValueFactory(new PropertyValueFactory<>("baptised_at"));

        archive_data = FXCollections.observableArrayList();

        try {
            conn = db.java_db();
            select_query = "SELECT * FROM archives;";
            rs = conn.createStatement().executeQuery(select_query);

            while (rs.next()){
                archive_data.add(new archives_tabular(rs.getInt("card_no"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("house_no"), rs.getString("gender"), rs.getString("zone"), rs.getString("baptism_date"), rs.getString("baptised_by"), rs.getString("baptism_location")));
            }
        } catch (SQLException ex){
            Logger.getLogger(archives_controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        archive_table.setItems(archive_data);
        //setting a selection model to allow selection of only one item at a time
        archive_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * SEARCH!!!!!
     */


    /**
     Method carrying the functionality deleting a user from the database and removing them from the table
     */
    public void delete() {
        //dialog box asking user to confirm deletion
        confirm_delete = confirmBox.affirm("Confirm Deletion", "Are You Sure to Delete This Member From the Database?", 380, 80);

        /*Get card_no value to use in the delete queries below*/
        pos = archive_table.getSelectionModel().getSelectedCells().get(0);
        x = pos.getRow();
        //item here is the table view type
        item = archive_table.getItems().get(x);
        col = pos.getTableColumn();
        //this gives the value in the selected cell
        card_no = (int) col.getCellObservableValue(item).getValue();

        //delete query removing the selected row from database
        try {
            conn = db.java_db();

            if (confirm_delete){
                //Delete a member from both the archive_transfers and archives tables
                del_trans_query = "DELETE FROM archive_transfers WHERE card_no = ?;";
                del_archive_query = "DELETE FROM archives WHERE card_no = ?;";

                del_trans_archive = conn.prepareStatement(del_trans_query);
                del_trans_archive.setInt(1, card_no);
                del_trans_archive.execute();

                del_archive = conn.prepareStatement(del_archive_query);
                del_archive.setInt(1, card_no);
                del_archive.execute();

                //alert box saying deletion was successful
                delete_success = new Alert(Alert.AlertType.INFORMATION, "MEMBER HAS BEEN DELETED FROM THIS TABLE AND THE ARCHIVE TRANSFERS TABLE", ButtonType.OK);
                delete_success.setHeaderText("SUCCESS");
                delete_success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                delete_success.showAndWait();

                //remove the selected item from the rest of the items
                archive_data = archive_table.getItems();
                getRow = archive_table.getSelectionModel().getSelectedItems();
                getRow.forEach(archive_data::remove);
            }
        } catch (Exception e) {
            sql_error = new Alert(Alert.AlertType.ERROR, "CLICK ON THE CARD NO. DIRECTLY TO DELETE A MEMBER", ButtonType.OK);
            sql_error.setHeaderText("FAILED DELETION");
            sql_error.setTitle("Kapita C.C.A.P. IMS - ERROR");
            sql_error.showAndWait();
        }
    }

    public void back_function() {
        callClerk = new clerk_home();
        callClerk.clerk();
    }

}