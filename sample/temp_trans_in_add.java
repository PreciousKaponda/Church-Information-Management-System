package sample;

import javafx.fxml.*;
import javafx.scene.*;
import java.io.IOException;
/**
 * Created by Kaponda on 23 Mar 2021.
 */
public class temp_trans_in_add {
    Parent temp_trans_in_add_root;
    Scene temp_trans_in_add_scene;

    public void add_temp_trans_in() {
        try {
            temp_trans_in_add_root = FXMLLoader.load(getClass().getResource("temp_trans_in_add_gui.fxml"));
        } catch (IOException e) {
            System.out.println(e);
        }

        temp_trans_in_add_scene = new Scene(temp_trans_in_add_root, 550, 555);
        temp_trans_in_add_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setScene(temp_trans_in_add_scene);
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
