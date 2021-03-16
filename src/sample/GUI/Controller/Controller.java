package sample.GUI.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private  VBox vBox;
    @FXML
    private TextField textField;
    @FXML
    private JFXButton button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    private void init() {
        vBox.setId("vBox");
        textField.setId("textField");
        button.setId("button");
        button.setText("Send");
        textField.setPromptText("Your message here ...");
        textField.setStyle("-fx-font-size: 20px;");
    }
}
