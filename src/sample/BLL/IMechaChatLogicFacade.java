package sample.BLL;

import sample.BE.Message;

import java.util.List;

/**
 * @author Kuba
 * @date 3/16/2021 11:20 AM
 */
public interface IMechaChatLogicFacade {

    void logMessage(Message msg);
    List<Message> getAllMessages();

}
