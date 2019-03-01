package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminController {
    /*
     *  Klassen som er koblet opp mot admin.fxml
     *
     *  Metoder:
     *      addUser(ActionEvent event)
     *          -Legger til en bruker i databasen
     *      addEmne(ActionEvent event)
     *          -Legger til et emne i databasen
     *      logout(Button b)
     *          -Gjør at en kan logge ut fra andre fxml filer og åpne Login.fxml
     */

    @FXML public Text actiontarget;
    @FXML public TextField userField;
    @FXML public TextField nameField;
    @FXML public PasswordField passwordField;
    @FXML public Button addUserBtn ;

    @FXML public TextField emneField;
    @FXML public TextField enameField;
    @FXML public TextField faglærerField;
    @FXML public Button addEmneBtn ;
    @FXML public TextField enameField1;
    @FXML public TextField nameField1;
    @FXML public TextField roleField;
    @FXML public Button addRolebtn;
    @FXML public Label lblUser;


    @FXML protected void addUser(ActionEvent event) throws Exception {
        if (Database.addBruker(userField.getText(),nameField.getText(),passwordField.getText())) {
            actiontarget.setText("|Add success!");
        }else {
            actiontarget.setText("|Add failed!");
        }


    }
    @FXML protected void addEmne(ActionEvent event) throws Exception {
        if (Database.addEmne(emneField.getText(),enameField.getText()) &&
        Database.addRolle(emneField.getText(), faglærerField.getText(), "faglærer")) {
            actiontarget.setText("|Add success!");
        }else{
            actiontarget.setText("|Add failed!");
        }


    }

    @FXML protected void addRolle (ActionEvent event) throws Exception {
        if(Database.getRolle(nameField1.getText(), enameField1.getText())!= ""){
            if(Database.updateRolle(enameField1.getText(),nameField1.getText(), roleField.getText())) {
                actiontarget.setText(nameField1.getText()+" har fått rollen "+roleField.getText());
            }else{
                actiontarget.setText("|Update failed!");
            }
        }
        else{
            if(Database.addRolle(enameField1.getText(),nameField1.getText(), roleField.getText())){
                actiontarget.setText(nameField1.getText()+" har fått rollen "+roleField.getText());
            }else{
                actiontarget.setText("|Add failed!");
            }
        }
    }

    @FXML protected void showUsers(ActionEvent event) throws Exception {
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getUser("all");
        String str = "|| BrukerNavn \t||\t Bruker \t||\t Passord \t||\t Rolle \t||\t EmneID \t||\t Emne\n";
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
        lblUser.setText(str);
    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(addEmneBtn);

    }
}
