package app;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class FaglaererController {

    /*
     *  Klassen som er koblet opp mot faglaerer.fxml
     *
     *  Metoder:
     *      check()
     *          -Sjekker at dato er på rett format
     *      addSaltid(ActionEvent event)
     *          -Sender en saltid request til app.UserManager når man trykker på tilhørende knapp
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
        showDate(0);
        showTable();
    }

    private void showDate(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Calendar today = Calendar.getInstance();
        if(defaultF.format(today.getTime()).equals(defaultF.format(calendar.getTime())) && i == -1 ){
            return;
        }
        calendar.add(Calendar.DAY_OF_MONTH, i);
        lblDato.setText(sdf.format(calendar.getTime()));
        txtDato.setText(defaultF.format(calendar.getTime()));
        showTable();
    }
    @FXML protected void showNextDay(ActionEvent event) throws Exception {
        showDate(1);
    }
    @FXML protected void showPrevDay(ActionEvent event) throws Exception {
        showDate(-1);
    }
    @FXML protected void addSaltid(ActionEvent event) throws Exception {
        if (Check.checkDato(txtDato.getText()) && Check.checkTidspunkt(txtFra.getText()) && Check.checkTidspunkt(txtTil.getText())){
            if (UserManager.addSaltid(txtDato.getText(),txtFra.getText(),txtTil.getText(), txtTidPerStudent.getText())) {
                lblStatus.setText("Add success!");
            }else {
                lblStatus.setText("Add failed!");
            }
        }else {
            lblStatus.setText("Dato og/eller klokkeslett skrevet på feil format!");
        }
        showTable();
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
                if (Check.future(key)){
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
        EmneController ec = new EmneController();
        ec.back(btnAddSaltid);

    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(btnAddSaltid);

    }

    @FXML protected void deleteSaltider(ActionEvent event) throws Exception {
        Database.deleteSaltid(UserManager._aktivtEmne,defaultF.format(calendar.getTime()));
        showTable();
    }

}
