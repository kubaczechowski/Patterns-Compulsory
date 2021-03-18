package sample.DAL;

import sample.BE.Message;
import sample.DAL.STAX.StaxWriter;
import sample.DAL.database.MessageDAO;
import sample.DAL.JAXB.Files;
import sample.DAL.STAX.StaxParser;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author Kuba
 * @date 3/16/2021 6:00 PM
 */
public class FacadeDAL implements IFacadeDAL{
    private IADDMessage messageTable = new MessageDAO();
    private IADDMessage file = new Files();

    @Override
    public void logMessage(Message msg) {
      /*  StaxWriter configFile = new StaxWriter();
        configFile.setFile("config2.xml");
        try {
            configFile.addNewMessage(msg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

       */
        StaxWriter.addElementToXML(msg.getMessage());
    }

    @Override
    public List<Message> getAllMessages() {
        StaxWriter configFile = new StaxWriter();
            configFile.setFile("config2.xml");
           try {
               configFile.saveConfig();
            } catch (Exception e) {
                e.printStackTrace();
            }


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
