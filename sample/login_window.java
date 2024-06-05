package sample;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import java.sql.*;

/**
 * Created by Kaponda on 23-Mar-18.
 */
public class login_window {
    static Label usernameLabel, passwordLabel, loginLabel, errorLabel, loginText;
    static TextField usernameField;
    static PasswordField passwordField;
    static Button loginBtn;
    static Scene sceneLogin;
    static HBox loginHbox;
    static BorderPane borderPaneLogin;
    static GridPane gPaneLogin;
    static FlowPane errorFlowPane;
    static ImageView logo;
    static Image img;
    static int count = 0;
    static KeyCombination key;
    static Alert login_error;
    public static String username_str, password_str;
	static String query;
    static PreparedStatement stmt;
    static ResultSet rs;
    static Connection conn;

    public static void login_visual(){
        /**
         * Instantiating the other components that will fill the login window
         */
        //Logo
        img = new Image("img/ccap_logo.png");


        logo = new ImageView();
        logo.setImage(img);
        logo.setFitWidth(100);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);
        logo.setCache(true);

        loginText = new Label("LOGIN");
        loginText.setStyle(
                "-fx-font-weight: bold;"+
                        "-fx-font-size: 18pt;"
        );


        loginLabel = new Label("KAPITA C.C.A.P. INFORMATION MANAGEMENT SYSTEM");
        loginLabel.setStyle(
                "-fx-font-size: 14pt;"
        );

        usernameLabel = new Label("USERNAME: ");
        passwordLabel = new Label("PASSWORD: ");
        usernameLabel.setStyle(
                "-fx-font-weight: bold;"+
                        "-fx-font-size: 11pt;"
        );
        passwordLabel.setStyle(
                "-fx-font-weight: bold;"+
                        "-fx-font-size: 11pt;"
        );

        usernameField = new TextField();
        usernameField.setPromptText("USERNAME");
        usernameField.setStyle(
                "-fx-font-size: 10pt;"
        );

        passwordField = new PasswordField();
        passwordField.setPromptText("PASSWORD");
        passwordField.setStyle(
                "-fx-font-size: 10pt;"
        );

        loginBtn = new Button("Login");
        loginBtn.setCursor(Cursor.HAND);
        loginBtn.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: #000000; -fx-font-size: 12pt");

        loginBtn.setOnAction(e -> {
            //Retrieve username & password from text fields
            username_str = usernameField.getText();
            password_str = passwordField.getText();

            //security feature that if the wrong credentials have been entered 5 times, the program closes
            count++;

            if (count <= 5) {
                login_logic.logger();
            }
            else {
                login_error = new Alert(Alert.AlertType.INFORMATION, "Too many failed login attempts. Try again later", ButtonType.OK);
                login_error.showAndWait();
                Main.stage.close();
            }

        });

        errorLabel = new Label();
        errorLabel.setStyle(
                "-fx-text-fill: #FF0000;"+
                        "-fx-font-weight: bold;"+
                        "-fx-font-size: 13pt;"
        );



        /**
         * Setting up the HBox layout housing the label
         */
        loginHbox = new HBox();

        loginHbox.setStyle(
                "-fx-font-family: times new roman; -fx-background-color: #b8b894;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 10pt;" +
                        "-fx-text-fill: #000000;"
        );

        loginHbox.setAlignment(Pos.CENTER);
        loginHbox.setPadding(new Insets(5,5,5,5));

        loginHbox.getChildren().add(loginLabel);


        /**
         * Setting up the GridPane layout to be used for the inner components of the login window
         */
        //setting up the GridPane layout
        gPaneLogin = new GridPane();
        gPaneLogin.setPadding(new Insets(0, 15, 15, 15));
        gPaneLogin.setHgap(25);
        gPaneLogin.setVgap(15);
        gPaneLogin.setAlignment(Pos.CENTER);

        //setting constraints i.e. placing components in grids in the GridPane layout
        GridPane.setConstraints(logo, 1, 0);
        GridPane.setConstraints(loginText, 1, 1);

        GridPane.setConstraints(usernameLabel, 0, 2);
        GridPane.setConstraints(passwordLabel, 0, 3);

        GridPane.setConstraints(usernameField, 1, 2);
        GridPane.setConstraints(passwordField, 1, 3);

        GridPane.setConstraints(loginBtn, 1, 4);

        //placing all the components in their place in the GridPane layout
        gPaneLogin.getChildren().addAll(logo, loginText, usernameLabel, passwordLabel, usernameField, passwordField, loginBtn);


        /**
         * Setting up the BorderPane layout to hold everything on the page
         */
        borderPaneLogin = new BorderPane();

        borderPaneLogin.setTop(loginHbox);
        borderPaneLogin.setCenter(gPaneLogin);

        /**
         * Setting up the stage and scene
         */
        sceneLogin = new Scene(borderPaneLogin, 520, 380);

        //Key listener to process login info when the ENTER key is pressed
        key = new KeyCodeCombination(KeyCode.ENTER, KeyCodeCombination.CONTROL_ANY);
        sceneLogin.setOnKeyPressed(e -> {
            //Retrieve username & password from text fields
            username_str = usernameField.getText();
            password_str = passwordField.getText();

            if(key.match(e)) {
                count++;

                if (count <= 5) {
                    login_logic.logger();
                } else {
                    login_error = new Alert(Alert.AlertType.INFORMATION, "Too many failed login attempts. Try again later", ButtonType.OK);
                    login_error.showAndWait();
                    Main.stage.close();
                }
            }
        });

        Main.stage.setScene(sceneLogin);
        Main.stage.setTitle("Kapita C.C.A.P. IMS");
        Main.stage.centerOnScreen();
        //mainComponents.stage.getIcons().add(new Image("img/ccap_logo.png"));
        Main.stage.setOnCloseRequest(e -> {
            e.consume();
            exitProtocol();
        });
        Main.stage.show();
    }

    public boolean logging_in(String username, String password) throws SQLException {
        conn = db.java_db();
        query = "SELECT * FROM login WHERE username = ? AND password = ?";

        stmt = null;
        rs = null;

        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            return false;
        } finally {
            stmt.close();
            rs.close();
        }
    }

    private static void exitProtocol(){
        //boolean value that gets the true or false value that is returned from the confirm exit window's two buttons ("OK" and "Cancel")
        Boolean affirmation = confirmBox.affirm("Confirm Exit", "Are You Sure You Want to Exit?", 250, 80);

        if (affirmation){
            Main.stage.close();
        }
    }

}
