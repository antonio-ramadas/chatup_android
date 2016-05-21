package antonio.chatup.screens.Room;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import antonio.chatup.R;
import antonio.chatup.data.Message;
import antonio.chatup.data.Room;
import antonio.chatup.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link ChatFragment.OnListFragmentInteractionListener}.
 */
public class MyChatRecyclerViewAdapter extends RecyclerView.Adapter<MyChatRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Message> messages;
    private final ChatFragment.OnListFragmentInteractionListener mListener;

    public MyChatRecyclerViewAdapter(Room room, ChatFragment.OnListFragmentInteractionListener listener) {
        if (room == null) {
            this.messages = new ArrayList<Message>();
        } else {
            this.messages = room.getMessages();
        }
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = messages.get(position);

        String senderInfo;

        if (messages.get(position).getSender() != null) {
            senderInfo = messages.get(position).getSender() + "@" + messages.get(position).getDate();
        } else {
            senderInfo = messages.get(position).getDate().toString();
            holder.layout.setBackgroundResource(R.drawable.message_sent);
        }

        holder.mUserView.setText(senderInfo);
        holder.mTextView.setText(messages.get(position).getMsg());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUserView;
        public final TextView mTextView;
        public Message mItem;
        LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUserView = (TextView) view.findViewById(R.id.userInfo);
            mTextView = (TextView) view.findViewById(R.id.chatMessage);
            layout = ((LinearLayout) view.findViewById(R.id.background_shape_layout));
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mUserView.getText() + "'";
        }
    }
}
