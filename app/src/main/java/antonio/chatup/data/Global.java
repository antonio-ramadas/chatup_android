package antonio.chatup.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 14-05-2016.
 */
public class Global {
    private static Global ourInstance = new Global();

    public static Global getInstance() {
        return ourInstance;
    }

    private Global() {
    }

    String userEmail, userToken;
    List<Room> rooms = new ArrayList<>();
    List<MyRunnable> connection_threads = new ArrayList<MyRunnable>();

    public void set(String email, String token) {
        userEmail = email;
        userToken = token;
    }

    public void addRoom(Room room) {
        rooms.add(room);
        MyRunnable thread = new MyRunnable(room.getId());

        connection_threads.add(thread);
        new Thread(thread).start();
    }

    public void kill() {
        for (MyRunnable thr : connection_threads) {
            thr.kill();
        }
    }
}
