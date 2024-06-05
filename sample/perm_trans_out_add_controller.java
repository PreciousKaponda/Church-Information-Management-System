package sample;

import javafx.fxml.*;
import javafx.scene.control.*;
import java.sql.*;
/**
 * Created by Kaponda on 27 Mar 2021.
 */
public class perm_trans_out_add_controller {
    @FXML
    public TextField search_field, transfer_date_field, transferred_to_field;
    @FXML
    Label card_no_label, first_name_label, last_name_label, house_no_label, gender_label, zone_label, baptised_on_label, baptised_by_label, baptised_at_label;

    transfers callTransfers;
    Connection conn;
    Alert card_no_alert, success, error, warning;
    PreparedStatement prep_search_stmt, insert_transfer_details, insert_archive_details, delete_from_baptism, delete_from_personal_details;
    int card_no, db_card_no;
    ResultSet search_result_set;
    String search_word, search_query, db_first_name, db_last_name, db_house_no, db_gender, db_zone, db_baptised_on, db_baptised_by, db_baptised_at, date_transferred_out, transferred_to;
    String insert_transfer_details_query, insert_archive_details_query, delete_from_baptism_query, delete_from_personalDetails_query;

    public void search () {
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

    //Insert member into permanent_transfers_out and archives tables. Delete from personal_details and baptism tables
    public void add_new_perm_trans_out () {
        conn = db.java_db();
        //retrieve transfer details from the textFields
        date_transferred_out = transfer_date_field.getText();
        transferred_to = transferred_to_field.getText();

        //Validate an empty textField
        if (date_transferred_out.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "PLEASE FILL THE \"DATE TRANSFERRED OUT\" FIELD, IT IS REQUIRED", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            //Queries inserting a member into the permanent_transfer_out & archives tables
            insert_transfer_details_query = "INSERT INTO permanent_transfer_out (card_no, first_name, last_name, transferred_to, date_transferred_out)" +
                    "VALUES (?, ?, ?, ?, ?);";
            insert_archive_details_query = "INSERT INTO archives (card_no, first_name, last_name, house_no, gender, zone, baptism_date, baptised_by, baptism_location)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            //Queries deleting a member from personal_details & baptism tables
            delete_from_baptism_query = "DELETE FROM baptism WHERE card_no = ?;";
            delete_from_personalDetails_query = "DELETE FROM personal_details WHERE card_no = ?;";

            //Execute queries
            try {
                //insert member into permanent_transfers_out table
                insert_transfer_details = conn.prepareStatement(insert_transfer_details_query);
                insert_transfer_details.setInt(1, db_card_no);
                insert_transfer_details.setString(2, db_first_name);
                insert_transfer_details.setString(3, db_last_name);
                insert_transfer_details.setString(4, transferred_to);
                insert_transfer_details.setString(5, date_transferred_out);
                insert_transfer_details.execute();

                //insert member into archives table
                insert_archive_details = conn.prepareStatement(insert_archive_details_query);
                insert_archive_details.setInt(1, db_card_no);
                insert_archive_details.setString(2, db_first_name);
                insert_archive_details.setString(3, db_last_name);
                insert_archive_details.setString(4, db_house_no);
                insert_archive_details.setString(5, db_gender);
                insert_archive_details.setString(6, db_zone);
                insert_archive_details.setString(7, db_baptised_on);
                insert_archive_details.setString(8, db_baptised_by);
                insert_archive_details.setString(9, db_baptised_at);
                insert_archive_details.execute();

                //delete member from baptism table
                delete_from_baptism = conn.prepareStatement(delete_from_baptism_query);
                delete_from_baptism.setInt(1, db_card_no);
                delete_from_baptism.execute();

                //delete member from personal_details table
                delete_from_personal_details = conn.prepareStatement(delete_from_personalDetails_query);
                delete_from_personal_details.setInt(1, db_card_no);
                delete_from_personal_details.execute();

                /*alert box informing user that the member has been successfully inserted into permanent_transfers_out & archives tables and
                deleted from personal_details & baptism tables*/
                success = new Alert(Alert.AlertType.INFORMATION, db_first_name + " " + db_last_name + " HAS BEEN DELETED FROM THE CHRISTIANS TABLE AND \nINSERTED INTO THE ARCHIVES AND PERMANENT TRANSFERS OUT TABLES", ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO DELETE " + db_first_name + " " + db_last_name + " FROM THE CHRISTIANS TABLE AND \nINSERT THEM INTO THE ARCHIVES AND PERMANENT TRANSFERS OUT TABLES", ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }

        }

    }

    //Clear the entire form when "clear" button is pressed
    public void clear_form() {
        search_field.clear();
        transferred_to_field.clear();
        transfer_date_field.clear();
    }

    public void done() {
        //call christians class to display the table so the user can see their changes
        perm_trans_out_view callPerm_trans_out_view = new perm_trans_out_view();
        callPerm_trans_out_view.perm_trans_out_table();
    }
    //Go back to clerk home screen when BACK label is pressed
    public void back_function() {
        callTransfers = new transfers();
        callTransfers.transfer();
    }
}
