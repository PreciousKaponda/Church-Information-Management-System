package sample;

import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by Kaponda on 11 May 2021.
 */
public class add_bapt_child_controller implements Initializable {
    @FXML
    TextField cert_no_field, name_field, dob_field, parents_field, house_no_field, baptised_on_field, baptised_by_field, witness_field;
    @FXML
    ChoiceBox<String> gender_field, zone_field;

    String add_baptised_child_query, cert_no_string, name, gender, dob, parents, house_no, zone, baptised_on, baptised_by, witness, sex, dela;
    int cert_no;
    Alert cert_no_alert, success, failure;
    clerk_home callClerk;
    Connection conn;
    PreparedStatement insert_child;


    /**
     * Setting up the Gender & Zone choiceBoxes
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //giving them their values
        gender_field.getItems().addAll("Male", "Female");
        zone_field.getItems().addAll("ABC", "DEF", "QRS", "GHIJ", "KLM", "N&O", "P&T", "ADEFO", "ILN", "GHM", "BCJKP", "Area 15", "Area 30");
    }
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

    public void validate_baptised_child() {
        //Form validation before entering the data into the database
        cert_no_string = cert_no_field.getText();
        name = name_field.getText();
        dob = dob_field.getText();
        parents = parents_field.getText();
        house_no = house_no_field.getText();
        baptised_on = baptised_on_field.getText();
        baptised_by = baptised_by_field.getText();
        witness = witness_field.getText();
        gender = get_gender(gender_field);
        zone = get_zone(zone_field);

        //Validating cert no. if its empty || not an integer
        if (cert_no_string.isEmpty()) {
            cert_no_alert = new Alert(Alert.AlertType.ERROR, "CERTIFICATE NO. CANNOT BE EMPTY", ButtonType.OK);
            //card_no_alert.getIcons().add(new Image("img/ccap_logo.png"));
            cert_no_alert.setHeaderText("EMPTY CERTIFICATE NO.");
            cert_no_alert.setTitle("Kapita C.C.A.P. IMS - ERROR");
            cert_no_alert.showAndWait();
        } else {
            try {
                //Converting cert_no_string to int
                cert_no = Integer.parseInt(cert_no_string);
            } catch (NumberFormatException e) {
                cert_no_alert = new Alert(Alert.AlertType.ERROR, "CERTIFICATE NO. MUST STRICTLY BE A NUMBER", ButtonType.OK);
                cert_no_alert.setHeaderText("NUMBERS ONLY!");
                cert_no_alert.setTitle("Kapita C.C.A.P. IMS");
                cert_no_alert.showAndWait();
            }
        }

        //SQL Query to add form details into database
        try {
            conn = db.java_db();
            add_baptised_child_query = "INSERT INTO baptised_children (certificate_no, name, gender , date_of_birth,  parent, house_no, zone, baptism_date, baptised_by, witness)" +
                    "VALUES (?, ?, ?, ? , ?, ?, ?, ? , ?, ?)";

            /**
             * insert child baptism details from the form into the "baptised_children" table in the database if the fields aren't empty
             */
            if (!name.isEmpty() || !parents.isEmpty()) {
                //running query to insert personal details
                insert_child = conn.prepareStatement(add_baptised_child_query);
                insert_child.setInt(1, cert_no);
                insert_child.setString(2, name);
                insert_child.setString(3, gender);
                insert_child.setString(4, dob);
                insert_child.setString(5, parents);
                insert_child.setString(6, house_no);
                insert_child.setString(7, zone);
                insert_child.setString(8, baptised_on);
                insert_child.setString(9, baptised_by);
                insert_child.setString(10, witness);
                insert_child.execute();

                //Alert box informing user that inserting a new member has been successful
                success = new Alert(Alert.AlertType.INFORMATION, name + " HAS BEEN INSERTED INTO THE DATABASE", ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } else {
                failure = new Alert(Alert.AlertType.WARNING, "MAKE SURE TO FILL THE NAME AND PARENT FIELDS", ButtonType.OK);
                failure.setHeaderText("FORM ERROR");
                failure.setTitle("Kapita C.C.A.P. IMS - Form Error");
                failure.showAndWait();
            }
        } catch (Exception e){
            failure = new Alert(Alert.AlertType.WARNING, "CHECK THAT THE CARD NO. DOESN'T ALREADY EXIST OR THAT THE FORM IS FILLED IN THE CORRECT FORMATS", ButtonType.OK);
            failure.setHeaderText(null);
            failure.setTitle("Kapita C.C.A.P. IMS - ERROR");
            failure.showAndWait();
        }
    }

    //Clear the entire form when the "clear" button is pressed
    public void clear_form() {
        cert_no_field.clear();
        name_field.clear();
        parents_field.clear();
        house_no_field.clear();
        baptised_by_field.clear();
        witness_field.clear();
        dob_field.clear();
        baptised_on_field.clear();

        //clear the 2 choiceBoxes then replace their contents since "getItems().clear()" erases all the items set for them
        gender_field.getItems().clear();
        gender_field.getItems().add("Male");
        gender_field.getItems().add("Female");

        zone_field.getItems().clear();
        zone_field.getItems().addAll("ABC", "DEF", "QRS", "GHIJ", "KLM", "N&O", "P&T", "ADEFO", "ILN", "GHM", "BCJKP", "Area 15", "Area 30");
    }

    public void done() {
        //call bapt_child_view class to display the table so the user can see their changes
        bapt_child_view call_bapt_child_table = new bapt_child_view();
        call_bapt_child_table.baptised_children();
    }

    //Go back to clerk home screen when BACK label is pressed
    public void back_function() {
        callClerk = new clerk_home();
        callClerk.clerk();
    }
}
