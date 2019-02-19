import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class LoginController {
    @FXML public Text actiontarget;
    @FXML public TextField userField;
    @FXML public PasswordField passwordField;
    @FXML private Button loginBtn ;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws Exception {
        if (UserManager.checkLogin(userField.getText(),passwordField.getText())){
            actiontarget.setText("|Login success!");
            openScene();
        }else {
            actiontarget.setText("|Login failed!");
        }
    }
    @FXML protected void openScene() throws Exception {
        Parent root;
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        if (UserManager._rolle.get(0).get(0).equals("admin")){
            root = FXMLLoader.load(getClass().getResource("admin.fxml"));
            stage.setTitle("Admin");
        }else {
            root = FXMLLoader.load(getClass().getResource("student.fxml"));
            stage.setTitle("Booking");
        }
        Scene scene =  new Scene(root, 500 ,275);
        stage.setScene(scene);
        stage.show();
    }
}
