package app;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;

import java.text.SimpleDateFormat;
import java.util.*;


public class StudentController {
    /*
     *  Klassen som er koblet opp mot student.fxml
     *
     *  Metoder:
     *      book(ActionEvent event)
     *          -Sender en booking request til app.UserManager når man trykker på tilhørende knapp
     *      showTid(ActionEvent event)
     *          -Viser saltider for studasser når man trykker på tilhørende knapp
     *
     *
     */
    @FXML public Label lblStatus;
    @FXML public Label lblTid;
    @FXML public Label lblBrukernavn;
    @FXML public Label lblDato;
    @FXML public TextField txtStudass;
    @FXML public TextField txtDato;
    @FXML public TextField txtTidspunkt;
    @FXML public Button btnBook;

    @FXML private TableView<List<StringProperty>> table;
    @FXML private TableColumn<List<StringProperty>, String> datoColumn;
    @FXML private TableColumn<List<StringProperty>, String> tidspunktColumn;
    @FXML private TableColumn<List<StringProperty>, String> emneColumn;
    @FXML private TableColumn<List<StringProperty>, String> studassColumn;
    @FXML private TableColumn<List<StringProperty>, String> varighetColumn;

    private Calendar calendar;
    private SimpleDateFormat defaultF = new SimpleDateFormat("yyyy-MM-dd");

    @FXML protected void initialize() throws Exception {
        lblBrukernavn.setText(UserManager._bruker);
        calendar = Calendar.getInstance();
        showDate(0);
        table.visibleProperty().setValue(true);
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

    @FXML protected void book(ActionEvent event) throws Exception {
        if (Check.checkDato(txtDato.getText()) && Check.checkTidspunkt(txtTidspunkt.getText())){
            if (UserManager.booking(txtDato.getText(), txtTidspunkt.getText(), txtStudass.getText())){
                lblStatus.setText("Booking success!");
            }else {
                lblStatus.setText("Booking failed!");
            }
        }
        showTable();
    }

    private void showTable() {
        datoColumn.setCellValueFactory(param -> param.getValue().get(0));
        tidspunktColumn.setCellValueFactory(param -> param.getValue().get(1));
        emneColumn.setCellValueFactory(param -> param.getValue().get(2));
        studassColumn.setCellValueFactory(param -> param.getValue().get(3));
        varighetColumn.setCellValueFactory(param -> param.getValue().get(4));

        table.setItems(getData());
    }

    public ObservableList<List<StringProperty>> getData()  {
        ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getStudassPåSal(defaultF.format(calendar.getTime()), UserManager._aktivtEmne);
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                List<StringProperty> row = new ArrayList<>();
                String key = entry.getKey();
                ArrayList<String> values = entry.getValue();
                ArrayList<HashMap<String, ArrayList<String>>> booking = Database.getUnikBooking(key, values.get(0), values.get(2));
                if (booking.isEmpty()){
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
        ec.back(btnBook);

    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(btnBook);

    }

}
