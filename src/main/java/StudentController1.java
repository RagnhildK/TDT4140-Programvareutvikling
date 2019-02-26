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
    @FXML public TableView table;

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

    @FXML protected void showBookinger(){
        try{
            FXMLLoader bookinger = new FXMLLoader(getClass().getResource("table.fxml"));
            Parent root1 = (Parent) bookinger.load();
            Stage stage = new Stage();
            stage.setTitle("Bookinger");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (Exception e){

        }


    }

    private void showTable() {
        List<String> list = new ArrayList();
        list.add(0, "en");
        list.add(1, "to");
        list.add(2, "tre");
        ObservableList<String> data = FXCollections.observableArrayList();
        data.add("Hei");
        table.setItems(data);



        TableColumn datoCol = new TableColumn("Dato");
        TableColumn tidspunktCol = new TableColumn("Tidspunkt");
        TableColumn emneCol = new TableColumn("Emne");
        TableColumn studassCol = new TableColumn("Studass");
        TableColumn varighetCol = new TableColumn("Varighet");
        datoCol.setMinWidth(100);
        tidspunktCol.setMinWidth(100);
        emneCol.setMinWidth(100);
        studassCol.setMinWidth(100);
        varighetCol.setMinWidth(100);
        table.setEditable(true);
        table.getColumns().addAll(datoCol, tidspunktCol, emneCol, studassCol, varighetCol);
        ;

        table.getItems().add(1,"Hello");


    }



    @FXML protected void showTid(ActionEvent event) throws Exception  {
        showDate(true);
        txtDato.setText(defaultF.format(calendar.getTime()));
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getStudassPåSal(defaultF.format(calendar.getTime()), UserManager._aktivtEmne);
        String str = "|| Dato \t||\t Tidspunkt \t||\t Emne \t||\t Studass \t||\t Varighet \t||\t \n";
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
        lblTid.setText(str);
    }

    @FXML protected void back(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnBook.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Emne.fxml"));
        Scene scene =  new Scene(root, 300 ,275);
        stage.setTitle("Emne");
        stage.setScene(scene);
        stage.show();

    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(btnBook);

    }

}
