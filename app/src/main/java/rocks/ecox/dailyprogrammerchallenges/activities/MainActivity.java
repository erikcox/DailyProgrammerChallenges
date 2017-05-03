package rocks.ecox.dailyprogrammerchallenges.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.greenrobot.eventbus.EventBus;

import rocks.ecox.dailyprogrammerchallenges.R;
import rocks.ecox.dailyprogrammerchallenges.fragments.ChallengeFragment;
import rocks.ecox.dailyprogrammerchallenges.fragments.ChallengeFragment.MessageEvent;

import static rocks.ecox.dailyprogrammerchallenges.utility.DataParsing.setPageNum;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    // A static variable to get a reference of the application Context
    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();

        // Create Navigation drawer and inflate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawerLayout.addDrawerListener(drawerToggle);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);
                        Class activity = null;
                        switch(menuItem.getItemId()) {
                            case R.id.nav_challenges:
                                // activity = MainActivity.class; // already in this view
                                break;
                            case R.id.nav_saved:
                                 activity = SavedActivity.class;
                                break;
                            case R.id.nav_completed:
                                 activity = CompletedActivity.class;
                                break;
                            case R.id.nav_about:
                                activity = AboutActivity.class;
                                break;
                            default:
                                // activity = MainActivity.class; // already in this view
                                break;
                        }

                        if (activity != null) {
                            Intent intent = new Intent(MainActivity.contextOfApplication, activity);
                            startActivity(intent);
                        }
                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Listener for sort button in toolbar
        ImageView sort = (ImageView) findViewById(R.id.sortButton);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create sorting shared pref
                SharedPreferences sortSettings = getApplicationContext().getSharedPreferences("sortSettings", Context.MODE_PRIVATE);
                String sortBy = sortSettings.getString("sortBy", "DESC");
                SharedPreferences sortOrder = getApplicationContext().getSharedPreferences("sortSettings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sortOrder.edit();

                if (sortBy.equals("ASC")) {
                    editor.putString("sortBy", "DESC");
                } else {
                    editor.putString("sortBy", "ASC");
                }

                editor.apply();
                EventBus.getDefault().postSticky(new MessageEvent());
            }
        });

        // Initialize Ad Mob
        MobileAds.initialize(getApplicationContext(), getString(R.string.AdUnitId));

        // Load an ad into the AdView
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    /**
     * This method is called after this activity has been paused or restarted.
     * This restarts the loader to re-query the underlying data for any changes.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }

        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
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
            return ChallengeFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            setPageNum(position, getApplicationContext());

            switch (position) {
                case 0:
                    return "ALL";
                case 1:
                    return "EASY";
                case 2:
                    return "MEDIUM";
                case 3:
                    return "HARD";
            }
            return null;
        }

    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }
}
