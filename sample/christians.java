package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import java.io.IOException;

/**
 * Created by Kaponda on 26-Apr-18.
 */
public class christians {
    public Parent akhristuRoot;
    public Scene akhristuScene;

    public void viewChristians(){
        try {
            akhristuRoot = FXMLLoader.load(getClass().getResource("christiansTableGui.fxml"));
        }catch(IOException e){

        }

        akhristuScene = new Scene(akhristuRoot, 900,500);
        akhristuScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        //akhristuScene.getStylesheets().add("style.css");

        Main.stage.setTitle("Kapita C.C.A.P. IMS");
        Main.stage.setScene(akhristuScene);
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
