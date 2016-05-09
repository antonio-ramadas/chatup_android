package antonio.chatup.screens;

import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import antonio.chatup.ItemFragment.OnListFragmentInteractionListener;
import antonio.chatup.R;
import antonio.chatup.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link RoomFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRoomRecyclerViewAdapter extends RecyclerView.Adapter<MyRoomRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final RoomFragment.OnListFragmentInteractionListener mListener;
    public FragmentManager fragmentManager;

    public MyRoomRecyclerViewAdapter(List<DummyItem> items, RoomFragment.OnListFragmentInteractionListener listener, FragmentManager fragmentManager) {
        mValues = items;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mRoomTypeView.setImageResource(android.R.drawable.ic_secure);
        holder.mUsersView.setText(mValues.get(position).id);
        holder.mRoomNameView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }

                new DialogAccessRoomFragment().show(fragmentManager, "createRoomDialogTag");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mRoomTypeView;
        public final TextView mUsersView;
        public final TextView mRoomNameView;
        public DummyItem mItem;

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
