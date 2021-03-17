package sample.BLL;

import sample.BE.Message;
import sample.DAL.FacadeDAL;
import sample.DAL.IFacadeDAL;
import sample.DAL.IMessageTable;
import sample.DAL.database.MessageTableDAO;

import java.util.List;

/**
 * @author Kuba
 * @date 3/16/2021 12:01 PM
 */
public class MessageManager {
    private IFacadeDAL facadeDAL = new FacadeDAL();

    List<Message> getAllMessages() {
        return facadeDAL.getAllMessages();
    }

    void saveMessage(Message message){
        facadeDAL.logMessage(message);
    }
}
