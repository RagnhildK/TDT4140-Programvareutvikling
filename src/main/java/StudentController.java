import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentController {
    /*
     *  Klassen som er koblet opp mot student.fxml
     *
     *  Metoder:
     *      book(ActionEvent event)
     *          -Sender en booking request til UserManager når man trykker på tilhørende knapp
     *      showTid(ActionEvent event)
     *          -Viser saltider for studasser når man trykker på tilhørende knapp
     *
     *
     */
    @FXML public Text status;
    @FXML public TextField studassField;
    @FXML public TextField datoField;
    @FXML public TextField tidspunktField;
    @FXML public Button bookBtn;


    @FXML protected void book(ActionEvent event) throws Exception {
        if (Check.checkDato(datoField.getText()) && Check.checkTidspunkt(tidspunktField.getText())){
            if (UserManager.booking(datoField.getText(), tidspunktField.getText(), studassField.getText())){
                status.setText("|Booking success!");
            }else {
                status.setText("|Booking failed!");
            }
        }



    }

    @FXML protected void showTid(ActionEvent event) throws Exception {

        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getStudassPåSal(datoField.getText(), UserManager._aktivtEmne);
        String str = "|| Dato \t||\t Tidspunkt \t||\t Emne \t||\t Studass \t||\t Varighet \t||\t \n";
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                String key = entry.getKey();
                ArrayList<String> values = entry.getValue();
                ArrayList<HashMap<String, ArrayList<String>>> booking = Database.getUnikBooking(key, values.get(0), values.get(2));
                if (booking.isEmpty()){
                    str += "|| " + key + " \t||\t";
                    for (String v : values) {
                        str += " " + v + " \t||\t";
                    }
                    str += "\n";
                }

            }
        }
        status.setText(str);

    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(bookBtn);

    }

}
