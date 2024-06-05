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
 * Created by Kaponda on 17 Apr 2021.
 */
public class bapt_child_view_controller implements Initializable {
    @FXML
    private TableView<bapt_child_tabular> bapt_child_table;
    @FXML
    private TableColumn<bapt_child_tabular, Integer> cert_no_col;
    @FXML
    private TableColumn<bapt_child_tabular, String> name_col, gender_col, dob_col, parent_col, house_no_col, zone_col, baptised_on_col, baptised_by_col, witness_col;

    clerk_home callClerk;
    ObservableList<bapt_child_tabular> bapt_child_data;
    Connection conn;
    ResultSet rs;
    String select_query, del_person_query, name;
    int cert_no, x;
    Boolean confirm_delete;
    TablePosition pos;
    TableColumn col;
    bapt_child_tabular item;
    ObservableList getRow;
    PreparedStatement delete_person;
    Alert sql_error, delete_success;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //the value in PropertyValueFactory is the variable declared in the bapt_child_tabular class
        cert_no_col.setCellValueFactory(new PropertyValueFactory<>("cert_no"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dob_col.setCellValueFactory(new PropertyValueFactory<>("dob"));
        parent_col.setCellValueFactory(new PropertyValueFactory<>("parent"));
        house_no_col.setCellValueFactory(new PropertyValueFactory<>("house_no"));
        zone_col.setCellValueFactory(new PropertyValueFactory<>("zone"));
        baptised_on_col.setCellValueFactory(new PropertyValueFactory<>("baptised_on"));
        baptised_by_col.setCellValueFactory(new PropertyValueFactory<>("baptised_by"));
        witness_col.setCellValueFactory(new PropertyValueFactory<>("witness"));

        bapt_child_data = FXCollections.observableArrayList();

        try {
            conn = db.java_db();
            select_query = "SELECT *FROM baptised_children;";
            rs = conn.createStatement().executeQuery(select_query);

            while (rs.next()){
                bapt_child_data.add(new bapt_child_tabular(rs.getInt("certificate_no"), rs.getString("name"), rs.getString("gender"), rs.getString("date_of_birth"), rs.getString("parent"), rs.getString("house_no"), rs.getString("zone"), rs.getString("baptism_date"), rs.getString("baptised_by"), rs.getString("witness")));
            }
        } catch (SQLException ex){
            Logger.getLogger(bapt_child_view_controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        bapt_child_table.setItems(bapt_child_data);
        //setting a selection model to allow selection of only one item at a time
        bapt_child_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    //Method carrying the functionality deleting a user from the database and removing them from the table
    public void delete() {
        //dialog box asking user to confirm deletion
        confirm_delete = confirmBox.affirm("Confirm Deletion", "Are You Sure to Delete This Member From the Database?", 380, 80);

        /*Get cert_no value to use in the delete queries below*/
        pos = bapt_child_table.getSelectionModel().getSelectedCells().get(0);
        x = pos.getRow();
        //item here is the table view type
        item = bapt_child_table.getItems().get(x);
        col = pos.getTableColumn();
        //this gives the value in the selected cell
        cert_no = (int) col.getCellObservableValue(item).getValue();

        //delete query removing the selected row from database
        try {
            conn = db.java_db();

            if (confirm_delete){
                del_person_query = "DELETE FROM baptised_children WHERE certificate_no = ?";

                delete_person = conn.prepareStatement(del_person_query);
                delete_person.setInt(1, cert_no);
                delete_person.execute();

                //alert box saying deletion was successful
                delete_success = new Alert(Alert.AlertType.CONFIRMATION, "Delete successful", ButtonType.OK);

                //remove the selected item from the rest of the items
                bapt_child_data = bapt_child_table.getItems();
                getRow = bapt_child_table.getSelectionModel().getSelectedItems();
                getRow.forEach(bapt_child_data::remove);
            }
        } catch (Exception e) {
            sql_error = new Alert(Alert.AlertType.ERROR, "Click on the cert_no directly to delete a child", ButtonType.OK);
            sql_error.showAndWait();
        }
    }


    //Take user back to the clerk panel
    public void back_function() {
        callClerk = new clerk_home();
        callClerk.clerk();
    }
}
