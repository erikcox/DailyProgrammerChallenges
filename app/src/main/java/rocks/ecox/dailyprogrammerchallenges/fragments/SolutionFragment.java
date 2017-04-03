package rocks.ecox.dailyprogrammerchallenges.fragments;

import android.content.Context;
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
import rocks.ecox.dailyprogrammerchallenges.activities.DetailActivity;
import rocks.ecox.dailyprogrammerchallenges.adapters.SolutionCursorAdapter;
import rocks.ecox.dailyprogrammerchallenges.data.DPChallengesContract;
import timber.log.Timber;

public class SolutionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String ARG_SECTION_NUMBER = "section_number";
    SolutionCursorAdapter mAdapter;
    protected RecyclerView mRecyclerView;

    public SolutionFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SolutionFragment newInstance(int sectionNumber) {
        SolutionFragment fragment = new SolutionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_solution, container, false);

        Bundle bundle = getArguments();
        int tabPosition = bundle.getInt(ARG_SECTION_NUMBER) - 1;

        mAdapter = new SolutionCursorAdapter(getContext());
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewSolutions);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        getLoaderManager().initLoader(tabPosition, null, this);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(final int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(getContext()) {

            // Initialize a Cursor, this will hold all the solution data
            Cursor mSolutionData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mSolutionData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mSolutionData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Query and load challenge data
                String queryParam = "\"" + DetailActivity.getChallengeParent() + "\"";
                Timber.d("QUERY PARAM: %s", DPChallengesContract.SolutionEntry.TABLE_NAME);
                Context applicationContext = DetailActivity.getContextOfApplication();

                try {
                    final Cursor query = applicationContext.getContentResolver().query(DPChallengesContract.SolutionEntry.SOLUTION_URI,
                            null,
                            DPChallengesContract.SolutionEntry.COLUMN_SHOW_COMMENT + " = 1 AND " + DPChallengesContract.SolutionEntry.COLUMN_PARENT_ID + " = " + queryParam,
                            null,
                            DPChallengesContract.SolutionEntry.COLUMN_UPS + " DESC");
                    query.moveToFirst(); // Not used in challenge fragment
                    return query;
                } catch (Exception e) {
                    Timber.e("Failed to asynchronously load solution data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mSolutionData = data;
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
