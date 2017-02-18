package rocks.ecox.dailyprogrammerchallenges;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import rocks.ecox.dailyprogrammerchallenges.utility.ReleaseTree;
import timber.log.Timber;

public class DPCApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

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
}
