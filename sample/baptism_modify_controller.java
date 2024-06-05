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

public class baptism_modify_controller {
    @FXML
    private TextField search_field, baptised_on_field, baptised_by_field, baptised_at_field;
    @FXML
    private Label card_no_label, first_name_label, last_name_label, baptised_on_label, baptised_by_label, baptised_at_label;

    clerk_home callClerk;
    baptism_view callBaptTable;
    Connection conn;
    PreparedStatement search_stmt, bapt_on_stmt, bapt_by_stmt, bapt_at_stmt;
    ResultSet rs;
    Alert empty_field_alert, error, warning, success;
    String b, c, d, e, f, search_word, search_query, baptised_on, baptised_by, baptised_at, bapt_on_query, bapt_by_query, bapt_at_query;
    int search_card, a;


    //Retrieve member baptism data from database and display it in the labels so the user can see what info they're changing
    public void search() throws SQLException {
        conn = db.java_db();

        //initialising the preparedStatement and resultSet so they can be closed after executing their duties
        search_stmt = null;
        rs = null;

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

        //Search for baptism details of members and display them in the labels so the user can see what details they're changing
        try {
            search_query = "SELECT b.card_no, p.first_name, p.last_name, b.baptism_date, b.baptised_by, b.baptism_location\n" +
                    "FROM baptism b, personal_details p\n" +
                    "WHERE b.card_no = ?\n" +
                    "AND p.card_no = ?;";

            search_stmt = conn.prepareStatement(search_query);
            search_stmt.setInt(1, search_card);
            search_stmt.setInt(2, search_card);
            rs = search_stmt.executeQuery();

            if (rs.next()) {
                a = rs.getInt("card_no");
                b = rs.getString("first_name");
                c = rs.getString("last_name");
                d = rs.getString("baptism_date");
                e = rs.getString("baptised_by");
                f = rs.getString("baptism_location");

                //display the baptism details on the labels as a reference for the user
                card_no_label.setText("CARD NO.: " + a);
                first_name_label.setText("FIRST NAME: " + b);
                last_name_label.setText("LAST NAME: " + c);
                baptised_on_label.setText("BAPTISM DATE: " + d);
                baptised_by_label.setText("BAPTISED BY: " + e);
                baptised_at_label.setText("BAPTISM LOCATION: " + f);
            } else {
                error = new Alert(Alert.AlertType.WARNING, "NO MEMBER FOUND WITH THAT CARD NO.", ButtonType.OK);
                error.setHeaderText("CARD NO. NOT FOUND");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        } catch (SQLException e) {

        } finally {
            search_stmt.close();
            rs.close();
        }
    }
    public void mod_baptised_on () {
        //Retrieve the new baptism date from the text field
        baptised_on = baptised_on_field.getText();

        //Validate an empty textField
        if (baptised_on.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "THE BAPTISM DATE FIELD IS EMPTY", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            bapt_on_query = "UPDATE baptism SET baptism_date = ? WHERE card_no = ?";

            try {
                bapt_on_stmt = conn.prepareStatement(bapt_on_query);
                bapt_on_stmt.setString(1, baptised_on);
                bapt_on_stmt.setInt(2, search_card);
                bapt_on_stmt.execute();

                //alertBox saying modifying baptism date was successful
                success = new Alert(Alert.AlertType.INFORMATION, "BAPTISM DATE UPDATED SUCCESSFULLY TO: " + baptised_on, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE BAPTISM DATE", ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }
    public void mod_baptised_by() {
        //Retrieve the new baptised by from the text field
        baptised_by = baptised_by_field.getText();

        //Validate an empty textField
        if (baptised_by.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "THE BAPTISED BY FIELD IS EMPTY", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            bapt_by_query = "UPDATE baptism SET baptised_by = ? WHERE card_no = ?";

            try {
                bapt_by_stmt = conn.prepareStatement(bapt_by_query);
                bapt_by_stmt.setString(1, baptised_by);
                bapt_by_stmt.setInt(2, search_card);
                bapt_by_stmt.execute();

                //alertBox saying modifying baptised by was successful
                success = new Alert(Alert.AlertType.INFORMATION, "BAPTISED BY UPDATED SUCCESSFULLY TO: " + baptised_by, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE BAPTISED BY", ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }
    public void mod_baptised_at() {
        //Retrieve the new baptism location from the text field
        baptised_at = baptised_at_field.getText();

        //Validate an empty textField
        if (baptised_at.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "THE BAPTISM LOCATION FIELD IS EMPTY", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            bapt_at_query = "UPDATE baptism SET baptism_location = ? WHERE card_no = ?";

            try {
                bapt_at_stmt = conn.prepareStatement(bapt_at_query);
                bapt_at_stmt.setString(1, baptised_at);
                bapt_at_stmt.setInt(2, search_card);
                bapt_at_stmt.execute();

                //alertBox saying modifying baptism location was successful
                success = new Alert(Alert.AlertType.INFORMATION, "BAPTISM LOCATION UPDATED SUCCESSFULLY TO: " + baptised_at, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE BAPTISM LOCATION", ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }

    public void clear_form() {
        search_field.clear();
        baptised_on_field.clear();
        baptised_by_field.clear();
        baptised_at_field.clear();

        //Clear the retrieved baptism details from the labels
        card_no_label.setText("CARD NO.: ");
        first_name_label.setText("FIRST NAME: ");
        last_name_label.setText("LAST NAME: ");
        baptised_on_label.setText("BAPTISM DATE: ");
        baptised_by_label.setText("BAPTISED BY: ");
        baptised_at_label.setText("BAPTISM LOCATION: ");

        //Set search card = 0 so no details can be modified once the entire form including the labels is cleared
        search_card = 0;
    }
    //call baptism_view class to display the table so the user can see their changes
    public void done() {
        callBaptTable = new baptism_view();
        callBaptTable.baptised_christians();
    }
    //Take user back to the clerk panel
    public void back_function() {
        callClerk = new clerk_home();
        callClerk.clerk();
    }
}
