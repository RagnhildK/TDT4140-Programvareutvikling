package app;


import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.SimpleDateFormat;
import java.util.*;

import javafx.fxml.FXML;
import jdk.internal.vm.compiler.collections.EconomicMap;

public class MeldingerController {

    @FXML public Button btnSend;
    @FXML public Button btnBack;
    @FXML public Button btnLoggut;
    @FXML public JFXTextArea txtMelding;
    @FXML public JFXTextField txtBruker;
    @FXML public Label lblMeldinger;
    @FXML public Label lblStatus;
    @FXML public Label lblBrukernavn;
    @FXML public Label lblTil;
    @FXML public ScrollPane scrollPane;
    @FXML public AnchorPane vindu;
    @FXML public AnchorPane anchorPane;
    @FXML public JFXListView listView;

    public String sender;
    private static final String ITEM = "Item ";
    private int counter = 0;

    @FXML protected void initialize() throws Exception {
        lblBrukernavn.setText(UserManager._bruker);
        sender = "bob";
        lblTil.setText(sender);
        update(sender);


        //listView = new JFXListView<>();
        for (int i = 0; i < 4; i++) {
            listView.getItems().add(new Label(ITEM + i));
        }



    }

    public void update(String sender){
        String data="";
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getMeldinger(sender,UserManager._bruker);
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                ArrayList<String> values = entry.getValue();
                String tid;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String dateInString = values.get(3);
                    Date date = sdf.parse(dateInString);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    SimpleDateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm");
                    tid = f.format(calendar.getTime());
                }catch (Exception e){
                    System.out.println(e.getStackTrace().toString());
                    tid = values.get(3);
                }
                String row = tid + " - " + values.get(0) + ":\n" + values.get(1) +"\n";
                data += row;
            }
        }
        lblMeldinger.setText(data);
        vindu.setPrefHeight(lblMeldinger.getHeight());

    }
    public void sendMsg(ActionEvent event){
        Database.addMelding(UserManager._bruker, sender,txtMelding.getText());
        txtMelding.setText("");
        update(sender);
    }
    @FXML protected void addChat(ActionEvent event){
        sender = txtBruker.getText();
        lblTil.setText(sender);
        update(sender);
    }
    @FXML protected void back(ActionEvent event) throws Exception {
        EmneController ec = new EmneController();
        ec.back(btnBack);

    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(btnLoggut);

    }


}
