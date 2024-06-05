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
 * Created by Kaponda on 05 Aug 2021.
 */

public class baptism_view_controller implements Initializable {
    @FXML
    private TableView<baptism_tabular> baptisms_table;
    @FXML
    private TableColumn<baptism_tabular, Integer> card_no_col;
    @FXML
    private TableColumn<baptism_tabular, String> first_name_col, last_name_col, baptised_on_col, baptised_by_col, baptised_at_col;

    clerk_home callClerk;
    ObservableList<baptism_tabular> baptism_data;
    Connection conn;
    ResultSet rs;
    String select_query, del_person_query, name;
    int card_no, x;
    Boolean confirm_delete;
    TablePosition pos;
    TableColumn col;
    baptism_tabular item;
    ObservableList getRow;
    PreparedStatement delete_person;
    Alert sql_error, delete_success;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //the value in PropertyValueFactory is the variable declared in the baptism_tabular class
        card_no_col.setCellValueFactory(new PropertyValueFactory<>("card_no"));
        first_name_col.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        last_name_col.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        baptised_on_col.setCellValueFactory(new PropertyValueFactory<>("baptised_on"));
        baptised_by_col.setCellValueFactory(new PropertyValueFactory<>("baptised_by"));
        baptised_at_col.setCellValueFactory(new PropertyValueFactory<>("baptised_at"));

        baptism_data = FXCollections.observableArrayList();

        try {
            conn = db.java_db();
            select_query = "SELECT b.card_no, p.first_name, p.last_name, b.baptism_date, b.baptised_by, b.baptism_location\n" +
                    "FROM baptism b, personal_details p\n" +
                    "WHERE b.card_no = p.card_no\n" +
                    "GROUP BY b.card_no;";
            rs = conn.createStatement().executeQuery(select_query);

            while (rs.next()){
                baptism_data.add(new baptism_tabular(rs.getInt("card_no"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("baptism_date"), rs.getString("baptised_by"), rs.getString("baptism_location")));
            }
        } catch (SQLException ex){
            Logger.getLogger(bapt_child_view_controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        baptisms_table.setItems(baptism_data);
        //setting a selection model to allow selection of only one item at a time
        baptisms_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    //Method carrying the functionality deleting a user from the database and removing them from the table
    public void delete() {
        //dialog box asking user to confirm deletion
        confirm_delete = confirmBox.affirm("Confirm Deletion", "Are You Sure to Delete This Member From the Database?", 380, 80);

        /*Get card_no value to use in the delete queries below*/
        pos = baptisms_table.getSelectionModel().getSelectedCells().get(0);
        x = pos.getRow();
        //item here is the table view type
        item = baptisms_table.getItems().get(x);
        col = pos.getTableColumn();
        //this gives the value in the selected cell
        card_no = (int) col.getCellObservableValue(item).getValue();

        //delete query removing the selected row from database
        try {
            conn = db.java_db();

            if (confirm_delete){
                del_person_query = "DELETE FROM baptism WHERE card_no = ?";

                delete_person = conn.prepareStatement(del_person_query);
                delete_person.setInt(1, card_no);
                delete_person.execute();

                //alert box saying deletion was successful
                delete_success = new Alert(Alert.AlertType.CONFIRMATION, "Delete successful", ButtonType.OK);

                //remove the selected item from the rest of the items
                baptism_data = baptisms_table.getItems();
                getRow = baptisms_table.getSelectionModel().getSelectedItems();
                getRow.forEach(baptism_data::remove);
            } else {
                sql_error = new Alert(Alert.AlertType.ERROR, "Click on the card_no directly to delete a member", ButtonType.OK);
                sql_error.showAndWait();
            }
        } catch (Exception e) {
            /*sql_error = new Alert(Alert.AlertType.ERROR, "Click on the card_no directly to delete a member", ButtonType.OK);
            sql_error.showAndWait();*/
        }
    }


    //Take user back to the clerk panel
    public void back_function() {
        callClerk = new clerk_home();
        callClerk.clerk();
    }
}
