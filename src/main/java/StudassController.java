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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    @FXML protected void showTid(ActionEvent event) throws Exception {
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getSaltid(datoField.getText(), UserManager._aktivtEmne);
        String str = "|| Dato \t||\t Fra \t||\t Til \t||\t EmneID \t||\t Varighet \t||\t Faglærer \t||\t \n";
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                String key = entry.getKey();
                ArrayList<String> values = entry.getValue();
                str += "|| " + key + " \t||\t";
                for (String v : values) {
                    str += " " + v + " \t||\t";
                }
                str += "\n";
            }
        }
        status.setText(str);

    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(addTidBtn);

    }
}

