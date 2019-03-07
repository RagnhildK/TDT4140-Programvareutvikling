package app;


import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.text.SimpleDateFormat;
import java.util.*;


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
    @FXML public JFXComboBox<String> comboBox;

    public String sender;


    @FXML protected void initialize() throws Exception {
        lblBrukernavn.setText(UserManager._bruker);
        showAvsendere();
        showAllBrukere();

        listView.setOnMouseClicked(new ListViewHandler(){
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                String s = listView.getSelectionModel().getSelectedItem().toString();
                int i = s.indexOf("'");
                lblTil.setText(s.substring(i+1,s.length()-1));
                sender = s.substring(i+1,s.length()-1);
                Database.updateUlest(sender,UserManager._bruker);
                update(sender);
            }
        });
    }

    public void showAllBrukere() {
        ArrayList<String> list = new ArrayList();
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getBrukere();
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                String key = entry.getKey();
                list.add(key);
            }
        }

        for (String e : list){
            comboBox.getItems().add(e);
        }
        comboBox.setEditable(false);
        comboBox.setPromptText("Velg bruker");
    }

    public int checkUleste() {
        int i = 0;
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getAvsendere(UserManager._bruker);
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                ArrayList<String> values = entry.getValue();
                if (values.get(1).equals("1")){
                    i++;
                }
            }
        }
        return i;
    }

    public void showAvsendere(){
        ArrayList<String> list = new ArrayList();
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getAvsendere(UserManager._bruker);
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                ArrayList<String> values = entry.getValue();
                if(!list.contains(values.get(0))){
                    list.add(values.get(0));
                }
            }
        }
        for (String b : list) {
            listView.getItems().add(new Label(b));

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
        sender = comboBox.getValue();
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
