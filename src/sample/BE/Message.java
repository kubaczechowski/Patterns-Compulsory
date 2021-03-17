package sample.BE;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Kuba
 * @date 3/16/2021 11:18 AM
 */

@XmlRootElement
public class Message {

    @XmlElement
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }
}
