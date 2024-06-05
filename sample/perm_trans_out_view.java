package sample;

import javafx.fxml.*;
import javafx.scene.*;
import java.io.*;
/**
 * Created by Kaponda on 27 Mar 2021.
 */
public class perm_trans_out_view {
    Parent perm_trans_out_view_root;
    Scene perm_trans_out_view_scene;

    public void perm_trans_out_table() {
        try {
            perm_trans_out_view_root = FXMLLoader.load(getClass().getResource("perm_trans_out_view_gui.fxml"));
        } catch (IOException e) {

        }

        perm_trans_out_view_scene = new Scene(perm_trans_out_view_root, 880, 500);
        perm_trans_out_view_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setScene(perm_trans_out_view_scene);
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
