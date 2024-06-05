package sample;

/**
 * Created by Kaponda on 10-Jan-18.
 * Class handling proper exiting for the program
 */

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.*;

public class confirmBox {
    public static boolean response;
    public static Stage exitStage;
    public static Label exitLabel;
    public static Button okbtn, cancelbtn;
    public static Scene exitScene;
    public static GridPane exitGrid1, exitGrid2;
    public static BorderPane exitBorderpane;


    public static boolean affirm(String title, String message, int width, int height){
        //instantiating the stage
        exitStage = new Stage();

        //blocking interactivity with other windows in the program until this one is closed
        exitStage.initModality(Modality.APPLICATION_MODAL);

        //instantiating the javafx control items
        exitLabel = new Label(message);
        exitLabel.setStyle(
                "-fx-font-size: 15px;"
        );
        okbtn = new Button("OK");
        cancelbtn = new Button("Cancel");
        okbtn.setStyle(
                "-fx-font-size: 13px;"
        );
        cancelbtn.setStyle(
                "-fx-font-size: 13px;"
        );

        //handling user events of the two buttons [OK & Cancel]
        okbtn.setOnAction(e -> {
            response = true;
            exitStage.close();
        });
        cancelbtn.setOnAction(e -> {
            response = false;
            exitStage.close();
        });

        /**************************************
         * GridPane layout 1 containing label *
         *************************************/
        //instantiating the GridPane layout tha will hold the label
        exitGrid1 = new GridPane();

        //padding between the GridPane layout and the window
        exitGrid1.setPadding(new Insets(5));

        //padding between individual components in the GridPane layout
        exitGrid1.setVgap(2);
        exitGrid1.setHgap(2);

        //positioning each component in the GridPane layout
        GridPane.setConstraints(exitLabel, 0, 0);

        //adding the label to the GridPane layout
        exitGrid1.getChildren().add(exitLabel);

        //setting the layout to center components within itself
        exitGrid1.setAlignment(Pos.CENTER);


        /*************************************************
         * GridPane layout 2 containing OK & CANCEL btns *
         ************************************************/
        //instantiating the GridPane layout
        exitGrid2 = new GridPane();

        //padding between the GridPane layout and the window
        exitGrid2.setPadding(new Insets(5));

        //padding between individual components in the GridPane layout
        exitGrid2.setVgap(2);
        exitGrid2.setHgap(2);

        //positioning each component in the GridPane layout
        GridPane.setConstraints(okbtn, 0, 1);
        GridPane.setConstraints(cancelbtn, 1, 1);

        //adding components to the GridPane layout
        exitGrid2.getChildren().addAll(okbtn, cancelbtn);

        //setting the layout to center components within itself
        exitGrid2.setAlignment(Pos.CENTER);

        /**********************************************
         * BorderPane layout containing the 2 layouts *
         *********************************************/
        //instantiating the BorderPane layout
        exitBorderpane = new BorderPane();

        //adding the 2 GridPane layouts to the BorderPane
        exitBorderpane.setTop(exitGrid1);
        exitBorderpane.setBottom(exitGrid2);

        exitScene = new Scene(exitBorderpane, width, height);

        exitStage.setScene(exitScene);
        exitStage.centerOnScreen();
        exitStage.setTitle(title);
        exitStage.setResizable(false);
        exitStage.getIcons().add(new Image("img/ccap_logo.png"));
        exitStage.showAndWait();

        return response;
    }
}
