package sample;

import javafx.fxml.*;
import javafx.scene.*;
import java.io.*;

/**
 * Created by Kaponda on 06 Aug 2021.
 */

public class baptism_modify {
    Parent baptism_mod_root;
    Scene baptism_mod_scene;

    public void baptism_modifications() {
        try {
            baptism_mod_root = FXMLLoader.load(getClass().getResource("baptism_modify_gui.fxml"));
        } catch (IOException e) {

        }

        baptism_mod_scene = new Scene(baptism_mod_root, 850, 480);
        baptism_mod_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setScene(baptism_mod_scene);
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
