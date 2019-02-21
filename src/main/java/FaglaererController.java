import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

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

    @FXML protected void addSaltid(ActionEvent event) throws Exception {
        if (Check.checkDato(txtDate.getText()) && Check.checkTidspunkt(txtFra.getText()) && Check.checkTidspunkt(txtTil.getText())){
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
