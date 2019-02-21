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

import java.util.ArrayList;

public class EmneController {

    @FXML public Label status;
    @FXML public TextField txtEmne;
    @FXML public Button btnLeggTilEmne;
    @FXML public Label lblBrukerNavn;
    @FXML public Label lblEmne;
    @FXML public Button btnEmne1 ;
    @FXML public Button btnEmne2 ;
    @FXML public Button btnEmne3 ;
    @FXML public Button btnEmne4 ;
    public ArrayList<Button> btns = new ArrayList<>();

    @FXML protected void initialize() {
        btns.add(btnEmne1);
        btns.add(btnEmne2);
        btns.add(btnEmne3);
        btns.add(btnEmne4);
        showButtons();

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
            status.setText("|Allerede meldt opp!");
        }
        else{
            if(Database.addRolle(txtEmne.getText(),UserManager._bruker, "student")){
                status.setText(UserManager._bruker+" meldt opp i "+txtEmne.getText());

            }else{
                status.setText("|Klarte ikke legge til!");
            }
        }
        UserManager.updateRoller();
        showButtons();
    }
    //TODO: Sette aktivt emne i UM og gå videre til booking side
    @FXML protected void setAktivtEmne(ActionEvent event) throws Exception{
        String text = ((Button)event.getSource()).getText();
        UserManager._aktivtEmne = text.substring(0,7);
        UserManager._aktivRolle = text.substring(9);
        openScene();

    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(btnEmne1);

    }


    @FXML protected void openScene() throws Exception {
        Parent root;
        Stage stage = (Stage) btnEmne1.getScene().getWindow();
        if (UserManager._aktivRolle.equals("faglærer")){
            root = FXMLLoader.load(getClass().getResource("faglaerer.fxml"));
            stage.setTitle("Faglærer");
        }
        else if (UserManager._aktivRolle.equals("studass")){
            root = FXMLLoader.load(getClass().getResource("studass.fxml"));
            stage.setTitle("Studass");
        }
        else {
            root = FXMLLoader.load(getClass().getResource("student.fxml"));
            stage.setTitle("Booking");
        }
        Scene scene =  new Scene(root, 700 ,500);
        stage.setScene(scene);
        stage.show();
    }

}


