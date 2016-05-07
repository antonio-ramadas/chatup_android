package antonio.chatup.data;

import java.util.ArrayList;

/**
 * Created by Antonio on 07-05-2016.
 */
public class Data {

    ArrayList<Room> rooms = new ArrayList<Room>();

    /**
     * User's e-mail address
     */
    private String user;

    private static Data ourInstance = new Data();

    public static Data getInstance() {
        return ourInstance;
    }

    private Data() {
    }

    public void clear() {
        user = null;
        rooms = new ArrayList<Room>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
