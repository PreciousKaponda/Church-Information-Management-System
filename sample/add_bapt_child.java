package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import java.io.IOException;

/**
 * Created by Kaponda on 11 May 2021.
 */
public class add_bapt_child {
    public Parent add_bapt_child_root;
    public Scene add_bapt_child_scene;

    public void add_baptised_child() {
        try {
            add_bapt_child_root = FXMLLoader.load(getClass().getResource("add_bapt_child_gui.fxml"));
        }catch(IOException e){

        }

        add_bapt_child_scene = new Scene(add_bapt_child_root, 900,530);
        add_bapt_child_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setTitle("Kapita C.C.A.P. IMS");
        Main.stage.setScene(add_bapt_child_scene);
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
