package sample.DAL;

import sample.BE.Message;
import sample.DAL.database.MessageTableDAO;
import sample.DAL.file.Files;

import java.util.List;

/**
 * @author Kuba
 * @date 3/16/2021 6:00 PM
 */
public class FacadeDAL implements IFacadeDAL{
    private IMessageTable messageTable = new MessageTableDAO();
    private IMessageTable file = new Files();

    @Override
    public void logMessage(Message msg) {
        file.saveMessage(msg);
    }

    @Override
    public List<Message> getAllMessages() {
        return file.getAllMessages();
    }

}
