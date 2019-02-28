import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.awt.print.Book;
import java.text.SimpleDateFormat;
import java.util.*;


public class StudentController1 {
    /*
     *  Klassen som er koblet opp mot student.fxml
     *
     *  Metoder:
     *      book(ActionEvent event)
     *          -Sender en booking request til UserManager når man trykker på tilhørende knapp
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
        showDate(false);
        showTid(new ActionEvent());
        table.visibleProperty().setValue(true);
        showTable();
    }

    private void showDate(boolean increment) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        if(increment){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        lblDato.setText(sdf.format(calendar.getTime()));
    }

    @FXML protected void book(ActionEvent event) throws Exception {
        if (Check.checkDato(txtDato.getText()) && Check.checkTidspunkt(txtTidspunkt.getText())){
            if (UserManager.booking(txtDato.getText(), txtTidspunkt.getText(), txtStudass.getText())){
                lblStatus.setText("|Booking success!");
            }else {
                lblStatus.setText("|Booking failed!");
            }
        }
        initialize();
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



    @FXML protected void showTid(ActionEvent event) throws Exception  {
        showDate(true);
        txtDato.setText(defaultF.format(calendar.getTime()));
        showTable();
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getStudassPåSal(defaultF.format(calendar.getTime()), UserManager._aktivtEmne);
        /*String str = "|| Dato \t||\t Tidspunkt \t||\t Emne \t||\t Studass \t||\t Varighet \t||\t \n";
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                String key = entry.getKey();
                ArrayList<String> values = entry.getValue();
                ArrayList<HashMap<String, ArrayList<String>>> booking = Database.getUnikBooking(key, values.get(0), values.get(2));
                if (booking.isEmpty()){
                    str += "|| " + key + " \t||\t";
                    for (String v : values) {
                        str += " " + v + " \t||\t";
                    }
                    str += "\n";
                }
            }
        }
        lblTid.setText(str);*/
    }

    @FXML protected void back(ActionEvent event) throws Exception {
        EmneController1 ec = new EmneController1();
        ec.back(btnBook);

    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController1 l = new LoginController1();
        l.logout(btnBook);

    }

}
