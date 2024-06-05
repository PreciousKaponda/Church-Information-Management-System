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
 * Created by Kaponda on 06 Aug 2021.
 */

public class baptism_add_controller {
    @FXML
    private TextField search_field, baptised_on_field, baptised_by_field, baptised_at_field;
    @FXML
    private Label card_no_label, first_name_label, last_name_label, house_no_label, gender_label, zone_label;
    @FXML
    private Button clear_btn, submit_btn, done_btn;

    clerk_home callClerk;
    baptism_view callBaptismTable;
    Connection conn;
    PreparedStatement search_stmt, insert_stmt;
    ResultSet resultSet;
    Alert empty_field_alert, error, success;
    String search_word, search_query, b, c, d, e, f, baptised_on, baptised_by, baptised_at, insert_query;
    int search_card, a;


    public void search() {
        conn = db.java_db();

        //initialising the preparedStatement and resultSet so they can be closed after executing their duties
        search_stmt = null;
        resultSet = null;

        search_word = search_field.getText();

        //Validating card no. if its empty || not an integer
        if (search_word.isEmpty()) {
            empty_field_alert = new Alert(Alert.AlertType.ERROR, "CARD NO. CANNOT BE EMPTY", ButtonType.OK);
            empty_field_alert.setHeaderText("EMPTY CARD NO.!");
            empty_field_alert.setTitle("Kapita C.C.A.P. IMS - ERROR");
            empty_field_alert.showAndWait();
        } else {
            //Converting the card no. to int
            try {
                search_card = Integer.parseInt(search_word);
            } catch (NumberFormatException e) {
                empty_field_alert = new Alert(Alert.AlertType.ERROR, "CARD NO. MUST STRICTLY BE A NUMBER", ButtonType.OK);
                empty_field_alert.setHeaderText("NUMBERS ONLY!");
                empty_field_alert.setTitle("Kapita C.C.A.P. IMS - ERROR");
                empty_field_alert.showAndWait();
            }
        }

        //search for member details using the card_no so the user can see which member he's adding the baptism details for
        try {
            search_query = "SELECT card_no, first_name, last_name, house_no, gender, zone\n" +
                    "FROM  personal_details\n" +
                    "WHERE card_no = ?;";

            search_stmt = conn.prepareStatement(search_query);
            search_stmt.setInt(1, search_card);
            resultSet = search_stmt.executeQuery();

            if (resultSet.next()) {
                a = resultSet.getInt(1);
                b = resultSet.getString(2);
                c = resultSet.getString(3);
                d = resultSet.getString(4);
                e = resultSet.getString(5);
                f = resultSet.getString(6);

                //Display member data on the labels as a reference
                card_no_label.setText("CARD NO.: " + a);
                first_name_label.setText("FIRST NAME: " + b);
                last_name_label.setText("LAST NAME: " + c);
                house_no_label.setText("HOUSE NO.: " + d);
                gender_label.setText("GENDER: " + e);
                zone_label.setText("ZONE: " + f);
            } else {
                error = new Alert(Alert.AlertType.WARNING, "NO MEMBER FOUND WITH THAT CARD NO.", ButtonType.OK);
                error.setHeaderText("CARD NO. NOT FOUND");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        } catch (SQLException e) {

        }  finally {
            try {
                search_stmt.close();
                resultSet.close();
            } catch (SQLException ex) {

            }
        }
    }


    //Actual process of adding a member's baptism details to the database
    public void add_baptised_member() {
        //Extract details from the forms
        baptised_on = baptised_on_field.getText();
        baptised_by = baptised_by_field.getText();
        baptised_at = baptised_at_field.getText();

        //Running the query to add the form details into the database
        try {
            conn = db.java_db();
            insert_query = "INSERT INTO baptism (baptism_date, baptised_by, baptism_location, card_no)" +
                    "VALUES (?, ?, ?, ?);";

            //If all the form fields are empty, don't run query
            if (!baptised_on.isEmpty() || !baptised_by.isEmpty() || !baptised_at.isEmpty()) {
                insert_stmt = conn.prepareStatement(insert_query);
                insert_stmt.setString(1, baptised_on);
                insert_stmt.setString(2, baptised_by);
                insert_stmt.setString(3, baptised_at);
                insert_stmt.setInt(4, search_card);
                insert_stmt.execute();

                //Alert box informing user that inserting a new member has been successful
                success = new Alert(Alert.AlertType.INFORMATION, b + " " + c + "'s BAPTISM DETAILS HAVE BEEN INSERTED INTO THE DATABASE", ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } else {
                error = new Alert(Alert.AlertType.ERROR, "FILL AT LEAST ONE OF THE BAPTISM DETAILS", ButtonType.OK);
                error.setHeaderText("FAILED INSERTION");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        } catch (SQLException e) {
            error = new Alert(Alert.AlertType.ERROR, "CHECK THAT THE FORM IS FILLED IN THE CORRECT FORMATS" + e, ButtonType.OK);
            error.setHeaderText(null);
            error.setTitle("Kapita C.C.A.P. IMS - ERROR");
            error.showAndWait();
        }
    }


    public void clear_form() {
        //Clear all text fields
        search_field.clear();
        baptised_on_field.clear();
        baptised_by_field.clear();
        baptised_at_field.clear();

        //Reset all the labels
        card_no_label.setText("CARD NO.: ");
        first_name_label.setText("FIRST NAME: ");
        last_name_label.setText("LAST NAME: ");
        house_no_label.setText("HOUSE NO.: ");
        gender_label.setText("GENDER: ");
        zone_label.setText("ZONE: ");

        //Clear the search input so baptism details can't be changed when the form's cleared
        search_card = 0;
    }

    //Take user to the baptisms table
    public void done() {
        callBaptismTable = new baptism_view();
        callBaptismTable.baptised_christians();
    }

    //Take user back to the clerk panel
    public void back_function() {
        callClerk = new clerk_home();
        callClerk.clerk();
    }
}
