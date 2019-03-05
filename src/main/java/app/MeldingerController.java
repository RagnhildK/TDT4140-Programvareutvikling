package app;


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
    @FXML public TextArea txtMelding;
    @FXML public ScrollPane scrollPane;
    @FXML public Label lblMeldinger;

    
    public void Send(){
        String meldinger = lblMeldinger.getText();
        meldinger += "Me: " + txtMelding.getText() + "\n";
        lblMeldinger.setText(meldinger);

        Database.addMelding(UserManager._bruker, "bob",txtMelding.getText());
    }


}
