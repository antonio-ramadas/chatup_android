package antonio.chatup.screens;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import antonio.chatup.screens.ChatFragment.OnListFragmentInteractionListener;
import antonio.chatup.R;
import antonio.chatup.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyChatRecyclerViewAdapter extends RecyclerView.Adapter<MyChatRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyChatRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
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
        holder.mItem = mValues.get(position);
        //TODO clean this up (just to fill with information for debug)
        holder.mUserView.setText(mValues.get(position).id+"@gmail.com@09-05-2016 17:59");
        String text = ""; int random = (int) (Math.random() * 20);
        for (int i = 0; i < random; i++) {
            text += " test";
        }
        holder.mMessageView.setText(mValues.get(position).content + text);

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
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUserView;
        public final TextView mMessageView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUserView = (TextView) view.findViewById(R.id.userInfo);
            mMessageView = (TextView) view.findViewById(R.id.chatMessage);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMessageView.getText() + "'";
        }
    }
}
