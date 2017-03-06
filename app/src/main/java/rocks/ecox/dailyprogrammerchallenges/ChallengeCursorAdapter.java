package rocks.ecox.dailyprogrammerchallenges;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rocks.ecox.dailyprogrammerchallenges.data.DPChallengesContract;

public class ChallengeCursorAdapter extends RecyclerView.Adapter<ChallengeCursorAdapter.ChallengeViewHolder> {

    // Class variables for the Cursor that holds challenge data and the Context
    private Cursor mCursor;
    private Context mContext;

    public ChallengeCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new ChallengeViewHolder that holds the view for each challenge
     */
    @Override
    public ChallengeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the card_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.card_layout, parent, false);
        final ChallengeViewHolder cvh = new ChallengeViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = cvh.getAdapterPosition();
                int idIndex = mCursor.getColumnIndex(DPChallengesContract.ChallengeEntry._ID);
                mCursor.moveToPosition(position);
                final String dbId = mCursor.getString(idIndex);

                Intent intent = new Intent(MainActivity.contextOfApplication, DetailActivity.class);
                intent.putExtra("EXTRA_DB_ID", dbId);
                mContext.startActivity(intent);
            }
        });

        return cvh;
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(ChallengeViewHolder holder, int position) {

        // Indices for the card view columns
        int idIndex = mCursor.getColumnIndex(DPChallengesContract.ChallengeEntry._ID);
        int titleIndex = mCursor.getColumnIndex(DPChallengesContract.ChallengeEntry.COLUMN_TITLE);
        int descriptionIndex = mCursor.getColumnIndex(DPChallengesContract.ChallengeEntry.COLUMN_DESCRIPTION_HTML);
        int numberIndex = mCursor.getColumnIndex(DPChallengesContract.ChallengeEntry.COLUMN_CHALLENGE_NUM);
        int difficultyIndex = mCursor.getColumnIndex(DPChallengesContract.ChallengeEntry.COLUMN_DIFFICULTY);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String title = mCursor.getString(titleIndex);
        String description = mCursor.getString(descriptionIndex);
        String num = mCursor.getString(numberIndex);
        String difficulty = mCursor.getString(difficultyIndex);

        //Set values
        holder.itemView.setTag(id);
        holder.challengeTitleView.setText(title);

        if(!description.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.challengeDescriptionView.setText(Html.fromHtml(description.replaceFirst("Description", ""), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.challengeDescriptionView.setText(Html.fromHtml(description.replaceFirst("Description", "")));
            }
        }

        holder.challengeNumberView.setText("#" + num);
        holder.challengeDifficultyView.setText(difficulty);

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

        // Class variables for the challenge description TextView
        CardView cv;
        TextView challengeTitleView;
        TextView challengeDescriptionView;
        TextView challengeNumberView;
        TextView challengeDifficultyView;

        /**
         * Constructor for the ChallengeViewHolder
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public ChallengeViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            challengeTitleView = (TextView) itemView.findViewById(R.id.challengeTitle);
            challengeDescriptionView = (TextView) itemView.findViewById(R.id.challengeDescription);
            challengeNumberView = (TextView) itemView.findViewById(R.id.challengeNumber);
            challengeDifficultyView = (TextView) itemView.findViewById(R.id.challengeDifficulty);
        }
    }
}