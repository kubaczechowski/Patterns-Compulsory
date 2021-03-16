package sample.GUI.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import sample.BE.Message;
import sample.GUI.Model.MessageModel;
import sample.GUI.util.Animation;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private MessageModel messageModel = new MessageModel();

    @FXML
    private  VBox vBox;
    @FXML
    private TextField textField;
    @FXML
    private JFXButton button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initView();
        loadMessages();
    }

    private void loadMessages() {
        List<Message> messageList = messageModel.getAllMessages();
        if(messageList !=null){
            for (Message m: messageList)
                vBox.getChildren().add(MessageObject.getMessageLabel(m));
        }
    }

    static class MessageObject{
        static Label  getMessageLabel(Message message){
            Label label = new Label(message.getMessage());
            label.setId("label");
            return label;
        }
    }


    /**
     * method initializes the view of a GUI
     */
    private void initView() {
        vBox.setId("vBox");
        textField.setId("textField");
        button.setId("button");
        button.setText("Send");
        textField.setPromptText("Your message here ...");
        textField.setStyle("-fx-font-size: 20px;");
    }

    /**
     * when button is clicked and the input is other than null
     * the message is showed above and saved into DB
     * @param actionEvent
     */
    public void ButtonOnAction(ActionEvent actionEvent) {
        if(!textField.getText().isEmpty()){
            Message message = new Message(textField.getText());
            messageModel.addMessage(message);
            vBox.getChildren().add(MessageObject.getMessageLabel(message));
            textField.clear();
        }
        else
            Animation.shakeNodeAnimation(textField);
    }
}
