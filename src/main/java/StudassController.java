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

public class StudassController {

    @FXML public Text status;
    @FXML public TextField datoField;
    @FXML public TextField tidspunktField;
    @FXML public TextField emneField;
    @FXML public TextField varighetField;
    @FXML public Button addTidBtn ;

    @FXML protected void addTid(ActionEvent event) throws Exception {
        if (UserManager.addStudassPåSal(datoField.getText(),tidspunktField.getText(),emneField.getText(),varighetField.getText())) {
            status.setText("|Add success!");
        }else {
            status.setText("|Add failed!");
        }
    }


    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(addTidBtn);

    }
}

