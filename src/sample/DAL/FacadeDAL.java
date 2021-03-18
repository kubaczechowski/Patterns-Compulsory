package sample.DAL;

import sample.BE.Message;
import sample.DAL.database.MessageTableDAO;
import sample.DAL.file.Files;
import sample.DAL.file.StaxParser;
import sample.DAL.file.StaxWriter;

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
        //file.saveMessage(msg);
    }

    @Override
    public List<Message> getAllMessages() {

        StaxParser read = new StaxParser();
        List<Message> readConfig = read.
                readConfig("config2.xml");

        for (Message m: readConfig
             ) {
            System.out.println("and now: "+ m.toString());
        }

        return readConfig;
    }

}
