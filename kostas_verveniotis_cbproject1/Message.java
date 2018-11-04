package kostas_verveniotis_cbproject1;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/**
 *
 * @author krocos
 */
public class Message {

    private int id;
    private String messageData;
    private int idFrom;
    private int idTo;
    private String sender;
    private String receiver;
    private Timestamp date;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

    public Message() {
    }

    public Message(String messageData, int idFrom, int idTo, Timestamp date) {
        this.setMessageData(messageData);
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageData() {
        return messageData;
    }

    public void setMessageData(String messageData) {
        messageData = Helper.checkMessageLength(messageData);
        this.messageData = messageData;
    }

    public int getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(int idFrom) {
        this.idFrom = idFrom;
    }

    public int getIdTo() {
        return idTo;
    }

    public void setIdTo(int idTo) {
        this.idTo = idTo;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message ID: " + this.id + "\n"
                + "From User : " + this.idFrom + "\n"
                + "To   User : " + this.idTo + "\n"
                + "Sender : " + this.sender + "\n"
                + "Receiver : " + this.receiver + "\n"
                + "Message   : " + this.messageData + "\n"
                + "Date      : " + sdf.format(this.date);
    }

}
