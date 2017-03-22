package rocks.ecox.dailyprogrammerchallenges.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rocks.ecox.dailyprogrammerchallenges.activities.DetailActivity;
import rocks.ecox.dailyprogrammerchallenges.R;
import rocks.ecox.dailyprogrammerchallenges.adapters.ChallengeCursorAdapter;
import rocks.ecox.dailyprogrammerchallenges.data.DPChallengesContract;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {
    // LoaderManager.LoaderCallbacks<Object>
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_CHALLENGE_POSITION = "challenge_position";
    private static final String ARG_CHALLENGE_ID = "db_id";
    ChallengeCursorAdapter mAdapter;

    public DetailFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DetailFragment newInstance(int position, String dbId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CHALLENGE_POSITION, position);
        args.putString(ARG_CHALLENGE_ID, dbId);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        String rowId = getArguments().getString(ARG_CHALLENGE_ID);
        mAdapter = new ChallengeCursorAdapter(getContext());
        Uri challenge = DPChallengesContract.ChallengeEntry.CONTENT_URI.buildUpon().appendPath(rowId).build();
        String title = "";
        String description = "";
        String author = "xxx";
        TextView detailChallengeTitle = (TextView) rootView.findViewById(R.id.detailChallengeTitle);
        TextView detailChallengeDescription = (TextView) rootView.findViewById(R.id.detailChallengeDescription);
        TextView detailChallengeAuthor = (TextView) rootView.findViewById(R.id.detailChallengeAuthor);

        Cursor mCursor = DetailActivity.getContextOfApplication().getContentResolver().query(challenge,
                null,
                null,
                null,
                null);

        try {
            mCursor.moveToPosition(Integer.parseInt(rowId) - 1);
            title = mCursor.getString(mCursor.getColumnIndex(DPChallengesContract.ChallengeEntry.COLUMN_TITLE));
            description = mCursor.getString(mCursor.getColumnIndex(DPChallengesContract.ChallengeEntry.COLUMN_DESCRIPTION_HTML));
            author = mCursor.getString(mCursor.getColumnIndex(DPChallengesContract.ChallengeEntry.COLUMN_AUTHOR));
            Timber.d("DATA: %s | %s | %s", rowId, title, description);
            mCursor.close();
        } catch (NullPointerException e) {
            Timber.e("Cursor error", e);
        }

        detailChallengeTitle.setText(title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            detailChallengeDescription.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
        } else {
            detailChallengeDescription.setText(Html.fromHtml(description));
        }

        detailChallengeAuthor.setText(String.format("%s%s", getString(R.string.author_label), author));

        return rootView;
    }

}
