package sample.DAL;

import sample.BE.Message;

import java.util.List;

/**
 * @author Kuba
 * @date 3/16/2021 11:36 AM
 */
public interface IADDMessage {
    List<Message> getAllMessages();
    void saveMessage(Message message);
}
