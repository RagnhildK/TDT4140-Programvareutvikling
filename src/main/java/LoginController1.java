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

public class LoginController1 {
    /*
     *  Klassen som er koblet opp mot Login.fxml
     *
     *  Metoder:
     *      login(ActionEvent event)
     *          -Sender en login request til UserManager når man trykker på tilhørende knapp
     *      openScene()
     *          -Åpner den fxml fila tilhører de rettigheter brukeren har
     *      logout(Button b)
     *          -Gjør at en kan logge ut fra andre fxml filer og åpne Login.fxml
     */

    @FXML public Label lblStatus;
    @FXML public TextField txtUser;
    @FXML public PasswordField txtPassword;
    @FXML public Button loginBtn ;

    @FXML protected void login(ActionEvent event) throws Exception {
        if (UserManager.checkLogin(txtUser.getText(),txtPassword.getText())){
            lblStatus.setText("|Login success!");
            openScene();
        }else {
            lblStatus.setText("|Login failed!");
        }
    }
    @FXML protected void openScene() throws Exception {
        Parent root;
        Stage stage = (Stage) lblStatus.getScene().getWindow();
        try {
            if (UserManager._rolle.get(0).get(1).equals("admin")){
                root = FXMLLoader.load(getClass().getResource("admin.fxml"));
                stage.setTitle("Admin");
            }
            else {
                root = FXMLLoader.load(getClass().getResource("Emne.fxml"));
                stage.setTitle("Booking");
            }
        }catch (Exception e) {
            root = FXMLLoader.load(getClass().getResource("Emne.fxml"));
            stage.setTitle("Booking");
        }

        Scene scene =  new Scene(root, 700 ,500);
        stage.setScene(scene);
        stage.show();
    }

    @FXML public void logout(Button b) throws Exception {
        Stage stage = (Stage) b.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login2.fxml"));
        Scene scene =  new Scene(root, 300 ,275);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
