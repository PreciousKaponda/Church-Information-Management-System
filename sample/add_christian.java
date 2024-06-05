package sample;

import javafx.fxml.*;
import javafx.scene.*;
import java.io.*;

/**
 * Created by Kaponda on 02 May 2020.
 */
public class add_christian {
    private Parent addChristianRoot;
    Scene addChristianScene;

    public void add_new_member() {
        try {
            addChristianRoot = FXMLLoader.load(getClass().getResource("add_christian_gui.fxml"));
        } catch (IOException e) {

        }

        addChristianScene = new Scene(addChristianRoot, 550, 600);
        addChristianScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setScene(addChristianScene);
        Main.stage.setTitle("Kapita C.C.A.P. IMS");
        Main.stage.centerOnScreen();
        Main.stage.setOnCloseRequest(e -> {
            e.consume();
            exitProtocol();
        });
        Main.stage.show();
    }

    //Confirm to exit the window
    private static void exitProtocol(){
        //boolean value that gets the true or false value that is returned from the confirm exit window's two buttons ("OK" and "Cancel")
        Boolean affirmation = confirmBox.affirm("Confirm Exit", "Are You Sure You Want to Exit?", 250, 80);

        if (affirmation){
            Main.stage.close();
        }
    }
}
