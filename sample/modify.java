package sample;

import javafx.fxml.*;
import javafx.scene.*;
import java.io.*;

/**
 * Created by Kaponda on 04 Mar 2021.
 */
public class modify {
    Parent modify_root;
    Scene modify_scene;

    public void modifications() {
        try {
            modify_root = FXMLLoader.load(getClass().getResource("modify_gui.fxml"));
        } catch (IOException e) {

        }

        modify_scene = new Scene(modify_root, 850, 500);
        modify_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setScene(modify_scene);
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
