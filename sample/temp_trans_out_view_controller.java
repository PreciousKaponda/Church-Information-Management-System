package sample;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.*;

/**
 * Created by Kaponda on 26 Mar 2021.
 */
public class temp_trans_out_view_controller implements Initializable{
    @FXML
    private TableView<temp_trans_out_tabular> temp_trans_out_table;
    @FXML
    private TableColumn<temp_trans_out_tabular, Integer> card_no_col;
    @FXML
    private TableColumn<temp_trans_out_tabular, String> first_name_col, last_name_col, trans_to_col, date_out_col, date_in_col;
    @FXML
    public Label back_label;
    @FXML
    public Button del_btn;

    transfers callTransfers;
    Connection conn;
    ObservableList<temp_trans_out_tabular> temp_trans_out_data;
    ResultSet rs;
    String select_query, del_query;
    int card_no, x;
    Boolean confirm_delete;
    TablePosition pos;
    TableColumn col;
    temp_trans_out_tabular item;
    ObservableList getRow;
    PreparedStatement delete_member;
    Alert sql_error, delete_success;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //the value in PropertyValueFactory is the variable declared in the temp_trans_out_tabular class
        card_no_col.setCellValueFactory(new PropertyValueFactory<>("card_no"));
        first_name_col.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        last_name_col.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        trans_to_col.setCellValueFactory(new PropertyValueFactory<>("transfer_to"));
        date_out_col.setCellValueFactory(new PropertyValueFactory<>("date_transfer_out"));
        date_in_col.setCellValueFactory(new PropertyValueFactory<>("date_transfer_in"));

        temp_trans_out_data = FXCollections.observableArrayList();

        try {
            conn = db.java_db();
            select_query = "SELECT * FROM temporary_transfer_out;";
            rs = conn.createStatement().executeQuery(select_query);

            while (rs.next()){
                temp_trans_out_data.add(new temp_trans_out_tabular(rs.getInt("card_no"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("transferring_to"), rs.getString("date_transferring_out"), rs.getString("date_transferring_in")));
            }
        } catch (SQLException ex){
            Logger.getLogger(temp_trans_in_view_controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        temp_trans_out_table.setItems(temp_trans_out_data);
        //setting a selection model to allow selection of only one item at a time
        temp_trans_out_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void delete() {
        //dialog box asking user to confirm deletion
        confirm_delete = confirmBox.affirm("Confirm Deletion", "Are You Sure You Want to Delete This Member?", 320, 80);

        /*Get card_no value to use in the delete queries below*/
        pos = temp_trans_out_table.getSelectionModel().getSelectedCells().get(0);
        x = pos.getRow();
        //item here is the table view type
        item = temp_trans_out_table.getItems().get(x);
        col = pos.getTableColumn();
        //this gives the value in the selected cell
        card_no = (int) col.getCellObservableValue(item).getValue();

        //delete query removing the selected row from database
        try {
            conn = db.java_db();

            if (confirm_delete){
                /*
                check if the card_no is also used in the baptism table so it can be deleted from there too if true
                 */
                del_query = "DELETE FROM temporary_transfer_out WHERE card_no = ?";

                delete_member = conn.prepareStatement(del_query);
                delete_member.setInt(1, card_no);
                delete_member.execute();

                //alert box saying deletion was successful
                delete_success = new Alert(Alert.AlertType.CONFIRMATION, "Deletion Successful", ButtonType.OK);
                delete_success.setHeaderText("SUCCESS");
                delete_success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                delete_success.showAndWait();

                //remove the selected item from the rest of the items
                temp_trans_out_data = temp_trans_out_table.getItems();
                getRow = temp_trans_out_table.getSelectionModel().getSelectedItems();
                getRow.forEach(temp_trans_out_data::remove);
            }
        } catch (Exception e) {
            sql_error = new Alert(Alert.AlertType.ERROR, "Click on the Card No. directly to delete a member", ButtonType.OK);
            sql_error.setHeaderText("FAILED DELETION");
            sql_error.setTitle("Kapita C.C.A.P. IMS - ERROR");
            sql_error.showAndWait();
        }
    }

    public void back_function() {
        callTransfers = new transfers();
        callTransfers.transfer();
    }
}
