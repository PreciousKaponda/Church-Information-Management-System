package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import java.io.IOException;

/**
 * Created by Kaponda on 06 Aug 2021.
 */

public class baptism_add {
    public Parent add_baptism_root;
    public Scene add_baptism_scene;

    public void add_baptised_member() {
        try {
            add_baptism_root = FXMLLoader.load(getClass().getResource("baptism_add_gui.fxml"));
        }catch(IOException e){

        }

        add_baptism_scene = new Scene(add_baptism_root, 880,500);
        add_baptism_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setTitle("Kapita C.C.A.P. IMS");
        Main.stage.setScene(add_baptism_scene);
        Main.stage.centerOnScreen();
        Main.stage.setOnCloseRequest(e -> {
            e.consume();
            exitProtocol();
        });
        Main.stage.show();
    }

    //Confirm to exit the window
    private void exitProtocol(){
        //boolean value that gets the true or false value that is returned from the confirm exit window's two buttons ("OK" and "Cancel")
        Boolean affirmation = confirmBox.affirm("Confirm Exit", "Are You Sure You Want to Exit?", 250, 80);

        if (affirmation){
            Main.stage.close();
        }
    }
}
