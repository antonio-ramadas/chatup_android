package antonio.chatup.screens.Room;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import java.security.Timestamp;
import java.util.Date;

import antonio.chatup.R;
import antonio.chatup.data.Message;
import antonio.chatup.data.Room;

public class ChatRoom extends AppCompatActivity implements ChatFragment.OnListFragmentInteractionListener {

    ChatFragment chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Bundle extras = getIntent().getExtras();
        TextView title = (TextView) findViewById(R.id.roomTitle);
        title.setText(extras.getString(String.valueOf(R.string.room_title_param)));

        ImageButton sendButton = (ImageButton) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Room room = new Room("Teste", Room.PRIVATE_ROOM);
            //Timestamp timestamp, String msg, String email
            Message message = new Message(System.currentTimeMillis(), "Message", "test@gmail.com");
            room.addMsg(message);
            chat = ChatFragment.newInstance(room);

            //fragmentTransaction.add(R.id.chatFragment, rf);
            fragmentTransaction.add(R.id.fragmentChatRoom, chat);

            fragmentTransaction.commit();
        }
    }


    @Override
    public void onListFragmentInteraction(Message message) {

    }

}
