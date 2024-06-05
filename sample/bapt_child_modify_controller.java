package sample;

import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by Kaponda on 12 May 2021.
 */
public class bapt_child_modify_controller implements  Initializable {
    @FXML
    Label cert_no_label, name_label, gender_label, dob_label, parents_label, house_no_label, zone_label, baptised_on_label, baptised_by_label, witness_label;
    @FXML
    TextField search_field, name_field, dob_field, parents_field, house_no_field, baptised_on_field, baptised_by_field, witness_field;
    @FXML
    ChoiceBox<String> gender_field, zone_field;

    String name, gender, dob, parents, house_no, zone, baptised_on, baptised_by, witness, sex, dela;
    String search_query, search_word, b, c, d, e, f, g, h, i, j;
    String update_name_query, update_gender_query, update_dob_query, update_parents_query, update_house_no_query, update_zone_query, update_baptised_on_query, update_baptised_by_query, update_witness_query;
    int cert_no, a;
    Alert empty_field_alert, error, warning, success;

    bapt_child_view call_bapt_child_table;
    clerk_home callClerk;
    Connection conn;
    PreparedStatement prep_search_stmt, update_name, update_gender, update_dob, update_parents, update_house_no, update_zone, update_baptised_on, update_baptised_by, update_witness;
    ResultSet resultSet;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //giving them their values
        gender_field.getItems().addAll("Male", "Female");
        zone_field.getItems().addAll("ABC", "DEF", "QRS", "GHIJ", "KLM", "N&O", "P&T", "ADEFO", "ILN", "GHM", "BCJKP", "Area 15", "Area 30");
    }

    //Retrieve data from database and display it in the labels so the user can see what info they're changing
    public void search() {
        conn = db.java_db();

        //initialising the preparedStatement and resultSet so they can be closed after executing their duties
        prep_search_stmt = null;
        resultSet = null;

        search_word = search_field.getText();

        //Validating cert no. if its empty || not genderr
        if (search_word.isEmpty()) {
            empty_field_alert = new Alert(Alert.AlertType.ERROR, "CERTIFICATE NO. CANNOT BE EMPTY", ButtonType.OK);
            empty_field_alert.setHeaderText("EMPTY CERTIFICATE NO.!");
            empty_field_alert.setTitle("Kapita C.C.A.P. IMS - ERROR");
            empty_field_alert.showAndWait();
        } else {
            //Converting the cert no. to int
            try {
                cert_no = Integer.parseInt(search_word);
            } catch (NumberFormatException e) {
                empty_field_alert = new Alert(Alert.AlertType.ERROR, "CERTIFICATE NO. MUST STRICTLY BE A NUMBER", ButtonType.OK);
                empty_field_alert.setHeaderText("NUMBERS ONLY!");
                empty_field_alert.setTitle("Kapita C.C.A.P. IMS - ERROR");
                empty_field_alert.showAndWait();
            }
        }

        //search for children's details using the cert_no given & display them in the labels so the user can see what details they're changing
        try {
            search_query = "SELECT certificate_no, name, gender, date_of_birth, parent, house_no, zone, baptism_date, baptised_by, witness\n" +
                    "FROM  baptised_children\n" +
                    "WHERE certificate_no = ?;";

            prep_search_stmt = conn.prepareStatement(search_query);
            prep_search_stmt.setInt(1, cert_no);
            resultSet = prep_search_stmt.executeQuery();

            if(resultSet.next()){
                //resultSet = prep_search_stmt.getResultSet()
                a = resultSet.getInt("certificate_no");
                b = resultSet.getString("name");
                c = resultSet.getString("gender");
                d = resultSet.getString("date_of_birth");
                e = resultSet.getString("parent");
                f = resultSet.getString("house_no");
                g = resultSet.getString("zone");
                h = resultSet.getString("baptism_date");
                i = resultSet.getString("baptised_by");
                j = resultSet.getString("witness");

                //Display member data on the labels as a reference
                cert_no_label.setText("CERTIFICATE NO: " + a);
                name_label.setText("NAME: " + b);
                gender_label.setText("GENDER: " + c);
                dob_label.setText("DATE OF BIRTH: " + d);
                parents_label.setText("PARENT: " + e);
                house_no_label.setText("HOUSE NO.: " + f);
                zone_label.setText("ZONE: " + g);
                baptised_on_label.setText("BAPTISM DATE: " + h);
                baptised_by_label.setText("BAPTISED BY: " + i);
                witness_label.setText("WITNESS: " + j);
            } else {
                error = new Alert(Alert.AlertType.WARNING, "NO CHILD FOUND WITH THAT CERTIFICATE NO.", ButtonType.OK);
                error.setHeaderText("CERT NO. NOT FOUND");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        } catch (SQLException e) {

        } finally {
            try {
                prep_search_stmt.close();
                resultSet.close();
            } catch (SQLException ex) {

            }
        }
    }


    /**
     * Update child baptism details
     */
    public void mod_name() {
        conn = db.java_db();
        //retrieve the new name from the textField
        name = name_field.getText();

        //Validate an empty textField
        if (name.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "THE NAME FIELD IS EMPTY", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();

        } else {
            update_name_query = "UPDATE baptised_children SET name = ? WHERE certificate_no = ?";

            try {
                update_name = conn.prepareStatement(update_name_query);
                update_name.setString(1, name);
                update_name.setInt(2, cert_no);
                update_name.execute();

                success = new Alert(Alert.AlertType.INFORMATION, "NAME UPDATED SUCCESSFULLY TO: " + name, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE NAME", ButtonType.OK);
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
            warning = new Alert(Alert.AlertType.WARNING, "THE GENDER FIELD IS EMPTY", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            update_gender_query = "UPDATE baptised_children SET gender = ? WHERE certificate_no = ?";

            try {
                update_gender = conn.prepareStatement(update_gender_query);
                update_gender.setString(1, gender);
                update_gender.setInt(2, cert_no);
                update_gender.execute();

                success = new Alert(Alert.AlertType.INFORMATION, "GENDER UPDATED SUCCESSFULLY TO: " + gender, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE GENDER", ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }
    public void mod_dob() {
        //retrieve the new date of birth from the textField
        dob = dob_field.getText();

        //Validate an empty textField
        if (dob.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "THE DATE OF BIRTH FIELD IS EMPTY", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            update_dob_query = "UPDATE baptised_children SET date_of_birth = ? WHERE certificate_no = ?";

            try {
                update_dob = conn.prepareStatement(update_dob_query);
                update_dob.setString(1, dob);
                update_dob.setInt(2, cert_no);
                update_dob.execute();

                success = new Alert(Alert.AlertType.INFORMATION, "DATE OF BIRTH UPDATED SUCCESSFULLY TO: " + dob, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE DATE OF BIRTH. \nMAKE SURE TO USE THE FORMAT YYYY/MM/DD", ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }
    public void mod_parents() {
        //retrieve the new parent from the textField
        parents = parents_field.getText();

        //Validate an empty textField
        if (parents.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "THE PARENT FIELD IS EMPTY", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            update_parents_query = "UPDATE baptised_children SET parent = ? WHERE certificate_no = ?";

            try {
                update_parents = conn.prepareStatement(update_parents_query);
                update_parents.setString(1, parents);
                update_parents.setInt(2, cert_no);
                update_parents.execute();

                success = new Alert(Alert.AlertType.CONFIRMATION, "PARENT UPDATED SUCCESSFULLY To: " + parents, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE PARENT", ButtonType.OK);
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
            warning = new Alert(Alert.AlertType.WARNING, "THE HOUSE NO. FIELD IS EMPTY", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            update_house_no_query = "UPDATE baptised_children SET house_no = ? WHERE certificate_no = ?";

            try {
                update_house_no = conn.prepareStatement(update_house_no_query);
                update_house_no.setString(1, house_no);
                update_house_no.setInt(2, cert_no);
                update_house_no.execute();

                success = new Alert(Alert.AlertType.CONFIRMATION, "HOUSE NO. UPDATED SUCCESSFULLY TO: " + house_no, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE HOUSE NO.", ButtonType.OK);
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
            warning = new Alert(Alert.AlertType.WARNING, "THE ZONE FIELD IS EMPTY", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            update_zone_query = "UPDATE baptised_children SET zone = ? WHERE certificate_no = ?";

            try {
                update_zone = conn.prepareStatement(update_zone_query);
                update_zone.setString(1, zone);
                update_zone.setInt(2, cert_no);
                update_zone.execute();

                success = new Alert(Alert.AlertType.CONFIRMATION, "ZONE UPDATED SUCCESSFULLY TO: " + zone, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE ZONE", ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }
    public void mod_baptised_on() {
        //retrieve the new first_name from the textField
        baptised_on = baptised_on_field.getText();

        //Validate an empty textField
        if (baptised_on.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "THE BAPTISM DATE FIELD IS EMPTY", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            update_baptised_on_query = "UPDATE baptised_children SET baptism_date = ? WHERE certificate_no = ?";

            try {
                update_baptised_on = conn.prepareStatement(update_baptised_on_query);
                update_baptised_on.setString(1, baptised_on);
                update_baptised_on.setInt(2, cert_no);
                update_baptised_on.execute();

                success = new Alert(Alert.AlertType.CONFIRMATION, "BAPTISM DATE UPDATED SUCCESSFULLY TO: " + baptised_on, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE BAPTISM DATE. \nMAKE SURE TO USE THE FORMAT YYYY/MM/DD", ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }
    public void mod_baptised_by() {
        //retrieve the new first_name from the textField
        baptised_by = baptised_by_field.getText();

        //Validate an empty textField
        if (baptised_by.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "THE BAPTISED BY FIELD IS EMPTY", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            update_baptised_by_query = "UPDATE baptised_children SET baptised_by = ? WHERE certificate_no = ?";

            try {
                update_baptised_by = conn.prepareStatement(update_baptised_by_query);
                update_baptised_by.setString(1, baptised_by);
                update_baptised_by.setInt(2, cert_no);
                update_baptised_by.execute();

                success = new Alert(Alert.AlertType.CONFIRMATION, "BAPTISED BY UPDATED SUCCESSFULLY TO: " + baptised_by, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE BAPTISED BY" , ButtonType.OK);
                error.setHeaderText("FAILED UPDATE");
                error.setTitle("Kapita C.C.A.P. IMS - ERROR");
                error.showAndWait();
            }
        }
    }
    public void mod_witness() {
        //retrieve the new first_name from the textField
        witness = witness_field.getText();

        //Validate an empty textField
        if (witness.isEmpty()) {
            warning = new Alert(Alert.AlertType.WARNING, "THE WITNESS FIELD IS EMPTY", ButtonType.OK);
            warning.setHeaderText("EMPTY FIELD");
            warning.setTitle("Kapita C.C.A.P. IMS");
            warning.showAndWait();
        } else {
            update_witness_query = "UPDATE baptised_children SET witness = ? WHERE certificate_no = ?";

            try {
                update_witness = conn.prepareStatement(update_witness_query);
                update_witness.setString(1, witness);
                update_witness.setInt(2, cert_no);
                update_witness.execute();

                success = new Alert(Alert.AlertType.CONFIRMATION, "WITNESS UPDATED SUCCESSFULLY TO: " + witness, ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } catch (SQLException e) {
                error = new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE WITNESS", ButtonType.OK);
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
        name_field.clear();
        dob_field.clear();
        parents_field.clear();
        house_no_field.clear();
        baptised_on_field.clear();
        baptised_by_field.clear();
        witness_field.clear();

        //clear the 2 choiceBoxes then replace their contents since "getItems().clear()" erases all the items set for them
        gender_field.getItems().clear();
        gender_field.getItems().add("Male");
        gender_field.getItems().add("Female");

        zone_field.getItems().clear();
        zone_field.getItems().addAll("ABC", "DEF", "QRS", "GHIJ", "KLM", "N&O", "P&T", "ADEFO", "ILN", "GHM", "BCJKP", "Area 15", "Area 30");

        //Clear the retrieved baptised child's info
        cert_no_label.setText("CERTIFICATE NO: ");
        name_label.setText("NAME: ");
        gender_label.setText("GENDER: ");
        dob_label.setText("DATE OF BIRTH: ");
        parents_label.setText("PARENT: ");
        house_no_label.setText("HOUSE NO.: ");
        zone_label.setText("ZONE: ");
        baptised_on_label.setText("BAPTISM DATE: ");
        baptised_by_label.setText("BAPTISED BY: ");
        witness_label.setText("WITNESS: ");

        //Set cert_no = 0 so no details can be modified once the entire form including the labels is cleared
        cert_no = 0;
    }

    //call bapt_child_view class to display the table so the user can see their changes
    public void done() {
        call_bapt_child_table = new bapt_child_view();
        call_bapt_child_table.baptised_children();
    }

    public void back_function() {
        callClerk = new clerk_home();
        callClerk.clerk();
    }
}
