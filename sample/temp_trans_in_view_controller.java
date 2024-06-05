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
 * Created by Kaponda on 23 Mar 2021.
 */
public class temp_trans_in_view_controller implements Initializable{
    @FXML
    private TableView<temp_trans_in_tabular> temp_trans_in_table;
    @FXML
    private TableColumn<temp_trans_in_tabular, Integer> temp_card_no_col;
    @FXML
    private TableColumn<temp_trans_in_tabular, String> first_name_col, last_name_col, trans_from_col, date_in_col, date_out_col;
    @FXML
    public Label back_label;
    @FXML
    public Button del_btn;

    transfers callTransfers;
    Connection conn;
    ObservableList<temp_trans_in_tabular> temp_trans_in_data;
    ResultSet rs;
    String select_query, del_query;
    int card_no, x;
    Boolean confirm_delete;
    TablePosition pos;
    TableColumn col;
    temp_trans_in_tabular item;
    ObservableList getRow;
    PreparedStatement delete_member;
    Alert sql_error, delete_success;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //the value in PropertyValueFactory is the variable declared in the temp_trans_tabular class
        temp_card_no_col.setCellValueFactory(new PropertyValueFactory<>("card_no"));
        first_name_col.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        last_name_col.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        trans_from_col.setCellValueFactory(new PropertyValueFactory<>("transfer_from"));
        date_in_col.setCellValueFactory(new PropertyValueFactory<>("date_transfer_in"));
        date_out_col.setCellValueFactory(new PropertyValueFactory<>("date_transfer_out"));

        temp_trans_in_data = FXCollections.observableArrayList();

        try {
            conn = db.java_db();
            select_query = "SELECT * FROM temporary_transfer_in;";
            rs = conn.createStatement().executeQuery(select_query);

            while (rs.next()){
                temp_trans_in_data.add(new temp_trans_in_tabular(rs.getInt("temporary_card_no"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("transferring_from"), rs.getString("date_transferred_in"), rs.getString("date_transferring_out")));
            }
        } catch (SQLException ex){
            Logger.getLogger(temp_trans_in_view_controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        temp_trans_in_table.setItems(temp_trans_in_data);
        //setting a selection model to allow selection of only one item at a time
        temp_trans_in_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void delete() {
        //dialog box asking user to confirm deletion
        confirm_delete = confirmBox.affirm("Confirm Deletion", "Are You Sure You Want to Delete This Member?", 320, 80);

        /*Get card_no value to use in the delete queries below*/
        pos = temp_trans_in_table.getSelectionModel().getSelectedCells().get(0);
        x = pos.getRow();
        //item here is the table view type
        item = temp_trans_in_table.getItems().get(x);
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
                del_query = "DELETE FROM temporary_transfer_in WHERE temporary_card_no = ?";

                delete_member = conn.prepareStatement(del_query);
                delete_member.setInt(1, card_no);
                delete_member.execute();

                //alert box saying deletion was successful
                delete_success = new Alert(Alert.AlertType.CONFIRMATION, "DELETION SUCCESSFUL", ButtonType.OK);
                delete_success.setHeaderText("SUCCESS");
                delete_success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                delete_success.showAndWait();

                //remove the selected item from the rest of the items
                temp_trans_in_data = temp_trans_in_table.getItems();
                getRow = temp_trans_in_table.getSelectionModel().getSelectedItems();
                getRow.forEach(temp_trans_in_data::remove);
            }
        } catch (Exception e) {
            sql_error = new Alert(Alert.AlertType.ERROR, "CLICK ON THE CARD NO. DIRECTLY TO DELETE A MEMBER", ButtonType.OK);
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
