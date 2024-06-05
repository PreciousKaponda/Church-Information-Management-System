package sample;

import javafx.fxml.*;
import javafx.scene.*;
import java.io.*;

/**
 * Created by Kaponda on 23 Mar 2021.
 */
public class transfers {
    Parent transfers_root;
    Scene transfers_scene;

    public void transfer() {
        try {
            transfers_root = FXMLLoader.load(getClass().getResource("transfers_gui.fxml"));
        } catch (IOException e) {

        }

        transfers_scene = new Scene(transfers_root, 635, 470);
        transfers_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setScene(transfers_scene);
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
