package sample;

import javafx.fxml.*;
import javafx.scene.control.*;
import java.sql.*;
/**
 * Created by Kaponda on 26 Mar 2021.
 */
public class temp_trans_out_add_controller {
    @FXML
    public TextField search_field, date_trans_out_field, transferring_to_field, date_trans_in_field;
    @FXML
    Label card_no_label, first_name_label, last_name_label, house_no_label, gender_label, zone_label, baptised_on_label, baptised_by_label, baptised_at_label;

    transfers callTransfers;
    Connection conn;
    Alert card_no_alert, success, error, warning;
    PreparedStatement prep_search_stmt, insert_transfer_details;
    ResultSet search_result_set;
    String db_first_name, db_last_name, db_house_no, db_gender, db_zone, db_baptised_on, db_baptised_by, db_baptised_at, insert_transfer_details_query;
    String search_word, search_query, date_transfer_out, transfer_to, date_transfer_in;
    int card_no, db_card_no;

    public void search() {
        conn = db.java_db();

        //initialising the preparedStatement and resultSet so they can be closed after executing their duties
        prep_search_stmt = null;
        search_result_set = null;

        search_word = search_field.getText();

        //Validating card no. if its empty || not a characters/s
        if (search_word.isEmpty()) {
            card_no_alert = new Alert(Alert.AlertType.ERROR, "CARD NO. CANNOT BE EMPTY", ButtonType.OK);
            card_no_alert.setHeaderText("EMPTY CARD NO.!");
            card_no_alert.setTitle("Kapita C.C.A.P. IMS - ERROR");
            card_no_alert.showAndWait();
        } else {
            //Converting the card no. to int
            try {
                card_no = Integer.parseInt(search_word);
            } catch (NumberFormatException e) {
                card_no_alert = new Alert(Alert.AlertType.ERROR, "CARD NO. MUST STRICTLY BE A NUMBER", ButtonType.OK);
                card_no_alert.setHeaderText("NUMBERS ONLY!");
                card_no_alert.setTitle("Kapita C.C.A.P. IMS - ERROR");
                card_no_alert.showAndWait();
            }
        }

        //search for member's details using the card_no given & display them in the labels so the user can see what details they're changing
        try {
            search_query = "SELECT p.card_no, p.first_name, p.last_name, p.house_no, p.gender, p.zone, b.baptism_date, b.baptised_by, b.baptism_location\n" +
                    "FROM  personal_details p, baptism b\n" +
                    "WHERE p.card_no = ?\n" +
                    "AND b.card_no = ?\n" +
                    "GROUP BY p.card_no;";

            prep_search_stmt = conn.prepareStatement(search_query);
            prep_search_stmt.setInt(1, card_no);
            prep_search_stmt.setInt(2, card_no);
            search_result_set = prep_search_stmt.executeQuery();

            if(search_result_set.next()){
                //resultSet = prep_search_stmt.getResultSet()
                db_card_no = search_result_set.getInt("card_no");
                db_first_name = search_result_set.getString("first_name");
                db_last_name = search_result_set.getString("last_name");
                db_house_no = search_result_set.getString("house_no");
                db_gender = search_result_set.getString("gender");
                db_zone = search_result_set.getString("zone");
                db_baptised_on = search_result_set.getString("baptism_date");
                db_baptised_by = search_result_set.getString("baptised_by");
                db_baptised_at = search_result_set.getString("baptism_location");

                //Display member data on the labels as a reference
                card_no_label.setText("CARD NO: " + db_card_no);
                first_name_label.setText("FIRST NAME: " + db_first_name);
                last_name_label.setText("LAST NAME: " + db_last_name);
                house_no_label.setText("HOUSE NO.: " + db_house_no);
                gender_label.setText("GENDER: " + db_gender);
                zone_label.setText("ZONE: " + db_zone);
                baptised_on_label.setText("BAPTISM DATE: " + db_baptised_on);
                baptised_by_label.setText("BAPTISED BY: " + db_baptised_by);
                baptised_at_label.setText("BAPTISM LOCATION: " + db_baptised_at);
            } else {
                error = new Alert(Alert.AlertType.WARNING, "NO MEMBER FOUND WITH THAT CARD NO.", ButtonType.OK);
                error.setHeaderText("CARD NO. NOT FOUND");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        } catch (SQLException e) {

        } finally {
            try {
                prep_search_stmt.close();
                search_result_set.close();
            } catch (SQLException ex) {

            }
        }
    }

    //insert member into temporary_transfer_out table
    public void add_new_temp_trans_out () {
        conn = db.java_db();
        //retrieve transfer details from the textFields
        date_transfer_out = date_trans_out_field.getText();
        transfer_to = transferring_to_field.getText();
        date_transfer_in = date_trans_in_field.getText();

        //Validate an empty textField
        if (date_transfer_out.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "PLEASE FILL THE \"DATE TRANSFERRING OUT\" FIELD, IT IS REQUIRED", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            //Query inserting a member into the temporary_transfer_out table
            insert_transfer_details_query = "INSERT INTO temporary_transfer_out (first_name, last_name, date_transferring_out, transferring_to, date_transferring_in, card_no)" +
                    "VALUES (?, ?, ?, ?, ?, ?);";

            //Execute query
            try {
                //insert member into temporary_transfer_out table
                insert_transfer_details = conn.prepareStatement(insert_transfer_details_query);
                insert_transfer_details.setString(1, db_first_name);
                insert_transfer_details.setString(2, db_last_name);
                insert_transfer_details.setString(3, date_transfer_out);
                insert_transfer_details.setString(4, transfer_to);
                insert_transfer_details.setString(5, date_transfer_in);
                insert_transfer_details.setInt(6, db_card_no);
                insert_transfer_details.execute();

                //alert box informing user that the member has been successfully inserted into temporary_transfer_out table
                success = new Alert(Alert.AlertType.INFORMATION, db_first_name + " " + db_last_name + " HAS BEEN INSERTED INTO THE TEMPORARY TRANSFERS OUT TABLE", ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPLOAD " + db_first_name + " " + db_last_name + " INTO THE TEMPORARY TRANSFERS OUT TABLE", ButtonType.OK);
                error.setHeaderText("FAILED UPLOAD");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }

    //Clear the entire form when "clear" button is pressed
    public void clear_form() {
        search_field.clear();
        transferring_to_field.clear();
        date_trans_out_field.clear();
        date_trans_in_field.clear();
    }

    public void done() {
        //call christians class to display the table so the user can see their changes
        temp_trans_out_view callTemp_trans_out_view = new temp_trans_out_view();
        callTemp_trans_out_view.temp_trans_out_table();
    }
    //Go back to clerk home screen when BACK label is pressed
    public void back_function() {
        callTransfers = new transfers();
        callTransfers.transfer();
    }
}
