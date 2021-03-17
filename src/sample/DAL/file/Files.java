package sample.DAL.file;
import javafx.scene.control.Alert;
import sample.BE.Message;
import sample.DAL.IMessageTable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;


/**
 * @author Kuba
 * @date 3/16/2021 5:59 PM
 */
public class Files implements IMessageTable {
    private final String PATH = "sample/data.xml";



    @Override
    public List<Message> getAllMessages() {
        try{
            JAXBContext context = JAXBContext
                    .newInstance(Message.class);
            Unmarshaller um = context.createUnmarshaller();

          //  File file =  new File(PATH);
            URL fileURL = this.getClass().getResource("/sample/data.xml");
            File file = new File(fileURL.getPath());
            MessageListWrapper wrapper = (MessageListWrapper) um.unmarshal(file);
            return wrapper.getMessageList();


        } catch (JAXBException e) {
            e.printStackTrace();
            showAlert("Could not load data",
                    "Could not load data from file:\n" + PATH);
        }
        return null;
    }

    /**
     * method shows the alert
     * later it may be moved to the util in dal
     */
    private void showAlert(String header, String ContentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(ContentText);
        alert.showAndWait();
    }

    @Override
    public void saveMessage(Message message) {
        try{
            JAXBContext context = JAXBContext
                    .newInstance(MessageListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            MessageListWrapper wrapper = new MessageListWrapper();
            wrapper.saveMessage(message);

        } catch (JAXBException e) {
            e.printStackTrace();
            showAlert("couldn't save message",
                    "Could save data to file:\n" + PATH);
        }
    }
}
