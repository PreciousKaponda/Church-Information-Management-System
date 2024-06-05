package sample;

import javafx.fxml.*;
import javafx.scene.*;
import java.io.*;

/**
 * Created by Kaponda on 25-Apr-18.
 */
public class clerk_home {
    private Parent clerkRoot;
    private Scene clerkScene;

    public void clerk(){
        try {
            clerkRoot = FXMLLoader.load(getClass().getResource("clerk_home.fxml"));
        }catch(IOException e){

        }

        clerkScene = new Scene(clerkRoot, 600, 510);
        clerkScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Main.stage.setTitle("Kapita C.C.A.P. IMS");
        Main.stage.setScene(clerkScene);
        Main.stage.centerOnScreen();
        Main.stage.setOnCloseRequest(e -> {
            e.consume();
            exitProtocol();
        });
        Main.stage.show();


    }


    private static void exitProtocol(){
        //boolean value that gets the true or false value that is returned from the confirm exit window's two buttons ("OK" and "Cancel")
        Boolean affirmation = confirmBox.affirm("Confirm Exit", "Are You Sure You Want to Exit?", 250, 80);

        if (affirmation){
            Main.stage.close();
        }
    }
}
