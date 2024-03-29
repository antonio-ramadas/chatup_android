package antonio.chatup.data;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Antonio on 07-05-2016.
 */
public class Message implements Comparable<Message>, Serializable {

    /**
     * Date of the message
     */
    private Date date;

    /**
     * Text of the message
     */
    private String msg;

    /**
     * E-mail of the sender
     */
    private String sender;

    public Message(long timestamp, String msg, String email) {
        date = new Date(timestamp);
        this.msg = msg;
        this.sender = email;
    }

    public Date getDate() {
        return date;
    }

    public String getMsg() {
        return msg;
    }

    public String getSender() {
        return sender;
    }

    @Override
    public int compareTo(Message another) {
        return date.compareTo(another.date);
    }
}
