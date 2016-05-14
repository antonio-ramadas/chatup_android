package antonio.chatup.data;

/**
 * Created by Antonio on 14-05-2016.
 */
public class MyRunnable implements Runnable {

    int id;
    boolean alive = true;

    MyRunnable(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (alive) {
            //TODO update room
            //lock
            //check
            //update if necessary
            //unlock
        }
    }

    public void kill() {
        alive = false;
    }
}
