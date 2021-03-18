package sample.DAL;

import sample.BE.Message;

import java.util.List;

/**
 * @author Kuba
 * @date 3/18/2021 4:44 PM
 */
public interface IGetAllMessages {
    List<Message> getAllMessages();
}
