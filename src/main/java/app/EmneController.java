package app;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.*;

public class EmneController {

    @FXML public Label lblStatus;
    @FXML public Label lblTid;
    @FXML public TextField txtEmne;
    @FXML public Button btnLeggTilEmne;
    @FXML public Label lblBrukernavn;
    @FXML public Button btnMeldinger;

    @FXML public Button btnLoggut;
    @FXML public JFXListView listView;
    @FXML public JFXComboBox<String> comboBox;

    @FXML private TableView<List<StringProperty>> table;
    @FXML private TableColumn<List<StringProperty>, String> emneColumn;
    @FXML private TableColumn<List<StringProperty>, String> datoColumn;
    @FXML private TableColumn<List<StringProperty>, String> tidspunktColumn;
    @FXML private TableColumn<List<StringProperty>, String> studassColumn;
    @FXML private TableView<List<StringProperty>> table1;
    @FXML private TableColumn<List<StringProperty>, String> datoColumn1;
    @FXML private TableColumn<List<StringProperty>, String> tidspunktColumn1;
    @FXML private TableColumn<List<StringProperty>, String> emneidColumn1;
    @FXML private TableColumn<List<StringProperty>, String> varighetColumn1;

    @FXML protected void initialize() throws Exception{
        lblBrukernavn.setText(UserManager._bruker);
        showTable();
        showEmner();
        showAllEmner();
        System.out.println(checkUleste());
        int i = checkUleste();
        if (i>0){
            btnMeldinger.setText(btnMeldinger.getText()+" ("+i+")");
        }
        listView.setOnMouseClicked(new ListViewHandler(){
            @Override
            public void handle(javafx.scene.input.MouseEvent event)  {
                String emneRolle = listView.getSelectionModel().getSelectedItem().toString();
                int i = emneRolle.indexOf("'");
                UserManager._aktivRolle = emneRolle.substring(i+11,emneRolle.length()-1);
                UserManager._aktivtEmne = emneRolle.substring(i+1,i+8);
                openScene();
            }
        });
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

    public void showAllEmner() {
        ArrayList<String> list = new ArrayList();
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getEmner();
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                String key = entry.getKey();
                if (key.equals("admin")){
                    continue;
                }
                list.add(key);
            }
        }

        for (String e : list){
            comboBox.getItems().add(e);
        }
        comboBox.setEditable(false);
        comboBox.setPromptText("Velg emne");
    }


    public void showEmner(){
        ArrayList<String> list = new ArrayList();
        for (ArrayList<String> r : UserManager._rolle){
                list.add(r.get(0)+" - "+r.get(1));
        }
        for (String b : list) {
            listView.getItems().add(new Label(b));
        }
    }



    @FXML protected void addEmne(ActionEvent event){
        if(Database.getRolle(UserManager._bruker, comboBox.getValue())!= ""){
            lblStatus.setText("Allerede meldt opp!");
        }
        else {
            if (Database.addRolle(comboBox.getValue(), UserManager._bruker, "student")) {
                lblStatus.setText(UserManager._bruker + " meldt opp i " + comboBox.getValue());
            } else {
                lblStatus.setText("Klarte ikke legge til!");
            }
        }
        UserManager.updateRoller();
        showEmner();
    }



    private void showTable() {

        emneColumn.setCellValueFactory(param -> param.getValue().get(0));
        datoColumn.setCellValueFactory(param -> param.getValue().get(1));
        tidspunktColumn.setCellValueFactory(param -> param.getValue().get(2));
        studassColumn.setCellValueFactory(param -> param.getValue().get(3));
        table.setItems(getBooking());

        table1.visibleProperty().setValue(false);
        /*for (ArrayList list : app.UserManager._rolle){
            if(list.get(1)=="studass"){
                table1.visibleProperty().setValue(true);
            }
        }*//*
        datoColumn1.setCellValueFactory(param -> param.getValue().get(0));
        tidspunktColumn1.setCellValueFactory(param -> param.getValue().get(1));
        emneidColumn1.setCellValueFactory(param -> param.getValue().get(2));
        varighetColumn1.setCellValueFactory(param -> param.getValue().get(3));
        table1.setItems(getStudassPåSal());*/
    }
    public ObservableList<List<StringProperty>> getStudassPåSal()  {
        ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getMineStudassPåSal(UserManager._bruker);
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                List<StringProperty> row = new ArrayList<>();
                ArrayList<String> values = entry.getValue();
                String key = entry.getKey();
                if (Check.future(key)){
                    for (String v : values) {
                        row.add(new SimpleStringProperty(v));
                    }
                    data.add(row);
                }
            }
        }
        return data;
    }
    public ObservableList<List<StringProperty>> getBooking()  {
        ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getBooking(UserManager._bruker);
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                List<StringProperty> row = new ArrayList<>();
                ArrayList<String> values = entry.getValue();
                if (Check.future(values.get(1))){
                    for (String v : values) {
                        row.add(new SimpleStringProperty(v));
                    }
                    data.add(row);
                }
            }
        }
        return data;
    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(btnLoggut);

    }


    @FXML protected void openScene() {
        try {
            Parent root;
            Stage stage = (Stage) btnLeggTilEmne.getScene().getWindow();
            if (UserManager._aktivRolle.equals("faglærer")){
                root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/faglaerer.fxml"));
                stage.setTitle("Faglærer");
            }
            else if (UserManager._aktivRolle.equals("studass")){
                root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/studass.fxml"));
                stage.setTitle("Studass");
            }
            else {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/student.fxml"));
                stage.setTitle("Booking");
            }
            Scene scene =  new Scene(root, 700 ,500);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {

        }

    }

    public void back(Button b) throws Exception {
        Stage stage = (Stage) b.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/emne.fxml"));
        Scene scene =  new Scene(root, 700 ,500);
        stage.setTitle("Emne");
        stage.setScene(scene);
        stage.show();

    }

    public void showMeldingsside(ActionEvent event) throws Exception{

        Stage stage = (Stage) btnMeldinger.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/meldinger1.fxml"));
        Scene scene =  new Scene(root, 700 ,500);
        stage.setTitle("Meldinger");
        stage.setScene(scene);
        stage.show();

    }
}


