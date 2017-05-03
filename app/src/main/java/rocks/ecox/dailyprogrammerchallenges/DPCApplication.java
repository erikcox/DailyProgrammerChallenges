package rocks.ecox.dailyprogrammerchallenges;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import io.fabric.sdk.android.Fabric;
import rocks.ecox.dailyprogrammerchallenges.utility.ReleaseTree;
import timber.log.Timber;

public class DPCApplication extends com.activeandroid.app.Application {

    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        // Set up Timber for logging
        if (BuildConfig.DEBUG) {
            // Debug mode
            Timber.plant(new Timber.DebugTree() {
                // Add the line number to the tag
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ":" + element.getLineNumber();
                }
             });
        } else {
            // Release mode
            Timber.plant(new ReleaseTree());
            // Initialize crash reporting for release build
            Fabric.with(this, new Crashlytics());
        }
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }
}
