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
    @FXML public Button addBtn ;


    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws Exception {
        Database.addBruker(userField.getText(),nameField.getText(),passwordField.getText());
        actiontarget.setText("|Add success!");

    }
    @FXML protected void logout(ActionEvent event) throws Exception {
        openScene();

    }
    @FXML protected void openScene() throws Exception {
        Stage stage = (Stage) addBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene =  new Scene(root, 300 ,275);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
