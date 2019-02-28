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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.*;

public class EmneController1 {

    @FXML public Label lblStatus;
    @FXML public Label lblTid;
    @FXML public TextField txtEmne;
    @FXML public Button btnLeggTilEmne;
    @FXML public Label lblBrukernavn;
    @FXML public Button btnEmne1 ;
    @FXML public Button btnEmne2 ;
    @FXML public Button btnEmne3 ;
    @FXML public Button btnEmne4 ;
    public ArrayList<Button> btns = new ArrayList<>();

    @FXML private TableView<List<StringProperty>> table;
    @FXML private TableColumn<List<StringProperty>, String> studentColumn;
    @FXML private TableColumn<List<StringProperty>, String> datoColumn;
    @FXML private TableColumn<List<StringProperty>, String> tidspunktColumn;
    @FXML private TableColumn<List<StringProperty>, String> studassColumn;
    @FXML private TableView<List<StringProperty>> table1;
    @FXML private TableColumn<List<StringProperty>, String> datoColumn1;
    @FXML private TableColumn<List<StringProperty>, String> tidspunktColumn1;
    @FXML private TableColumn<List<StringProperty>, String> emneidColumn1;
    @FXML private TableColumn<List<StringProperty>, String> varighetColumn1;

    @FXML protected void initialize() {
        lblBrukernavn.setText(UserManager._bruker);
        btns.add(btnEmne1);
        btns.add(btnEmne2);
        btns.add(btnEmne3);
        btns.add(btnEmne4);
        showButtons();
        showTable();
    }

    private void showButtons(){
        for(int i = 0; i < 4; i++){
            try{
                btns.get(i).setText(UserManager._rolle.get(i).get(0)+": "+UserManager._rolle.get(i).get(1));
                btns.get(i).visibleProperty().setValue(true);
            }catch(Exception e){
            }
        }
    }
    //TODO: Klikker når det ikke er lagt til noen fag fra tidligere.
    @FXML protected void addEmne(ActionEvent event){

        if(Database.getRolle(UserManager._bruker, txtEmne.getText())!= ""){
            lblStatus.setText("|Allerede meldt opp!");
        }
        else {
            if (Database.addRolle(txtEmne.getText(), UserManager._bruker, "student")) {
                lblStatus.setText(UserManager._bruker + " meldt opp i " + txtEmne.getText());
            } else {
                lblStatus.setText("|Klarte ikke legge til!");
            }
        }
        UserManager.updateRoller();
        showButtons();
    }

    @FXML protected void setAktivtEmne(ActionEvent event) throws Exception{
        String text = ((Button)event.getSource()).getText();
        UserManager._aktivtEmne = text.substring(0,7);
        UserManager._aktivRolle = text.substring(9);
        openScene();

    }

    private void showTable() {

        studentColumn.setCellValueFactory(param -> param.getValue().get(0));
        datoColumn.setCellValueFactory(param -> param.getValue().get(1));
        tidspunktColumn.setCellValueFactory(param -> param.getValue().get(2));
        studassColumn.setCellValueFactory(param -> param.getValue().get(3));
        table.setItems(getBooking());

        table1.visibleProperty().setValue(false);
        /*for (ArrayList list : UserManager._rolle){
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



    protected void showTid() {

        /*ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getBooking(UserManager._bruker);
        String str = "|| Student \t||\t Dato \t||\t Tidspunkt \t||\t Studass \t||\t \n";
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                ArrayList<String> values = entry.getValue();
                SimpleDateFormat defaultF = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                String nu = defaultF.format(calendar.getTime());
                String b = values.get(1);

                if (Integer.parseInt(nu.substring(0,4)) <= Integer.parseInt(b.substring(0,4))
                    && Integer.parseInt(nu.substring(5,7)) <= Integer.parseInt(b.substring(5,7))
                    && Integer.parseInt(nu.substring(8)) <= Integer.parseInt(b.substring(8))){
                    str += "||";
                    for (String v : values) {
                        str += " " + v + " \t||\t";
                    }
                    str += "\n";
                }



            }
        }
        lblTid.setText(str);*/
    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController1 l = new LoginController1();
        l.logout(btnEmne1);

    }


    @FXML protected void openScene() throws Exception {
        Parent root;
        Stage stage = (Stage) btnEmne1.getScene().getWindow();
        if (UserManager._aktivRolle.equals("faglærer")){
            root = FXMLLoader.load(getClass().getResource("faglaerer1.fxml"));
            stage.setTitle("Faglærer");
        }
        else if (UserManager._aktivRolle.equals("studass")){
            root = FXMLLoader.load(getClass().getResource("studass1.fxml"));
            stage.setTitle("Studass");
        }
        else {
            root = FXMLLoader.load(getClass().getResource("student1.fxml"));
            stage.setTitle("Booking");
        }
        Scene scene =  new Scene(root, 700 ,500);
        stage.setScene(scene);
        stage.show();
    }

    public void back(Button b) throws Exception {
        Stage stage = (Stage) b.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("emne1.fxml"));
        Scene scene =  new Scene(root, 700 ,500);
        stage.setTitle("Emne");
        stage.setScene(scene);
        stage.show();

    }
}


