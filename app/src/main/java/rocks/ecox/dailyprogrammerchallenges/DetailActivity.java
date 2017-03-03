package rocks.ecox.dailyprogrammerchallenges;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rocks.ecox.dailyprogrammerchallenges.data.DPChallengesContract;
import timber.log.Timber;

import static rocks.ecox.dailyprogrammerchallenges.utility.DataParsing.setPageNum;

public class DetailActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentStatePagerAdapter} derivative, which will keep every
     * loaded fragment in memory.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    // A static variable to get a reference of the application Context
    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Get data from reddit and create Challenge objects
//        UpdateChallenges.update();
    }

    /**
     * This method is called after this activity has been paused or restarted.
     * This restarts the loader to re-query the underlying data for any changes.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
        // LoaderManager.LoaderCallbacks<Object>
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_CHALLENGE_ID = "challenge_id";

        public DetailFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static DetailFragment newInstance(int dbId) {
            DetailFragment fragment = new DetailFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_CHALLENGE_ID, dbId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.detail_layout, container, false);

            Bundle bundle = getArguments();
            int challengeId = bundle.getInt(ARG_CHALLENGE_ID);

            TextView detailChallengeTitle = (TextView) rootView.findViewById(R.id.detailChallengeTitle);
            TextView detailChallengeDescription = (TextView) rootView.findViewById(R.id.detailChallengeDescription);
            TextView detailChallengeAuthor = (TextView) rootView.findViewById(R.id.detailChallengeAuthor);

            String title = "Challenge title";
            String description = "Challenge description goes here. Lots and lots of words going on for ever."
                    + "Challenge description goes here. Lots and lots of words going on for ever."
                    + "Challenge description goes here. Lots and lots of words going on for ever.";
            String author = "/u/DeletedAccount";

            getLoaderManager().initLoader(0, null, this); // Need to pass challenge id & flag to say it's one record

            detailChallengeTitle.setText(title);
            detailChallengeDescription.setText(description);
            detailChallengeAuthor.setText(author);

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
                    Context applicationContext = DetailActivity.getContextOfApplication();

                    if (id >= 1 && id <= 3) {
                        sortQuery = " AND difficulty_num = " + id;
                    } else {
                        sortQuery = "";
                    }

                    try {
                        final Cursor query = applicationContext.getContentResolver().query(DPChallengesContract.ChallengeEntry.CONTENT_URI,
                                null,
                                "show_challenge = 1" + sortQuery,
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
//            mAdapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            // Loader<Object> loader
//            mAdapter.swapCursor(null);
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page\
            return DetailFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            setPageNum(position, getApplicationContext());

            switch (position) {
                case 0:
                    return "DESCRIPTION";
                case 1:
                    return "SOLUTIONS";
            }
            return null;
        }

    }

    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
}