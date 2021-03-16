package sample.BLL;

import sample.BE.Message;

import java.util.List;

/**
 * @author Kuba
 * @date 3/16/2021 12:11 PM
 */
public class Facade implements IMechaChatLogicFacade{
    private MessageManager messageManager = new MessageManager();
    private static Facade facade;

    public static Facade createOrGetInstance() {
        if (facade == null) {
            facade = new Facade();
        }
        return facade;
    }

    private Facade() {
    }

    @Override
    public void logMessage(Message msg) {
        messageManager.saveMessage(msg);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageManager.getAllMessages();
    }
}
