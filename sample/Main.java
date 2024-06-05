package sample;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stage;
    static login_window lw;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        //instantiating the stage/window that will be used throughout the system
        stage = new Stage();
        //adding icon to title bar
        stage.getIcons().add(new Image("img/ccap_logo.png"));

        //calling the first class: login_window to display the login window
        lw = new login_window();
        lw.login_visual();
    }

}