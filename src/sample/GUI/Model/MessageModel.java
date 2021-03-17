package sample.GUI.Model;

import javafx.collections.FXCollections;
import sample.BE.Message;
import sample.BLL.Facade;
import sample.BLL.IMechaChatLogicFacade;
import sample.BLL.MessageManager;

import java.util.List;

/**
 * @author Kuba
 * @date 3/16/2021 12:00 PM
 */
public class MessageModel {
    private IMechaChatLogicFacade facade =
            Facade.createOrGetInstance();

    List<Message> observableMessages = FXCollections.observableArrayList();

    public void addMessage(Message message) {
        observableMessages.add(message);
        facade.logMessage(message);
    }

    public List<Message> getAllMessages() {
        return facade.getAllMessages();
    }
}
