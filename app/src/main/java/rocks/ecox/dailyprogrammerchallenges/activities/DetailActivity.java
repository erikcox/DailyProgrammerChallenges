package rocks.ecox.dailyprogrammerchallenges.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import rocks.ecox.dailyprogrammerchallenges.R;
import rocks.ecox.dailyprogrammerchallenges.fragments.DetailFragment;
import rocks.ecox.dailyprogrammerchallenges.fragments.SolutionFragment;

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
    public static String challengeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        contextOfApplication = getApplicationContext();

        dbId = getIntent().getStringExtra("EXTRA_DB_ID");
        challengeId = getIntent().getStringExtra("EXTRA_CHALLENGE_ID");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container_detail);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_detail);
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
     * A {@link FragmentStatePagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            DetailFragment fragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("db_id", getDbId());
            bundle.putString("challenge_id", challengeId);
            fragment.setArguments(bundle);

            if (position == 0) {
                return DetailFragment.newInstance(position + 1, getDbId());
            } else if (position == 1) {
                return SolutionFragment.newInstance(position + 1);
            } else {
                return null;
            }

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
                    return getString(R.string.header_description);
                case 1:
                    return getString(R.string.header_solutions);
            }
            return null;
        }
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    public static String getChallengeParent() {
        return challengeId;
    }

    public String getDbId(){
        return dbId;
    }
}