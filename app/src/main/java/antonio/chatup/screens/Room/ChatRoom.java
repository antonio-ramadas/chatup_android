package antonio.chatup.screens.Room;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import antonio.chatup.R;
import antonio.chatup.data.Message;
import antonio.chatup.data.Room;
import antonio.chatup.screens.ViewRooms.MainActivity;

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

        ImageButton informationButton = (ImageButton) findViewById(R.id.room_information_button);
        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RoomInfoActivity.class);
                startActivity(i);
            }
        });

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Room room = new Room(1, "ip:port","Teste", Room.PRIVATE_ROOM, 0);
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
