package antonio.chatup.data;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Antonio on 14-05-2016.
 */
public class Global extends Application {

    String userEmail, userToken;
    Context context;
    Map<Integer, Room> rooms = new HashMap<Integer, Room>();
    List<MyRunnable> connection_threads = new ArrayList<MyRunnable>();

    public void set(String email, String token, Context context) {
        userEmail = email;
        userToken = token;
        this.context = context;
    }

    public void addRoom(Room room) {
        rooms.put(room.getId(), room);
        MyRunnable thread = new MyRunnable(room, context);

        connection_threads.add(thread);
        new Thread(thread).start();
    }

    public void kill() {
        for (MyRunnable thr : connection_threads) {
            thr.kill();
        }
    }
}
