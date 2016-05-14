package antonio.chatup.data;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;

import antonio.chatup.R;
import antonio.chatup.screens.ViewRooms.MainActivity;

/**
 * Created by Antonio on 14-05-2016.
 */
public class MyRunnable implements Runnable {

    boolean alive = true;
    Room room;
    Context context;

    MyRunnable(Room room, Context context) {
        this.room = room;
        this.context = context;
    }

    @Override
    public void run() {
        while (alive) {
            //TODO update room
            //http://developer.android.com/training/volley/simple.html#simple
            //lock
                //check
                //update if necessary
                //play sound if new messages have been received
            //release
        }
    }

    private void playSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.message_received);
        mediaPlayer.start();
    }

    public void kill() {
        alive = false;
    }
}
