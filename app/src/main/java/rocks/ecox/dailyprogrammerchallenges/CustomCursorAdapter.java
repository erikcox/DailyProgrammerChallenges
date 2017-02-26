package rocks.ecox.dailyprogrammerchallenges;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rocks.ecox.dailyprogrammerchallenges.data.DPChallengesContract;

public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.ChallengeViewHolder> {

    // Class variables for the Cursor that holds challenge data and the Context
    private Cursor mCursor;
    private Context mContext;

    public CustomCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new ChallengeViewHolder that holds the view for each challenge
     */
    @Override
    public ChallengeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the challenge_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.challenge_layout, parent, false);

        return new ChallengeViewHolder(view);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(ChallengeViewHolder holder, int position) {

        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(DPChallengesContract.ChallengeEntry._ID);
        int descriptionIndex = mCursor.getColumnIndex(DPChallengesContract.ChallengeEntry.COLUMN_DESCRIPTION);
        int priorityIndex = mCursor.getColumnIndex(DPChallengesContract.ChallengeEntry.COLUMN_DIFFICULTY);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String description = mCursor.getString(descriptionIndex);
        int priority = mCursor.getInt(priorityIndex);

        //Set values
        holder.itemView.setTag(id);
        holder.challengeDescriptionView.setText(description);

    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    // Inner class for creating ViewHolders
    class ChallengeViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the challenge description and priority TextViews
        TextView challengeDescriptionView;
        TextView priorityView;

        /**
         * Constructor for the ChallengeViewHolder
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public ChallengeViewHolder(View itemView) {
            super(itemView);

            challengeDescriptionView = (TextView) itemView.findViewById(R.id.challengeDescription);
            priorityView = (TextView) itemView.findViewById(R.id.priorityTextView);
        }
    }
}