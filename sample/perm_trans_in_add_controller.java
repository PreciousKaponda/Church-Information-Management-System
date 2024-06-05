package sample;

import javafx.fxml.*;
import javafx.scene.control.*;
import java.sql.*;
/**
 * Created by Kaponda on 27 Mar 2021.
 */
public class perm_trans_in_add_controller {
    @FXML
    public TextField card_no_field, first_name_field, last_name_field, house_no_field, gender_field, zone_field, trans_from_field, date_in_field, baptised_on_field, baptised_by_field, baptised_at_field;

    transfers callTransfers;
    Connection conn;
    Alert card_no_alert, success, failure;
    PreparedStatement insert_transfer_details, insert_baptism_details, insert_personal_details;
    String add_transfer_details, add_baptism_details, add_personal_details;
    int card_no;
    String card_no_string, first_name, last_name, house_no, gender, zone, trans_from, date_in, baptised_on, baptised_by, baptised_at;

    public void add_new_perm_trans_in () {
        card_no_string = card_no_field.getText();
        first_name = first_name_field.getText();
        last_name = last_name_field.getText();
        house_no = house_no_field.getText();
        gender = gender_field.getText();
        zone = zone_field.getText();
        trans_from = trans_from_field.getText();
        date_in = date_in_field.getText();
        baptised_on = baptised_on_field.getText();
        baptised_by = baptised_by_field.getText();
        baptised_at = baptised_at_field.getText();

        //Validating card no. if its empty || not an integer
        if (card_no_string.isEmpty()) {
            card_no_alert = new Alert(Alert.AlertType.ERROR, "CARD NO. CANNOT BE EMPTY", ButtonType.OK);
            //card_no_alert.getIcons().add(new Image("img/ccap_logo.png"));
            card_no_alert.setHeaderText("EMPTY CARD NO.");
            card_no_alert.setTitle("Kapita C.C.A.P. IMS - ERROR");
            card_no_alert.showAndWait();
        } else {
            try {
                //Converting card_no_string to int
                card_no = Integer.parseInt(card_no_string);
            } catch (NumberFormatException e) {
                card_no_alert = new Alert(Alert.AlertType.WARNING, "CARD NO. MUST STRICTLY BE A NUMBER", ButtonType.OK);
                card_no_alert.setHeaderText("NUMBERS ONLY!");
                card_no_alert.setTitle("Kapita C.C.A.P. IMS");
                card_no_alert.showAndWait();
            }
        }


        //SQL Query to add form details into database
        try {
            conn = db.java_db();

            add_personal_details = "INSERT INTO personal_details (card_no, first_name, last_name , house_no,  gender, zone)" +
                    "VALUES (?, ?, ?, ? , ?, ?)";
            add_transfer_details = "INSERT INTO permanent_transfer_in (card_no, first_name, last_name , transferred_from,  date_transferred_in)" +
                    "VALUES (?, ?, ?, ? , ?)";
            add_baptism_details = "INSERT INTO baptism(baptism_date, baptised_by, baptism_location, card_no)" +
                    "VALUES (?, ?, ?, ?)";

            /**
             * insert personal details from form into the "personal_details" table in database
             * insert transfer details from form into the "permanent_transfer_in" table in database
             * insert baptism details from form into the "baptism" table in database
             */
            if (!first_name.isEmpty() || !last_name.isEmpty() ||!gender.isEmpty() || date_in.isEmpty() || baptised_on.isEmpty() || trans_from.isEmpty()) {
                //running query to insert personal details
                insert_personal_details = conn.prepareStatement(add_personal_details);
                insert_personal_details.setInt(1, card_no);
                insert_personal_details.setString(2, first_name);
                insert_personal_details.setString(3, last_name);
                insert_personal_details.setString(4, house_no);
                insert_personal_details.setString(5, gender);
                insert_personal_details.setString(6, zone);
                insert_personal_details.execute();

                //running query to insert "permanent transfer in" details
                insert_transfer_details = conn.prepareStatement(add_transfer_details);
                insert_transfer_details.setInt(1, card_no);
                insert_transfer_details.setString(2, first_name);
                insert_transfer_details.setString(3, last_name);
                insert_transfer_details.setString(4, trans_from);
                insert_transfer_details.setString(5, date_in);
                insert_transfer_details.execute();

                //running query to insert baptism details
                insert_baptism_details = conn.prepareStatement(add_baptism_details);
                insert_baptism_details.setString(1, baptised_on);
                insert_baptism_details.setString(2, baptised_by);
                insert_baptism_details.setString(3, baptised_at);
                insert_baptism_details.setInt(4, card_no);
                insert_baptism_details.execute();

                //Alert box informing user that inserting a new member has been successful
                success = new Alert(Alert.AlertType.INFORMATION, first_name + " " + last_name + " HAS BEEN INSERTED INTO THE TRANSFERS AND MEMBERS TABLES", ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } else {
                failure = new Alert(Alert.AlertType.WARNING, "Please fill all Transfer fields", ButtonType.OK);
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
        card_no_field.clear();
        first_name_field.clear();
        last_name_field.clear();
        house_no_field.clear();
        gender_field.clear();
        zone_field.clear();
        trans_from_field.clear();
        baptised_by_field.clear();
        baptised_at_field.clear();
        date_in_field.clear();
        baptised_on_field.clear();
    }

    public void done() {
        //call christians class to display the table so the user can see their changes
        perm_trans_in_view callPerm_trans_in_view = new perm_trans_in_view();
        callPerm_trans_in_view.perm_trans_in_table();
    }
    //Go back to clerk home screen when BACK label is pressed
    public void back_function() {
        callTransfers = new transfers();
        callTransfers.transfer();
    }
}
