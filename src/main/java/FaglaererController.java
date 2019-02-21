import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class FaglaererController {
    /*
     *  Klassen som er koblet opp mot faglaerer.fxml
     *
     *  Metoder:
     *      check()
     *          -Sjekker at dato er på rett format
     *      addSaltid(ActionEvent event)
     *          -Sender en saltid request til UserManager når man trykker på tilhørende knapp
     *
     */
    @FXML public TextField txtDate;
    @FXML public TextField txtFra;
    @FXML public TextField txtTil;
    @FXML public TextField txtEmneID;
    @FXML public TextField txtTidPerStudent;
    @FXML public Button btnLeggTil;
    @FXML public Label lblStatus;



    public boolean checkFraTil() {
        boolean bool = false;
        try{
            Integer.parseInt(txtFra.getText().substring(0,2));
            Integer.parseInt(txtFra.getText().substring(3));
            Integer.parseInt(txtTil.getText().substring(0,2));
            Integer.parseInt(txtTil.getText().substring(3));
            if(txtFra.getText().substring(2,3).equals(":") && txtFra.getText().length() == 5 && txtTil.getText().substring(2,3).equals(":") && txtTil.getText().length() == 5) {
                bool = true;
            }
        }
        catch (Exception e){
            lblStatus.setText("|Klokkeslett skrevet på feil format!");
            bool = false;
        }finally {
            return bool;
        }
    }

    public boolean checkDato() {
        boolean bool = false;
        try {
            Integer.parseInt(txtDate.getText().substring(0,4));
            Integer.parseInt(txtDate.getText().substring(5,7));
            Integer.parseInt(txtDate.getText().substring(8));
            if(txtDate.getText().substring(4,5).equals("-") && txtDate.getText().substring(7,8).equals("-") && txtDate.getText().length() == 10) {
                bool = true;
            }
        }
        catch (Exception e){
            lblStatus.setText("|Dato skrevet på feil format!");
            bool = false;
        }finally {
            return bool;
        }
    }

    @FXML protected void addSaltid(ActionEvent event) throws Exception {
        if (checkDato() && checkFraTil()){
            if (UserManager.addSaltid(txtDate.getText(),txtFra.getText(),txtTil.getText(), txtEmneID.getText(), txtTidPerStudent.getText())) {
                lblStatus.setText("|Add success!");
            }else {
                lblStatus.setText("|Add failed!");
            }
        }else {
            lblStatus.setText("|Dato og/eller klokkeslett skrevet på feil format!");
        }
    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(btnLeggTil);

    }

}
