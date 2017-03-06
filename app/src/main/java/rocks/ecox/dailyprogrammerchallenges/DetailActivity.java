package rocks.ecox.dailyprogrammerchallenges;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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
    public String dbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();

        dbId = getIntent().getStringExtra("EXTRA_DB_ID");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

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
    public static class DetailFragment extends Fragment {
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
            View rootView = inflater.inflate(R.layout.detail_layout, container, false);

            String rowId = getArguments().getString(ARG_CHALLENGE_ID);
            mAdapter = new ChallengeCursorAdapter(getContext());
            Uri challenge = DPChallengesContract.ChallengeEntry.CONTENT_URI.buildUpon().appendPath(rowId).build();
            String title = "";
            String description = "";
            String author = "xxx";
            TextView detailChallengeTitle = (TextView) rootView.findViewById(R.id.detailChallengeTitle);
            TextView detailChallengeDescription = (TextView) rootView.findViewById(R.id.detailChallengeDescription);
            TextView detailChallengeAuthor = (TextView) rootView.findViewById(R.id.detailChallengeAuthor);

            Timber.d("URI: %s", challenge);

            Cursor mCursor = getContextOfApplication().getContentResolver().query(challenge,
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

//            if (mCursor.moveToFirst()) {
//                do{
//                    Toast.makeText(this,
//                            mCursor.getString(mCursor.getColumnIndex(challenge._ID)) +
//                                    ", " +  mCursor.getString(mCursor.getColumnIndex( challenge.NAME)) +
//                                    ", " + mCursor.getString(mCursor.getColumnIndex( challenge.GRADE)),
//                            Toast.LENGTH_SHORT).show();
//                } while (mCursor.moveToNext());
//            }

            detailChallengeTitle.setText(title);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                detailChallengeDescription.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
            } else {
                detailChallengeDescription.setText(Html.fromHtml(description));
            }
            // TODO: Display author, currently not showing
            detailChallengeAuthor.setText(String.format("%s%s", getString(R.string.author_label), author));

            return rootView;
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
            // getItem is called to instantiate the fragment for the given page
            DetailFragment fragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("db_id", getDbId());
            fragment.setArguments(bundle);
            return fragment.newInstance(position + 1, getDbId());
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

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    public String getDbId(){
        return dbId;
    }
}