package sample.DAL.file;

import sample.BE.Message;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Another way
 * @author Kuba
 * @date 3/18/2021 4:02 PM
 */
public class StaxParser {
    private final String  MESSAGE = "message";

    @SuppressWarnings({ "unchecked", "null" })
    public List<Message> readConfig(String configFile) {
        List<Message> items = new ArrayList<>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            Message message = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    String elementName = startElement.getName().getLocalPart();
                    if(elementName.equals(MESSAGE)){
                        event = eventReader.nextEvent();
                        message.setMessage(event.asCharacters().getData());
                    }
                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(MESSAGE)) {
                        items.add(message);
                    }
                }

            }
        } catch ( XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return items;
    }

}
