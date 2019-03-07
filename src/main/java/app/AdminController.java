package app;

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
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.*;

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


    @FXML public Label lblStatus;
    @FXML public Label lblBrukernavn;
    @FXML public Label lblDato;
    @FXML public TextField txtBrukernavn;
    @FXML public TextField txtNavn;
    @FXML public TextField txtPassord;
    @FXML public Button btnBruker;
    @FXML public TextField txtEmneID;
    @FXML public TextField txtEmneNavn;
    @FXML public TextField txtFaglærer;
    @FXML public Button btnEmne;
    @FXML public TextField txtBrukernavnRolle;
    @FXML public TextField txtEmneIDRolle;
    @FXML public TextField txtRolle;
    @FXML public Button btnRolle;
    @FXML public Button btnMeldinger;



    @FXML private TableView<List<StringProperty>> table;
    @FXML private TableColumn<List<StringProperty>, String> brukernavnColumn;
    @FXML private TableColumn<List<StringProperty>, String> navnColumn;
    @FXML private TableColumn<List<StringProperty>, String> emneColumn;
    @FXML private TableColumn<List<StringProperty>, String> rolleColumn;

    private Calendar calendar;
    private SimpleDateFormat defaultF = new SimpleDateFormat("yyyy-MM-dd");

    @FXML protected void initialize() throws Exception {
        lblBrukernavn.setText(UserManager._bruker);
        calendar = Calendar.getInstance();
        showDate();
        table.visibleProperty().setValue(true);
        showTable();
        MeldingerController msg = new MeldingerController();
        int i = msg.checkUleste();
        if (i>0){
            btnMeldinger.setText(btnMeldinger.getText()+" ("+i+")");
        }
    }
    private void showDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        lblDato.setText(sdf.format(calendar.getTime()));
    }
    @FXML protected void addBruker(ActionEvent event) throws Exception {
        if (Database.addBruker(txtBrukernavn.getText(),txtNavn.getText(),txtPassord.getText())) {
            lblStatus.setText("Add success!");
        }else {
            lblStatus.setText("Add failed!");
        }
    }
    @FXML protected void addEmne(ActionEvent event) throws Exception {
        if (Database.addEmne(txtEmneID.getText(),txtEmneNavn.getText()) &&
        Database.addRolle(txtEmneID.getText(), txtFaglærer.getText(), "faglærer")) {
            lblStatus.setText("Add success!");
        }else{
            lblStatus.setText("Add failed!");
        }
    }
    @FXML protected void addRolle (ActionEvent event) throws Exception {
        if(Database.getRolle(txtBrukernavnRolle.getText(), txtEmneIDRolle.getText())!= ""){
            if(Database.updateRolle(txtEmneIDRolle.getText(),txtBrukernavnRolle.getText(), txtRolle.getText())) {
                lblStatus.setText(txtBrukernavnRolle.getText()+" har fått rollen "+txtRolle.getText());
            }else{
                lblStatus.setText("Update failed!");
            }
        }
        else{
            if(Database.addRolle(txtEmneIDRolle.getText(),txtBrukernavnRolle.getText(), txtRolle.getText())){
                lblStatus.setText(txtBrukernavnRolle.getText()+" har fått rollen "+txtRolle.getText());
            }else{
                lblStatus.setText("Add failed!");
            }
        }
    }

    private void showTable() {
        brukernavnColumn.setCellValueFactory(param -> param.getValue().get(0));
        navnColumn.setCellValueFactory(param -> param.getValue().get(1));
        emneColumn.setCellValueFactory(param -> param.getValue().get(2));
        rolleColumn.setCellValueFactory(param -> param.getValue().get(3));
        table.setItems(getData());
    }
    public ObservableList<List<StringProperty>> getData()  {
        ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getBruker("all");
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                List<StringProperty> row = new ArrayList<>();
                String key = entry.getKey();
                ArrayList<String> values = entry.getValue();
                row.add(new SimpleStringProperty(key));
                for (String v : values) {
                    row.add(new SimpleStringProperty(v));
                }
                data.add(row);

            }
        }
        return data;
    }

    @FXML protected void showUsers(ActionEvent event) throws Exception {
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getBruker("all");
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
        //lblUser.setText(str);
    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(btnBruker);

    }

    public void showMeldingsside(ActionEvent event) throws Exception{

        Stage stage = (Stage) btnMeldinger.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/meldinger.fxml"));
        Scene scene =  new Scene(root, 700 ,500);
        stage.setTitle("Meldinger");
        stage.setScene(scene);
        stage.show();

    }
}
