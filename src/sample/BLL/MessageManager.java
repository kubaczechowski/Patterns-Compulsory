package sample.BLL;

import sample.BE.Message;
import sample.DAL.FacadeDAL;
import sample.DAL.IFacadeDAL;
import sample.DAL.StorageType;

import java.util.List;

/**
 * @author Kuba
 * @date 3/16/2021 12:01 PM
 */
public class MessageManager {
    private IFacadeDAL facadeDAL = FacadeDAL.createOrGetInstance(StorageType.STAX);

    List<Message> getAllMessages() {
        return facadeDAL.getAllMessages();
    }

    void saveMessage(Message message){
        facadeDAL.saveMessage(message);
    }
}
