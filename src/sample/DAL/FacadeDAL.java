package sample.DAL;

import sample.BE.Message;
import sample.BLL.Facade;
import sample.DAL.STAX.StaxParserWriter;
import sample.DAL.STAX.StaxWriter;
import sample.DAL.database.MessageDAO;
import sample.DAL.JAXB.Files;
import sample.DAL.STAX.StaxParser;
import java.util.List;

/**
 * @author Kuba
 * @date 3/16/2021 6:00 PM
 */
public class FacadeDAL implements IFacadeDAL{
    private static FacadeDAL facadeDAL;
    private IOPERATIONS messageTable = new MessageDAO();
    private IADDMessage file = new Files();
    private IOPERATIONS stax = new StaxParserWriter();

    private static StorageType storageType;

    public static FacadeDAL createOrGetInstance(StorageType type) {
        storageType = type;

        if (facadeDAL == null) {
            facadeDAL = new FacadeDAL();
        }
        return facadeDAL;
    }

    private FacadeDAL() {
    }


    @Override
    public void saveMessage(Message msg) {
        switch (storageType){
            case STAX -> stax.saveMessage(msg);
            case DATABASE -> messageTable.saveMessage(msg);
        }
    }

    @Override
    public List<Message> getAllMessages() {
        switch (storageType){
            case STAX: return stax.getAllMessages();
            case DATABASE: return messageTable.getAllMessages();
        }
        // not valid storage type inserted
      return null;
    }

}
