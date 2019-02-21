import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.application.Application;
import javafx.stage.Stage;

public class FXMLMain extends Application{
    /*
     *  Klassen som starter Login FXMLen.
     *  Metoder:
     *      Start() - Starter Login.fxml
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        //UserManager.main();
    }
}
