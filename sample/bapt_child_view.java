package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import java.io.IOException;

/**
 * Created by Kaponda on 17 Apr 2021.
 */
public class bapt_child_view {
    public Parent bapt_child_root;
    public Scene bapt_child_scene;

    public void baptised_children() {
        try {
            bapt_child_root = FXMLLoader.load(getClass().getResource("bapt_child_view_gui.fxml"));
        }catch(IOException e){

        }

        bapt_child_scene = new Scene(bapt_child_root, 920,500);
        bapt_child_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setTitle("Kapita C.C.A.P. IMS");
        Main.stage.setScene(bapt_child_scene);
        Main.stage.centerOnScreen();
        Main.stage.setMaximized(true);
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
