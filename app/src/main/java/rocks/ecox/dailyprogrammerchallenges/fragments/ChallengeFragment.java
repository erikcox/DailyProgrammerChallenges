package rocks.ecox.dailyprogrammerchallenges.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rocks.ecox.dailyprogrammerchallenges.R;
import rocks.ecox.dailyprogrammerchallenges.activities.MainActivity;
import rocks.ecox.dailyprogrammerchallenges.adapters.ChallengeCursorAdapter;
import rocks.ecox.dailyprogrammerchallenges.data.DPChallengesContract;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChallengeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // LoaderManager.LoaderCallbacks<Object>
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    ChallengeCursorAdapter mAdapter;
    protected RecyclerView mRecyclerView;

    public ChallengeFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ChallengeFragment newInstance(int sectionNumber) {
        ChallengeFragment fragment = new ChallengeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Bundle bundle = getArguments();
        int tabPosition = bundle.getInt(ARG_SECTION_NUMBER) - 1;

        mAdapter = new ChallengeCursorAdapter(getContext());
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewChallenges);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        getLoaderManager().initLoader(tabPosition, null, this);
        return rootView;
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
                String sortQuery;
                Context applicationContext = MainActivity.getContextOfApplication();
                SharedPreferences sortSettings = ChallengeFragment.this.getContext().getSharedPreferences("sortSettings", Context.MODE_PRIVATE);
                String sortBy = sortSettings.getString("sortBy", "DESC");

                if (id >= 1 && id <= 3) {
                    sortQuery = " AND difficulty_num = " + id;
                } else {
                    sortQuery = "";
                }

                try {
                    final Cursor query = applicationContext.getContentResolver().query(DPChallengesContract.ChallengeEntry.CONTENT_URI,
                            null,
                            "show_challenge = 1 AND completed_challenge = 0" + sortQuery,
                            null,
                            DPChallengesContract.ChallengeEntry.COLUMN_CHALLENGE_NUM + " " + sortBy + ", "
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

//    public static void refreshData() {
//        mAdapter.notifyDataSetChanged();
//    }

}
