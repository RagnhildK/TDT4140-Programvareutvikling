import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class EmneController {

    @FXML public Text actiontarget;
    @FXML public TextField txtEmne;
    @FXML public Button btnLeggTilEmne;
    @FXML public Label lblBrukerNavn;
    @FXML public Label lblEmne;
    @FXML public Button btnEmne1 ;
    @FXML public Button btnEmne2 ;
    @FXML public Button btnEmne3 ;
    @FXML public Button btnEmne4 ;

    @FXML protected void initialize() {
        lblEmne.setText(UserManager._rolle.toString());

    }
    @FXML protected void addEmne(ActionEvent event){

    }


    /*
    @FXML protected void openScene() throws Exception {
        Parent root;
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        if (UserManager._rolle.get(0).get(1).equals("admin")){
            root = FXMLLoader.load(getClass().getResource("admin.fxml"));
            stage.setTitle("Admin");
        }else if (UserManager._rolle.get(0).get(1).equals("faglærer")){
            root = FXMLLoader.load(getClass().getResource("faglaerer.fxml"));
            stage.setTitle("Faglærer");
        }
        else if (UserManager._rolle.get(0).get(1).equals("studass")){
            root = FXMLLoader.load(getClass().getResource("studass.fxml"));
            stage.setTitle("Studass");
        }
        else {
            root = FXMLLoader.load(getClass().getResource("student.fxml"));
            stage.setTitle("Booking");
        }
        Scene scene =  new Scene(root, 500 ,300);
        stage.setScene(scene);
        stage.show();
    }
*/
}

