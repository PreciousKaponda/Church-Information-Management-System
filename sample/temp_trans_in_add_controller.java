package sample;

import javafx.fxml.*;
import javafx.scene.control.*;
import java.sql.*;
/**
 * Created by Kaponda on 23 Mar 2021.
 */
public class temp_trans_in_add_controller {
    @FXML
    public TextField temp_card_no_field, first_name_field, last_name_field, trans_from_field, date_in_field, date_out_field;

    transfers callTransfers;
    Connection conn;
    Alert card_no_alert, success, failure;
    PreparedStatement insert_transfer_details;
    String add_transfer_details;
    int card_no;
    String temp_card_no_string, first_name, last_name, trans_from, date_in, date_out;

    public void add_new_temp_trans_in () {
        temp_card_no_string = temp_card_no_field.getText();
        first_name = first_name_field.getText();
        last_name = last_name_field.getText();
        trans_from = trans_from_field.getText();
        date_in = date_in_field.getText();
        date_out = date_out_field.getText();

        //Validating card no. if its empty || not an integer
        if (temp_card_no_string.isEmpty()) {
            card_no_alert = new Alert(Alert.AlertType.ERROR, "CARD NO. CANNOT BE EMPTY", ButtonType.OK);
            //card_no_alert.getIcons().add(new Image("img/ccap_logo.png"));
            card_no_alert.setHeaderText("EMPTY CARD NO.");
            card_no_alert.setTitle("Kapita C.C.A.P. IMS - ERROR");
            card_no_alert.showAndWait();
        } else {
            try {
                //Converting card_no_string to int
                card_no = Integer.parseInt(temp_card_no_string);
            } catch (NumberFormatException e) {
                card_no_alert = new Alert(Alert.AlertType.ERROR, "CARD NO. MUST STRICTLY BE A NUMBER", ButtonType.OK);
                card_no_alert.setHeaderText("NUMBERS ONLY!");
                card_no_alert.setTitle("Kapita C.C.A.P. IMS");
                card_no_alert.showAndWait();
            }
        }


        //SQL Query to add form details into database
        try {
            conn = db.java_db();
            add_transfer_details = "INSERT INTO temporary_transfer_in (temporary_card_no, first_name, last_name, transferring_from, date_transferred_in, date_transferring_out)" +
                    "VALUES (?, ?, ?, ? , ?, ?);";

            //insert form details into the "temporary_transfer_in" table in the database
            if (!last_name.isEmpty() || !trans_from.isEmpty() || !date_in.isEmpty()) {
                insert_transfer_details = conn.prepareStatement(add_transfer_details);
                insert_transfer_details.setInt(1, card_no);
                insert_transfer_details.setString(2, first_name);
                insert_transfer_details.setString(3, last_name);
                insert_transfer_details.setString(4, trans_from);
                insert_transfer_details.setString(5, date_in);
                insert_transfer_details.setString(6, date_out);
                insert_transfer_details.execute();

                //Alert box informing user that inserting a new member has been successful
                success = new Alert(Alert.AlertType.INFORMATION, first_name + " " + last_name + " HAS BEEN INSERTED INTO THE TEMPORARY TRANSFERS IN TABLE", ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } else {
                failure = new Alert(Alert.AlertType.WARNING, "THE ONLY FIELD THAT CAN BE EMPTY IS \"DATE TRANSFERRING OUT\"", ButtonType.OK);
                failure.setHeaderText("FORM ERROR");
                failure.setTitle("Kapita C.C.A.P. IMS - Form Error");
                failure.showAndWait();
            }
        } catch (Exception e){
            failure = new Alert(Alert.AlertType.WARNING, "CHECK THAT THE CARD NO. ISN'T ALREADY IN USE OR THAT THE FORM IS FILLED IN THE CORRECT FORMATS", ButtonType.OK);
            failure.setHeaderText(null);
            failure.setTitle("Kapita C.C.A.P. IMS - ERROR");
            failure.showAndWait();
        }

    }

    //Clear the entire form when "clear" button is pressed
    public void clear_form() {
        temp_card_no_field.clear();
        first_name_field.clear();
        last_name_field.clear();
        trans_from_field.clear();
        date_in_field.clear();
        date_out_field.clear();
    }

    public void done() {
        //call christians class to display the table so the user can see their changes
        temp_trans_in_view callTemp_trans_in_view = new temp_trans_in_view();
        callTemp_trans_in_view.temp_trans_in_table();
    }
    //Go back to clerk home screen when BACK label is pressed
    public void back_function() {
        callTransfers = new transfers();
        callTransfers.transfer();
    }
}
