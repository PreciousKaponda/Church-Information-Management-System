package sample;

import javafx.fxml.*;
import javafx.scene.*;
import java.io.IOException;

/**
 * Created by Kaponda on 21-Mar-2021.
 */
public class archives {
    Parent archives_root;
    Scene archives_scene;

    public void depository() {
        try {
            archives_root = FXMLLoader.load(getClass().getResource("archives_gui.fxml"));
        } catch (IOException e) {
            System.out.println(e);
        }

        archives_scene = new Scene(archives_root, 920, 500);
        archives_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setScene(archives_scene);
        Main.stage.setTitle("Kapita C.C.A.P. IMS");
        Main.stage.centerOnScreen();
        //Main.stage.setMaximized(true);
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