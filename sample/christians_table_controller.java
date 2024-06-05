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
 * Created by Kaponda on 26-Apr-18.
 */

public class christians_table_controller implements Initializable {
    @FXML
    private TableView<christiansTabular> akhristuTable;
    @FXML
    private TableColumn<christiansTabular, Integer> card_no_col;
    @FXML
    private TableColumn<christiansTabular, String> first_name_col, last_name_col, house_no_col, gender_col, zone_col;
    @FXML
    public Label back_label;
    @FXML
    public Button del_btn;

    clerk_home callClerk;
    ObservableList<christiansTabular> christians_data;
    Connection conn;
    ResultSet rs;
    String select_query, del_personal_query, first_name, last_name;
    int card_no, x;
    Boolean confirm_delete;
    TablePosition pos;
    TableColumn col;
    christiansTabular item;
    ObservableList getRow;
    PreparedStatement delete_personal;
    Alert sql_error, delete_success;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //the value in PropertyValueFactory is the variable declared in the christiansTabular class
        card_no_col.setCellValueFactory(new PropertyValueFactory<>("card_no"));
        first_name_col.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        last_name_col.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        house_no_col.setCellValueFactory(new PropertyValueFactory<>("house_no"));
        gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
        zone_col.setCellValueFactory(new PropertyValueFactory<>("zone"));

        christians_data = FXCollections.observableArrayList();

        try {
            conn = db.java_db();
            select_query = "SELECT card_no, first_name, last_name, house_no, gender, zone\n" +
                    "FROM  personal_details\n" +
                    "GROUP BY card_no;";
            rs = conn.createStatement().executeQuery(select_query);

            while (rs.next()){
                christians_data.add(new christiansTabular(rs.getInt("card_no"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("house_no"), rs.getString("gender"), rs.getString("zone")));
            }
        } catch (SQLException ex){
            Logger.getLogger(christians_table_controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        akhristuTable.setItems(christians_data);
        //setting a selection model to allow selection of only one item at a time
        akhristuTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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
        pos = akhristuTable.getSelectionModel().getSelectedCells().get(0);
        x = pos.getRow();
        //item here is the table view type
        item = akhristuTable.getItems().get(x);
        col = pos.getTableColumn();
        //this gives the value in the selected cell
        card_no = (int) col.getCellObservableValue(item).getValue();

        //delete query removing the selected row from database
        try {
            conn = db.java_db();

            if (confirm_delete){
                //delete person with the selected card_no from the database
                del_personal_query = "DELETE FROM personal_details WHERE card_no = ?";

                delete_personal = conn.prepareStatement(del_personal_query);
                delete_personal.setInt(1, card_no);
                delete_personal.execute();

                //alert box saying deletion was successful
                delete_success = new Alert(Alert.AlertType.CONFIRMATION, "DELETION SUCCESSFUL", ButtonType.OK);
                delete_success.setHeaderText("SUCCESS");
                delete_success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                delete_success.showAndWait();

                //remove the selected item from the rest of the items
                christians_data = akhristuTable.getItems();
                getRow = akhristuTable.getSelectionModel().getSelectedItems();
                getRow.forEach(christians_data::remove);
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