import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.*;

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
    @FXML private TableView<List<StringProperty>> table;
    @FXML private TableColumn<List<StringProperty>, String> datoColumn;
    @FXML private TableColumn<List<StringProperty>, String> fraColumn;
    @FXML private TableColumn<List<StringProperty>, String> tilColumn;
    @FXML private TableColumn<List<StringProperty>, String> tpsColumn;

    private Calendar calendar;
    private SimpleDateFormat defaultF = new SimpleDateFormat("yyyy-MM-dd");


    @FXML protected void initialize() throws Exception {
        lblBrukernavn.setText(UserManager._bruker);
        calendar = Calendar.getInstance();
        showDate(false);
        showTable();
    }

    private void showDate(boolean increment) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        if(increment){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        lblDato.setText(sdf.format(calendar.getTime()));
        txtDato.setText(defaultF.format(calendar.getTime()));
        showTable();
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
        initialize();
    }
    private void showTable() {
        datoColumn.setCellValueFactory(param -> param.getValue().get(0));
        fraColumn.setCellValueFactory(param -> param.getValue().get(1));
        tilColumn.setCellValueFactory(param -> param.getValue().get(2));
        tpsColumn.setCellValueFactory(param -> param.getValue().get(3));
        table.setItems(getData());
    }
    public ObservableList<List<StringProperty>> getData()  {
        ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getSaltid(txtDato.getText(), UserManager._aktivtEmne);
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                List<StringProperty> row = new ArrayList<>();
                ArrayList<String> values = entry.getValue();
                String key = entry.getKey();
                SimpleDateFormat defaultF = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                String nu = defaultF.format(c.getTime());
                String b = key;
                if (Check.future(nu,b)){
                    row.add(new SimpleStringProperty(key));
                    for (String v : values) {
                        row.add(new SimpleStringProperty(v));
                    }
                    data.add(row);
                }
            }
        }
        return data;
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
