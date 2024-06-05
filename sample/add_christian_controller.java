package sample;

import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by Kaponda on 02 May 2020.
 */
public class add_christian_controller implements Initializable {
    @FXML
    public TextField card_no_field, first_name_field, last_name_field, house_no_field;
    public ChoiceBox<String> gender_field, zone_field;

    clerk_home callClerk;
    add_christian callAddChristian;
    Connection conn;
    Alert card_no_alert, success, failure;
    PreparedStatement insert_personal_details;
    int card_no;
    String card_no_string, sex, dela, first_name, last_name, house_no, gender, zone, add_personal_details;


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


    public void validate_member() {
        card_no_string = card_no_field.getText();
        first_name = first_name_field.getText();
        last_name = last_name_field.getText();
        house_no = house_no_field.getText();
        gender = get_gender(gender_field);
        zone = get_zone(zone_field);

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

            //insert personal details from form into the "personal details" table in database
            if (!first_name.isEmpty() || !last_name.isEmpty()) {
                //running query to insert personal details
                insert_personal_details = conn.prepareStatement(add_personal_details);
                insert_personal_details.setInt(1, card_no);
                insert_personal_details.setString(2, first_name);
                insert_personal_details.setString(3, last_name);
                insert_personal_details.setString(4, house_no);
                insert_personal_details.setString(5, gender);
                insert_personal_details.setString(6, zone);
                insert_personal_details.execute();

                //Alert box informing user that inserting a new member has been successful
                success = new Alert(Alert.AlertType.INFORMATION, first_name + " " + last_name + " HAS BEEN INSERTED INTO THE DATABASE", ButtonType.OK);
                success.setHeaderText("SUCCESS");
                success.setTitle("Kapita C.C.A.P. IMS - SUCCESS");
                success.showAndWait();
            } else {
                failure = new Alert(Alert.AlertType.WARNING, "CARD NO. & FIRST OR LAST NAME ARE REQUIRED FIELDS", ButtonType.OK);
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
        card_no_field.clear();
        first_name_field.clear();
        last_name_field.clear();
        house_no_field.clear();

        //clear the 2 choiceBoxes then replace their contents since "getItems().clear()" erases all the items set for them
        gender_field.getItems().clear();
        gender_field.getItems().add("Male");
        gender_field.getItems().add("Female");

        zone_field.getItems().clear();
        zone_field.getItems().addAll("ABC", "DEF", "QRS", "GHIJ", "KLM", "N&O", "P&T", "ADEFO", "ILN", "GHM", "BCJKP", "Area 15", "Area 30");
    }

    public void done() {
        //call christians class to display the table so the user can see their changes
        christians call_christians = new christians();
        call_christians.viewChristians();
    }

    //Make mouse disappear when typing so it doesn't block user's view
    public void typing_mouse_setting() {
        callAddChristian.addChristianScene.setCursor(Cursor.NONE);
    }

    //Make mouse disappear when typing so it doesn't block user's view
    public void typing_mouse_released() {
        callAddChristian.addChristianScene.setCursor(Cursor.DEFAULT);
    }

    //Go back to clerk home screen when BACK label is pressed
    public void back_function() {
        callClerk = new clerk_home();
        callClerk.clerk();
    }
}
