package sample.BLL;

import sample.BE.Message;
import sample.DAL.IMessageTable;
import sample.DAL.MessageTableDAO;

import java.util.List;

/**
 * @author Kuba
 * @date 3/16/2021 12:01 PM
 */
public class MessageManager {
    private IMessageTable messageTable = new MessageTableDAO();

    List<Message> getAllMessages() {
        return messageTable.getAllMessages();
    }

    void saveMessage(Message message){
        messageTable.saveMessage(message);
    }
}
