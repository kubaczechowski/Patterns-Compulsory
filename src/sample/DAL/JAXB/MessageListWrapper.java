package sample.DAL.JAXB;

import sample.BE.Message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @XmlRootElement defines the name of the root element.
 * @XmlElement is an optional name we can specify for the element.
 *
 * @author Kuba
 * @date 3/16/2021 6:16 PM
 */


//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "messages")
public class MessageListWrapper {


    private List<Message> messageList;

    @XmlElement(name = "message")
    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public void saveMessage(Message message){
        if(messageList == null)
            messageList = new ArrayList<>();

        messageList.add(message);

    }
}
