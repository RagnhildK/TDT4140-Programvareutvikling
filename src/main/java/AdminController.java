import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminController {

    @FXML public Text actiontarget;
    @FXML public TextField userField;
    @FXML public TextField nameField;
    @FXML public PasswordField passwordField;
    @FXML public Button addUserBtn ;

    @FXML public TextField emneField;
    @FXML public TextField enameField;
    @FXML public TextField faglærerField;
    @FXML public Button addEmneBtn ;


    @FXML protected void addUser(ActionEvent event) throws Exception {
        if (Database.addBruker(userField.getText(),nameField.getText(),passwordField.getText())) {
            actiontarget.setText("|Add success!");
        }else {
            actiontarget.setText("|Add failed!");
        }


    }
    @FXML protected void addEmne(ActionEvent event) throws Exception {
        if (Database.addEmne(emneField.getText(),enameField.getText()) &&
        Database.addRolle(emneField.getText(), faglærerField.getText(), "faglærer")) {
            actiontarget.setText("|Add success!");
        }else{
            actiontarget.setText("|Add failed!");
        }


    }
    @FXML protected void logout(ActionEvent event) throws Exception {
        openScene();

    }
    @FXML protected void openScene() throws Exception {
        Stage stage = (Stage) addUserBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene =  new Scene(root, 300 ,275);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
