package sample.DAL.STAX;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import sample.BE.Message;
import sample.DAL.IADDMessage;
import sample.DAL.IOPERATIONS;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


/**
 * @author Kuba
 * @date 3/18/2021 6:28 PM
 */
public class StaxParserWriter implements IOPERATIONS {

    private static final String  MESSAGE = "message";
    private static final String configFile = "config2.xml";


    /**
     * before first run we had to config
     * method creates initial file or if file exists
     * creates another content
     * @throws Exception
     */
    public void saveConfig() throws Exception {
        // create an XMLOutputFactory
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        // create XMLEventWriter
        XMLEventWriter eventWriter = outputFactory
                .createXMLEventWriter(new FileOutputStream(configFile));
        // create an EventFactory
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        // create and write Start Tag
        StartDocument startDocument = eventFactory.createStartDocument();
        eventWriter.add(startDocument);

        // create config open tag
        StartElement configStartElement = eventFactory.createStartElement("",
                "", "config");
        eventWriter.add(configStartElement);
        eventWriter.add(end);
        // Write the different nodes
        createNode(eventWriter, "message", "buy bread");
        createNode(eventWriter, "message", "don't forget about eggs");
        createNode(eventWriter, "message", "what about butter");
        createNode(eventWriter, "message", "buy milk too");

        eventWriter.add(eventFactory.createEndElement("", "", "config"));
        eventWriter.add(end);
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();
    }

    public void saveMessage(Message message) {
        try {

            Path in = Paths.get(configFile);
            Path temp = Files.createTempFile(null, null);
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            try (FileWriter out = new FileWriter(temp.toFile())) {
                XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(new FileReader(in.toFile()));
                XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(out);
                int depth = 0;
                while (reader.hasNext()) {
                    XMLEvent event = reader.nextEvent();
                    int eventType = event.getEventType();
                    if (eventType == XMLStreamConstants.START_ELEMENT) {
                        depth++;
                    } else if (eventType == XMLStreamConstants.END_ELEMENT) {
                        depth--;
                        if (depth == 0) {
                            List<Attribute> attrs = new ArrayList<>(1);
                            //  attrs.add(eventFactory.createAttribute("id", "4"));
                            writer.add(eventFactory.createStartElement("", null, "message", attrs.iterator(), null));
                            writer.add(eventFactory.createCharacters(message.getMessage().trim()));
                            writer.add(eventFactory.createEndElement("", null, "message"));
                            writer.add(eventFactory.createSpace(System.getProperty("line.separator")));
                        }
                    }
                    writer.add(event);
                }
                writer.flush();
                writer.close();
            } catch (XMLStreamException | FactoryConfigurationError e) {
                try {
                    throw new IOException(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            Files.move(temp, in, StandardCopyOption.REPLACE_EXISTING);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    private void createNode(XMLEventWriter eventWriter, String name,
                            String value) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // create Start node
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        // create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);

    }

    @SuppressWarnings({ "unchecked", "null" })
    public List<Message> getAllMessages() {
        List<Message> items = new ArrayList<>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    String elementName = startElement.getName().getLocalPart();
                    if(elementName.equals(MESSAGE)){
                        Message message = new Message();
                        event = eventReader.nextEvent();
                        message.setMessage(event.asCharacters().getData());
                        System.out.println(message.toString());
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
