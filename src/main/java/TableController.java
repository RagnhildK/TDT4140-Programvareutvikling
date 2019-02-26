import javafx.beans.property.ReadOnlyStringWrapper;
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

public class TableController {


    @FXML private TableView<List<StringProperty>> table;
    @FXML private TableColumn<List<StringProperty>, String> datoColumn = new TableColumn<>("Dato");
    @FXML private TableColumn<List<StringProperty>, String> tidspunktColumn = new TableColumn<>("Tidspunkt");
    @FXML private TableColumn<List<StringProperty>, String> emneColumn = new TableColumn<>("Emne");
    @FXML private TableColumn<List<StringProperty>, String> studassColumn = new TableColumn<>("Studass");
    @FXML private TableColumn<List<StringProperty>, String> varighetColumn = new TableColumn<>("Varighet");
  

    @FXML protected void initialize() throws Exception {
        datoColumn.setCellValueFactory(param -> param.getValue().get(0));
        tidspunktColumn.setCellValueFactory(param -> param.getValue().get(1));
        emneColumn.setCellValueFactory(param -> param.getValue().get(2));
        studassColumn.setCellValueFactory(param -> param.getValue().get(3));
        varighetColumn.setCellValueFactory(param -> param.getValue().get(4));

        calendar = Calendar.getInstance();

        System.out.println("hei");

        table.setItems(getData());
    }


    private Calendar calendar;
    private SimpleDateFormat defaultF = new SimpleDateFormat("yyyy-MM-dd");


    public ObservableList<List<StringProperty>> getData() throws Exception  {

        ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getStudassPåSal(defaultF.format(calendar.getTime()), UserManager._aktivtEmne);
        for (HashMap<String,ArrayList<String>> set : dbOutput) {

            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {

                String key = entry.getKey();
                List<StringProperty> row = new ArrayList<>();
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
        System.out.println(data);
        return data;
    }



    public ObservableList<List<StringProperty>> getString(){
        ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();
        List<StringProperty> row = new ArrayList<>();
        row.add(new SimpleStringProperty("2019-02-05"));
        row.add(new SimpleStringProperty("16:30"));
        row.add(new SimpleStringProperty("TDT4100"));
        row.add(new SimpleStringProperty("Bob"));
        row.add(new SimpleStringProperty("20"));
        data.add(row);
        return data;
    }

}
