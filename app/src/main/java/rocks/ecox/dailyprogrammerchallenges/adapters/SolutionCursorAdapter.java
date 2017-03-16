package rocks.ecox.dailyprogrammerchallenges.adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rocks.ecox.dailyprogrammerchallenges.R;
import rocks.ecox.dailyprogrammerchallenges.data.DPChallengesContract;

public class SolutionCursorAdapter extends RecyclerView.Adapter<SolutionCursorAdapter.SolutionViewHolder> {

    // Class variables for the Cursor that holds the solution data and the Context
    private Cursor mCursor;
    private Context mContext;

    public SolutionCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new SolutionViewHolder that holds the view for each challenge
     */
    @Override
    public SolutionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.solution_layout, parent, false);
        final SolutionViewHolder svh = new SolutionViewHolder(view);

        return svh;
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(SolutionViewHolder holder, int position) {

        // Indices for the card view columns
        int commentIndex = mCursor.getColumnIndex(DPChallengesContract.SolutionEntry.COLUMN_COMMENT_ID);
        int bodyIndex = mCursor.getColumnIndex(DPChallengesContract.SolutionEntry.COLUMN_BODY_HTML);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final String id = mCursor.getString(commentIndex);
        final String body = mCursor.getString(bodyIndex);

        //Set values
        holder.itemView.setTag(id);
        holder.solutionBody.setText(body);

        if (!body.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.solutionBody.setText(Html.fromHtml(body, Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.solutionBody.setText(Html.fromHtml(body));
            }
        }

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
    class SolutionViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the solution TextView
        TextView solutionBody;

        /**
         * Constructor for the SolutionViewHolder
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public SolutionViewHolder(View itemView) {
            super(itemView);
            solutionBody = (TextView) itemView.findViewById(R.id.solutionBody);
        }
    }
}