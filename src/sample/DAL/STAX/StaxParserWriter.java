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


    public void saveMessage(Message message){

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        Document doc = null;
        try {
            String filePath = "config2.xml";
            db = dbf.newDocumentBuilder();
            doc = db.parse(new File(filePath));
            NodeList ndListe = doc.getElementsByTagName("message");


            String newXMLLine ="<message>"+ message.getMessage()+"</message>";

            Node nodeToImport = db.parse(new InputSource(new StringReader(newXMLLine))).getElementsByTagName("message").item(0);

            ndListe.item(ndListe.getLength()-1).getParentNode().appendChild(doc.importNode(nodeToImport, true));

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new StringWriter());

            transformer.transform(source, result);
            Writer output = new BufferedWriter(new FileWriter(filePath));
            String xmlOutput = result.getWriter().toString();
            output.write(xmlOutput);
            output.close();


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
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
