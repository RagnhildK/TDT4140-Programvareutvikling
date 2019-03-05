package app;


import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextArea;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.*;

import javafx.fxml.FXML;

public class MeldingerController {

    @FXML public Button btnSend;
    @FXML public Button btnBack;
    @FXML public Button btnLoggut;
    @FXML public JFXTextArea txtMelding;
    @FXML public Label lblMeldinger;
    @FXML public Label lblStatus;
    @FXML public JFXScrollPane scrollPane;

    
    public void sendMsg(ActionEvent event){
        String meldinger;
        try {
            meldinger = lblMeldinger.getText();
        }catch(Exception e){
            meldinger = "";
        }

        meldinger += "Me: " + txtMelding.getText() + "\n";
        lblMeldinger.setText(meldinger);

        Database.addMelding(UserManager._bruker, "bob",lblMeldinger.getText());
    }
    public void addChat(ActionEvent event){
        lblMeldinger.setText(Database.getMeldinger("bob","alice").get(0).toString());
    }
    @FXML protected void back(ActionEvent event) throws Exception {
        EmneController ec = new EmneController();
        ec.back(btnBack);

    }

    @FXML protected void logout(ActionEvent event) throws Exception {
        LoginController l = new LoginController();
        l.logout(btnLoggut);

    }


}
