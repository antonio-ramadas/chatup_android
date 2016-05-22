package antonio.chatup.screens.ViewRooms;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import antonio.chatup.ItemFragment.OnListFragmentInteractionListener;
import antonio.chatup.R;
import antonio.chatup.data.ChatupGlobals;
import antonio.chatup.data.Room;
import antonio.chatup.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link RoomFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRoomRecyclerViewAdapter extends RecyclerView.Adapter<MyRoomRecyclerViewAdapter.ViewHolder> {

    private final RoomFragment.OnListFragmentInteractionListener mListener;
    public FragmentManager fragmentManager;

    public MyRoomRecyclerViewAdapter(RoomFragment.OnListFragmentInteractionListener listener, FragmentManager fragmentManager) {
        mListener = listener;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Room room = ChatupGlobals.ROOMS.get(position);
        holder.mRoom = room;

        if (room.isPrivate())
            holder.mRoomTypeView.setImageResource(android.R.drawable.ic_secure);
        else
            holder.mRoomTypeView.setImageResource(android.R.drawable.ic_partial_secure);

        holder.mUsersView.setText(room.getNumUsers() + "");
        holder.mRoomNameView.setText(room.getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mRoom);
                }

                DialogAccessRoomFragment dialog = new DialogAccessRoomFragment();

                Bundle args = new Bundle();
                args.putSerializable("room", ChatupGlobals.ROOMS.get(position));
                dialog.setArguments(args);

                dialog.show(fragmentManager, "accessRoomDialogTag");
            }
        });
    }

    @Override
    public int getItemCount() {
        return ChatupGlobals.ROOMS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mRoomTypeView;
        public final TextView mUsersView;
        public final TextView mRoomNameView;
        public Room mRoom;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mRoomTypeView = (ImageView) view.findViewById(R.id.roomType);
            mUsersView = (TextView) view.findViewById(R.id.users);
            mRoomNameView = (TextView) view.findViewById(R.id.roomName);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mRoomNameView.getText() + "'";
        }
    }
}
