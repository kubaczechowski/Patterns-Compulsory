package sample.DAL;

import sample.BE.Message;

import java.util.List;

/**
 * @author Kuba
 * @date 3/16/2021 11:36 AM
 */
public interface IMessageTable {
    List<Message> getAllMessages();
    void saveMessage(String message);
}
