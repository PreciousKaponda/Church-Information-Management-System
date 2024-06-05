package sample;

/**
 * Created by Kaponda on 03-Apr-18.
 * Database connection class to be used throughout program
 */
import javafx.scene.control.*;
import java.sql.*;


public class db {

    public static Connection java_db() {
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/kapita_database?allowMultiQueries=true","root","1234");

            return conn;
        }
        catch(SQLException e){
            Alert error = new Alert(Alert.AlertType.WARNING, "NO DATABASE CONNECTION, CAN'T PROCEED", ButtonType.OK);
            error.setHeaderText("DATABASE ERROR");
            error.setTitle("Kapita C.C.A.P. IMS - Database error");
            error.showAndWait();

            return null;
        }
    }
}
