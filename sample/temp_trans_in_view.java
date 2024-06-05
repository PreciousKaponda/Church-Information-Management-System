package sample;

import javafx.fxml.*;
import javafx.scene.*;
import java.io.*;
/**
 * Created by Kaponda on 23 Mar 2021.
 */
public class temp_trans_in_view {
    Parent temp_trans_in_view_root;
    Scene temp_trans_in_view_scene;

    public void temp_trans_in_table() {
        try {
            temp_trans_in_view_root = FXMLLoader.load(getClass().getResource("temp_trans_in_view_gui.fxml"));
        } catch (IOException e) {

        }

        temp_trans_in_view_scene = new Scene(temp_trans_in_view_root, 1070, 500);
        temp_trans_in_view_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setScene(temp_trans_in_view_scene);
        Main.stage.setTitle("Kapita C.C.A.P. IMS");
        Main.stage.centerOnScreen();
        Main.stage.setOnCloseRequest(e -> {
            e.consume();
            exitProtocol();
        });
        Main.stage.show();
    }

    //confirm to exit the window
    private void exitProtocol() {
        //boolean value that gets the true or false value that is returned from the confirm exit window's two buttons ("OK" and "Cancel")
        Boolean affirmation = confirmBox.affirm("Confirm Exit", "Are You Sure You Want to Exit?", 250, 80);

        if (affirmation) {
            Main.stage.close();
        }
    }
}
