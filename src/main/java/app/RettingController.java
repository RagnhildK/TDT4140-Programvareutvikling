package app;


import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RettingController {
    /*
     * Siden som er koblet opp mot rettinger.fxml
     * Kan sende rettinger på innleveringer
     */

    @FXML public Button btnLever;
    @FXML public Button btnOpenFile;
    @FXML public Button btnBack;
    @FXML public Button btnLoggut;
    @FXML public JFXTextArea txtKommentar;
    @FXML public JFXTextField txtFilnavn;
    @FXML public Label lblStatus;
    @FXML public Label lblBrukernavn;
    @FXML public Label lblØving;
    @FXML public JFXListView listView;
    @FXML public JFXCheckBox checkGodkjent;


    //Lokale variabler
    public String ovingID;
    public String innleveringID;
    public File file;

    @FXML protected void initialize() throws Exception {
        lblBrukernavn.setText(UserManager._bruker);
        showOvinger();
        //Gjør at en kan trykke i listviewen og få opp innleveringer
        listView.setOnMouseClicked(new ListViewHandler(){
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                String s = listView.getSelectionModel().getSelectedItem().toString();
                int i = s.indexOf("'");
                String str = s.substring(i+1,s.length()-1);
                lblØving.setText(str);
                ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getUnikInnlevering(str);
                for (HashMap<String,ArrayList<String>> set : dbOutput) {
                    for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                        innleveringID = entry.getKey();
                        ArrayList<String> values = entry.getValue();
                        ovingID = values.get(0);
                        lblØving.setText(values.get(1));
                        file = Database.getInnlevering(innleveringID, innleveringID+" "+values.get(1)+".pdf");
                        lblStatus.setText("Levert: " + values.get(2) + "\nBeskrivelse: " + values.get(3));
                    }
                }
            }
        });
    }
    //Viser innleveringer i listviewen
    public void showOvinger(){
        listView.getItems().clear();
        ArrayList<String> list = new ArrayList();
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getInnleveringer(UserManager._aktivtEmne);
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                ArrayList<String> values = entry.getValue();
                String key = entry.getKey();
                list.add(key);
            }
        }
        for (String b : list) {
            listView.getItems().add(new Label(b));
        }
    }

    //Sender en request om retting
    @FXML public void lever(ActionEvent event){
        //TODO: Funker ikke per nå
        if(Database.addRetting(innleveringID, UserManager._bruker, String.valueOf(checkGodkjent.isSelected()), txtKommentar.getText())){
            lblStatus.setText("Add success!");
        }else {
            lblStatus.setText("Add failed!");
        }
        txtFilnavn.setText("");
        txtKommentar.setText("");
    }

    @FXML protected void openFile(ActionEvent event) throws Exception {
        Desktop.getDesktop().open(file);
    }



    @FXML protected void back(ActionEvent event) throws Exception {
        EmneController ec = new EmneController();
        ec.back(btnBack);
    }
    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(btnLoggut);
    }

    //Åpner rettingsiden
    public void openRetting(Button b) throws Exception{
        Stage stage = (Stage) b.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/retting.fxml"));
        Scene scene =  new Scene(root, 700 ,500);
        stage.setTitle("Retting");
        stage.setScene(scene);
        stage.show();
    }


}
