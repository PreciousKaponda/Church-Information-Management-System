package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import java.io.IOException;

/**
 * Created by Kaponda on 05 Aug 2021.
 */
public class baptism_view {
    public Parent baptised_member_root;
    public Scene baptised_member_scene;

    public void baptised_christians() {
        try {
            baptised_member_root = FXMLLoader.load(getClass().getResource("baptism_view_gui.fxml"));
        }catch(IOException e){

        }

        baptised_member_scene = new Scene(baptised_member_root, 1025,500);
        baptised_member_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setTitle("Kapita C.C.A.P. IMS");
        Main.stage.setScene(baptised_member_scene);
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
