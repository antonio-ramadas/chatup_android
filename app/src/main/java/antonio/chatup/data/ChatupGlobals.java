package antonio.chatup.data;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 14-05-2016.
 */
public class ChatupGlobals extends Application {

    String userEmail, userToken;
    Context context;
    public static List<Room> ROOMS = new ArrayList<Room>();
    List<MyRunnable> connection_threads = new ArrayList<MyRunnable>();

    public void set(String email, String token, Context context) {
        userEmail = email;
        userToken = token;
        ROOMS.add(new Room(0, "", "teste", Room.PRIVATE_ROOM, 5));
        this.context = context;
    }

    public void addRoom(Room room) {
        getRooms().add(room);
        MyRunnable thread = new MyRunnable(room, context);

        connection_threads.add(thread);
        new Thread(thread).start();
    }

    public void kill() {
        for (MyRunnable thr : connection_threads) {
            thr.kill();
        }
    }

    public List<Room> getRooms() {
        return ROOMS;
    }
}
