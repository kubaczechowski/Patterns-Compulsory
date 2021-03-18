package sample.BE;


/**
 * @author Kuba
 * @date 3/16/2021 11:18 AM
 */


public class Message {


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

    @Override
    public String toString() {
        return message;
    }
}
