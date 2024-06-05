package sample;

import javafx.fxml.*;
import javafx.scene.*;
import java.io.*;

/**
 * Created by Kaponda on 12 May 2021.
 */
public class bapt_child_modify {
    Parent bapt_child_modify_root;
    Scene bapt_child_modify_scene;

    public void child_baptism_modification() {
        try {
            bapt_child_modify_root = FXMLLoader.load(getClass().getResource("bapt_child_modify_gui.fxml"));
        } catch (IOException e) {

        }

        bapt_child_modify_scene = new Scene(bapt_child_modify_root, 850, 635);
        bapt_child_modify_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setScene(bapt_child_modify_scene);
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
