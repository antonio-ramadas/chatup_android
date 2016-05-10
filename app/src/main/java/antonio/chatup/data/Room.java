package antonio.chatup.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Antonio on 07-05-2016.
 */
public class Room implements Serializable {

    public static final boolean PUBLIC_ROOM = false;
    public static final boolean PRIVATE_ROOM = true;

    private ArrayList<Message> messages = new ArrayList<Message>();

    private boolean privateRoom;

    private String name;

    public Room(String name, boolean type) {
        this.name = name;
        privateRoom = type;
    }

    public boolean isPrivate() {
        return privateRoom;
    }

    public String getName() {
        return name;
    }

    /**
     * Add a message to the chat room.
     * The message is inserted in a way to keep the array ordered.
     * This way of implementation can be compared to a priority queue
     * @param msg Message to be added
     */
    public void addMsg(Message msg) {
        int index = Collections.binarySearch(getMessages(), msg);

        //TODO probably if the index is positive, then the message already exists...
        if (index < 0) {
            //https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html#binarySearch(java.util.List,%20T)
            //the index of the search key, if it is contained in the list; otherwise, (-(insertion point) - 1).
            index = -index - 1;
        }

        getMessages().add(index, msg);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
