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
        StaxWriter.addElementToXML(msg.getMessage());
    }

    @Override
    public List<Message> getAllMessages() {
       return StaxParser.readConfig("config2.xml");
    }

}
