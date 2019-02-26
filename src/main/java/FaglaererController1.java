import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FaglaererController1 {

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
    @FXML public Label lblBrukernavn;
    @FXML public TextField txtDato;
    @FXML public TextField txtFra;
    @FXML public TextField txtTil;
    @FXML public TextField txtTidPerStudent;
    @FXML public Button btnAddSaltid;
    @FXML public Label lblStatus;
    @FXML public Label lblDato;
    private Calendar calendar;
    private SimpleDateFormat defaultF = new SimpleDateFormat("yyyy-MM-dd");

    @FXML protected void initialize() throws Exception {
        lblBrukernavn.setText(UserManager._bruker);
        calendar = Calendar.getInstance();
        showDate(false);

    }

    private void showDate(boolean increment) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        if(increment){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        lblDato.setText(sdf.format(calendar.getTime()));
        txtDato.setText(defaultF.format(calendar.getTime()));
    }
    @FXML protected void showTid(ActionEvent event) throws Exception {
        showDate(true);
    }

    @FXML protected void addSaltid(ActionEvent event) throws Exception {
        if (Check.checkDato(txtDato.getText()) && Check.checkTidspunkt(txtFra.getText()) && Check.checkTidspunkt(txtTil.getText())){
            if (UserManager.addSaltid(txtDato.getText(),txtFra.getText(),txtTil.getText(), txtTidPerStudent.getText())) {
                lblStatus.setText("|Add success!");
            }else {
                lblStatus.setText("|Add failed!");
            }
        }else {
            lblStatus.setText("|Dato og/eller klokkeslett skrevet på feil format!");
        }
    }

    @FXML protected void back(ActionEvent event) throws Exception {
        EmneController1 ec = new EmneController1();
        ec.back(btnAddSaltid);

    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(btnAddSaltid);

    }

}
