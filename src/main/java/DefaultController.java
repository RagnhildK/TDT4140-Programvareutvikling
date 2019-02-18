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

public class DefaultController {

    @FXML public Text actiontarget;
    @FXML public TextField studassField;
    @FXML public TextField datoField;
    @FXML public TextField tidspunktField;
    @FXML public Button bookBtn ;


    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws Exception {
        UserManager.booking(datoField.getText(), tidspunktField.getText(), studassField.getText());
        actiontarget.setText("|Booking success!");

    }
    @FXML protected void logout(ActionEvent event) throws Exception {
        openScene();

    }
    @FXML protected void openScene() throws Exception {
        Stage stage = (Stage) bookBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene =  new Scene(root, 300 ,275);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
