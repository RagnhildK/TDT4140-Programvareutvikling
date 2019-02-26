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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

    @FXML protected void initialize() {
        lblBrukernavn.setText(UserManager._bruker);
        btns.add(btnEmne1);
        btns.add(btnEmne2);
        btns.add(btnEmne3);
        btns.add(btnEmne4);
        showButtons();
        showTid();
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
        else{
            if(Database.addRolle(txtEmne.getText(),UserManager._bruker, "student")){
                lblStatus.setText(UserManager._bruker+" meldt opp i "+txtEmne.getText());

            }else{
                lblStatus.setText("|Klarte ikke legge til!");
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

    protected void showTid() {

        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getBooking(UserManager._bruker);
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
        lblTid.setText(str);
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
            root = FXMLLoader.load(getClass().getResource("student1.fxml"));
            stage.setTitle("Booking");
        }
        Scene scene =  new Scene(root, 700 ,500);
        stage.setScene(scene);
        stage.show();
    }

}


