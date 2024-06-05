package sample;

import javafx.fxml.*;
import javafx.scene.*;
import java.io.IOException;

/**
 * Created by Kaponda on 17-May-2021.
 */

public class archive_transfers {
    Parent archive_transfers_root;
    Scene archive_transfers_scene;

    public void view_archive_transfers() {
        try {
            archive_transfers_root = FXMLLoader.load(getClass().getResource("archive_transfers_gui.fxml"));
        } catch (IOException e) {
            System.out.println(e);
        }

        archive_transfers_scene = new Scene(archive_transfers_root, 1045, 500);
        archive_transfers_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setScene(archive_transfers_scene);
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
