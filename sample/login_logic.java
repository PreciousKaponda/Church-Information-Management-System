package sample;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import java.sql.*;

/**
 * Created by Kaponda on 28-Mar-18.
 */
public class login_logic {
    static String lw_username, lw_password;
    static login_window lw;
    static clerk_home c = new clerk_home();
    static Alert error;

    /**
     * Method handling logins for all the different users
     */
    public static void logger() {
        lw = new login_window();

        lw_username = lw.username_str;
        lw_password = lw.password_str;

        try {
            if (lw.logging_in(lw_username, lw_password)) {
                c.clerk();
            } else {
                displayError();
            }
        } catch (SQLException e) {
            error = new Alert(Alert.AlertType.WARNING, "Database not responding correctly", ButtonType.OK);
            error.setHeaderText("DATABASE CONNECTION ERROR");
            error.setTitle("Kapita C.C.A.P. IMS - Database error");
            error.showAndWait();
        }
    }

    /**
     * method that will display an error message in the login window
     */
    public static void displayError() {
        lw = new login_window();
        //Displaying error text on the label from the login_window class so that the error message can be displayed there
        lw.errorLabel.setText("Username or Password is incorrect");

        //instantiating the flowPane layout from the login_window class
        lw.errorFlowPane = new FlowPane();
        lw.errorFlowPane.setAlignment(Pos.CENTER);

        //adding the label containing the error message to the flowPane layout on the login window
        lw.errorFlowPane.getChildren().add(lw.errorLabel);

        //adding the flowPane layout with the error message to the bottom of the screen using the borderPane layout
        lw.borderPaneLogin.setBottom(lw.errorFlowPane);
    }

}
