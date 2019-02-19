import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class FaglærerController {

    @FXML public TextField txtDate;
    @FXML public TextField txtFra;
    @FXML public TextField txtTil;
    @FXML public TextField txtEmneID;
    @FXML public TextField txtTidPerStudent;
    @FXML public Button btnLeggTil;
    @FXML public Label lblStatus;


    public boolean check() {
        /*Integer.parseInt(txtDate.substring(0,4));
        Integer.parseInt(txtDate.substring(5,7));
        Integer.parseInt(txtDate.substring(7,9));

        if(txtDate.substring(4,5).equals("-") && txtDate.substring(7,8).equals("-") && txtDate.length() == 10) {
            return true;
        }*/
        return false;
    }

    @FXML protected void addUser(ActionEvent event) throws Exception {


        if (UserManager.addSaltid(txtDate.getText(),txtFra.getText(),txtTil.getText(), txtEmneID.getText(), txtTidPerStudent.getText())) {
            lblStatus.setText("|Add success!");
        }else {
            lblStatus.setText("|Add failed!");
        }


    }





}
