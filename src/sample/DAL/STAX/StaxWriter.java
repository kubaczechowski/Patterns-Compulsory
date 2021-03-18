package sample.DAL.STAX;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/**
 *  class is responsible for adding new records
 * @author Kuba
 * @date 3/18/2021 4:16 PM
 */


public class StaxWriter {
    private String configFile;

    public void setFile(String configFile) {
        this.configFile = configFile;
    }

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


    public static void addElementToXML(String value){

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        Document doc = null;
        try {
            String filePath = "config2.xml";
            db = dbf.newDocumentBuilder();
            doc = db.parse(new File(filePath));
            NodeList ndListe = doc.getElementsByTagName("message");


            String newXMLLine ="<message>"+ value+"</message>";

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

}

/**
 * Used for creating intial configuration
 *    StaxWriter configFile = new StaxWriter();
 *             configFile.setFile("config2.xml");
 *            try {
 *                configFile.saveConfig();
 *             } catch (Exception e) {
 *                 e.printStackTrace();
 *             }
 *
 *       */
