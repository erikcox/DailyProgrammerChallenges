package rocks.ecox.dailyprogrammerchallenges;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import rocks.ecox.dailyprogrammerchallenges.data.DPChallengesContract;
import timber.log.Timber;

import static com.activeandroid.Cache.getContext;

public class CompletedActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ChallengeCursorAdapter mAdapter;
    protected RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        mAdapter = new ChallengeCursorAdapter(getContext());
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewCompleted);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(final int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(getContext()) {

            // Initialize a Cursor, this will hold all the challenge data
            Cursor mChallengeData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mChallengeData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mChallengeData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Query and load challenge data
                String sortQuery = "completed_challenge = 1"; // TODO: Add ability to sort favorite by newest/oldest?

                try {
                    final Cursor query = getContentResolver().query(DPChallengesContract.ChallengeEntry.CONTENT_URI,
                            null,
                            sortQuery,
                            null,
                            DPChallengesContract.ChallengeEntry.COLUMN_CHALLENGE_NUM + " DESC, "
                                    + DPChallengesContract.ChallengeEntry.COLUMN_DIFFICULTY_NUM + " ASC");
                    return query;
                } catch (Exception e) {
                    Timber.e("Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mChallengeData = data;
                super.deliverResult(data);
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Cursor Object
        mAdapter.swapCursor(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Loader<Object> loader
        mAdapter.swapCursor(null);
    }

}
