package app;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


public class InnleveringController {
    /*
     * Siden som er koblet opp mot innleveringer.fxml
     * Kan levere inn øvinger
     */

    @FXML public Button btnLever;
    @FXML public Button btnBack;
    @FXML public Button btnLoggut;
    @FXML public JFXTextArea txtBeskrivelse;
    @FXML public JFXTextField txtFilnavn;
    @FXML public Label lblMeldinger;
    @FXML public Label lblStatus;
    @FXML public Label lblBrukernavn;
    @FXML public Label lblØving;
    @FXML public JFXListView listView;

    //Lokale variabler
    public String ovingID;
    public File file;


    @FXML protected void initialize() throws Exception {
        lblBrukernavn.setText(UserManager._bruker);
        showOvinger();
        //Gjør at en kan trykke i listviewen med øvinger og få opp
        listView.setOnMouseClicked(new ListViewHandler(){
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                String s = listView.getSelectionModel().getSelectedItem().toString();
                int i = s.indexOf("'");
                String str = s.substring(i+1,s.length()-1);
                lblØving.setText(str);
                ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getOvingID(UserManager._aktivtEmne,str);
                for (HashMap<String,ArrayList<String>> set : dbOutput) {
                    for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                        ovingID = entry.getKey();
                        ArrayList<String> values = entry.getValue();
                        lblStatus.setText("Frist: " + values.get(1) + "\nBeskrivelse: " + values.get(0));
                    }
                }
            }
        });
    }
    //Viser øvinger i listviewen
    public void showOvinger(){
        listView.getItems().clear();
        ArrayList<String> list = new ArrayList();
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getOvinger(UserManager._aktivtEmne);
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                ArrayList<String> values = entry.getValue();
                list.add(values.get(1));
            }
        }
        for (String b : list) {
            listView.getItems().add(new Label(b));
        }
    }

    //Sender inn en innlevering til databasen
    @FXML public void lever(ActionEvent event){
        if(Database.addInnlevering(ovingID,UserManager._bruker, txtBeskrivelse.getText(), file)){
            lblStatus.setText("Add success!");
        }else {
            lblStatus.setText("Add failed!");
        }
        txtFilnavn.setText("");
        txtBeskrivelse.setText("");
    }

    @FXML protected void openExplorer(ActionEvent event) throws Exception {
        JFileChooser chooser= new JFileChooser();
        Container content = chooser.getComponentPopupMenu();
        int choice = chooser.showOpenDialog(content);

        if (choice != JFileChooser.APPROVE_OPTION) return;

        File chosenFile = chooser.getSelectedFile();
        file = chosenFile;
        txtFilnavn.setText(file.getPath());
    }


    @FXML protected void back(ActionEvent event) throws Exception {
        EmneController ec = new EmneController();
        ec.back(btnBack);
    }
    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(btnLoggut);
    }

    //Åpner innleveringssiden
    public void openInnlevering(Button b) throws Exception{
        Stage stage = (Stage) b.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/innlevering.fxml"));
        Scene scene =  new Scene(root, 700 ,500);
        stage.setTitle("Innlevering");
        stage.setScene(scene);
        stage.show();
    }


}
