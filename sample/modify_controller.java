package sample;

import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by Kaponda on 25 Feb 2021.
 */
public class modify_controller implements Initializable {
    @FXML
    private TextField first_name_field, last_name_field, house_no_field, search_field;
    @FXML
    private ChoiceBox<String> gender_field, zone_field;
    @FXML
    private Label card_no_label, first_name_label, last_name_label, house_no_label, gender_label, zone_label;

    String first_name, last_name, house_no, gender, zone, b, c, d, e, f, sex, dela;
    String update_first_name_query, update_last_name_query, update_house_no_query, update_gender_query, update_zone_query;
    String search_word, search_query;
    int card, search_card, a;
    Alert empty_field_alert, error, success, warning;
    Connection conn;
    PreparedStatement prep_search_stmt, update_first_name, update_last_name, update_house_no, update_gender, update_zone;
    ResultSet resultSet;
    clerk_home callClerk;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //giving them their values
        gender_field.getItems().addAll("Male", "Female");
        zone_field.getItems().addAll("ABC", "DEF", "QRS", "GHIJ", "KLM", "N&O", "P&T", "ADEFO", "ILN", "GHM", "BCJKP", "Area 15", "Area 30");
    }

    //Retrieve member personal data from database and display it in the labels so the user can see what info they're changing
    public void search() throws SQLException {
        conn = db.java_db();

        //initialising the preparedStatement and resultSet so they can be closed after executing their duties
        prep_search_stmt = null;
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

        //search for member details using the card_no given & display them in the labels so the user can see what details they're changing
        try {
            search_query = "SELECT card_no, first_name, last_name, house_no, gender, zone\n" +
                    "FROM  personal_details\n" +
                    "WHERE card_no = ?;";

            prep_search_stmt = conn.prepareStatement(search_query);
            prep_search_stmt.setInt(1, search_card);
            resultSet = prep_search_stmt.executeQuery();
            if(resultSet.next()){
                //resultSet = prep_search_stmt.getResultSet()
                a = resultSet.getInt("card_no");
                b = resultSet.getString("first_name");
                c = resultSet.getString("last_name");
                d = resultSet.getString("house_no");
                e = resultSet.getString("gender");
                f = resultSet.getString("zone");

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

        } finally {
            prep_search_stmt.close();
            resultSet.close();
        }
    }

    /**
     * Update member details
     */
    public void mod_first_name() {
        conn = db.java_db();
        //retrieve the new first_name from the textField
        first_name = first_name_field.getText();

        //Validate an empty textField
        if (first_name.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "The First Name field is empty", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            update_first_name_query = "UPDATE personal_details SET first_name = ? WHERE card_no = ?";

            try {
                update_first_name = conn.prepareStatement(update_first_name_query);
                update_first_name.setString(1, first_name);
                update_first_name.setInt(2, search_card);
                update_first_name.execute();

                //alertBox saying modifying first name was successful
                success = new Alert(Alert.AlertType.INFORMATION, "FIRST NAME UPDATED SUCCESSFULLY TO: " + first_name, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "Failed to update First Name", ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }
    public void mod_last_name() {
        //retrieve the new last_name from the textField
        last_name = last_name_field.getText();

        //Validate an empty textField
        if (last_name.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "The Last Name field is empty", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            update_last_name_query = "UPDATE personal_details SET last_name = ? WHERE card_no = ?";

            try {
                update_last_name = conn.prepareStatement(update_last_name_query);
                update_last_name.setString(1, last_name);
                update_last_name.setInt(2, search_card);
                update_last_name.execute();

                //alertBox saying modifying last name was successful
                success = new Alert(Alert.AlertType.INFORMATION, "LAST NAME UPDATED SUCCESSFULLY TO: " + last_name, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "Failed to update Last Name", ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }
    public void mod_house_no() {
        //retrieve the new house_no from the textField
        house_no = house_no_field.getText();

        //Validate an empty textField
        if (house_no.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "The House No. field is empty", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            update_house_no_query = "UPDATE personal_details SET house_no = ? WHERE card_no = ?";

            try {
                update_house_no = conn.prepareStatement(update_house_no_query);
                update_house_no.setString(1, house_no);
                update_house_no.setInt(2, search_card);
                update_house_no.execute();

                //alertBox saying modifying house no. was successful
                success = new Alert(Alert.AlertType.INFORMATION, "HOUSE NUMBER UPDATED SUCCESSFULLY TO: " + house_no, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "Failed to update House Number", ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }
    public void mod_gender() {
        //retrieve the new gender from the textField
        gender = get_gender(gender_field);

        //Validate an empty textField
        if (gender.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "The Gender field is empty", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            update_gender_query = "UPDATE personal_details SET gender = ? WHERE card_no = ?";

            try {
                update_gender = conn.prepareStatement(update_gender_query);
                update_gender.setString(1, gender);
                update_gender.setInt(2, search_card);
                update_gender.execute();

                //alertBox saying modifying gender was successful
                success = new Alert(Alert.AlertType.INFORMATION, "GENDER UPDATED SUCCESSFULLY TO: " + gender, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE GENDER\n MAKE SURE IT'S NO MORE THAN 6 LETTERS", ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }
    public void mod_zone() {
        //retrieve the new zone from the textField
        zone = get_zone(zone_field);

        //Validate an empty textField
        if (zone.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "The Zone field is empty", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            update_zone_query = "UPDATE personal_details SET zone = ? WHERE card_no = ?";

            try {
                update_zone = conn.prepareStatement(update_zone_query);
                update_zone.setString(1, zone);
                update_zone.setInt(2, search_card);
                update_zone.execute();

                //alertBox saying modifying zone was successful
                success = new Alert(Alert.AlertType.INFORMATION, "ZONE UPDATED SUCCESSFULLY TO: " + zone, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "Failed to update Zone", ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }

    /**
     * Methods extracting gender and zone from their choiceBoxes
     */
    //Extract the value from the gender choiceBox
    private String get_gender(ChoiceBox<String> genderChoiceBox) {
        sex = genderChoiceBox.getValue();
        return sex;
    }
    //Extract the value from the zone choiceBox
    private String get_zone(ChoiceBox<String> zoneChoiceBox) {
        dela = zoneChoiceBox.getValue();
        return dela;
    }


    //Clear the entire form when the "clear" button is pressed
    public void clear_form() {
        search_field.clear();
        first_name_field.clear();
        last_name_field.clear();
        house_no_field.clear();

        //clear the 2 choiceBoxes then replace their contents since "getItems().clear()" erases all the items set for them
        gender_field.getItems().clear();
        gender_field.getItems().add("Male");
        gender_field.getItems().add("Female");

        zone_field.getItems().clear();
        zone_field.getItems().addAll("ABC", "DEF", "QRS", "GHIJ", "KLM", "N&O", "P&T", "ADEFO", "ILN", "GHM", "BCJKP", "Area 15", "Area 30");

        //Clear the retrieved member's info
        card_no_label.setText("CARD NO.: ");
        first_name_label.setText("FIRST NAME: ");
        last_name_label.setText("LAST NAME: ");
        house_no_label.setText("HOUSE NO.: ");
        gender_label.setText("GENDER: ");
        zone_label.setText("ZONE: ");

        //Set search card = 0 so no details can be modified once the entire form including the labels is cleared
        search_card = 0;
    }

    //call christians class to display the table so the user can see their changes
    public void done() {
        christians call_christians = new christians();
        call_christians.viewChristians();
    }

    public void back_function() {
        callClerk = new clerk_home();
        callClerk.clerk();
    }

}