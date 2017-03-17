package rocks.ecox.dailyprogrammerchallenges.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rocks.ecox.dailyprogrammerchallenges.utility.UpdateChallenges;

public class SplashActivity extends AppCompatActivity {

    private static Context mContext;
    static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getApplicationContext();
        activity = this;

        // Get data from reddit and create Challenge objects
        UpdateChallenges.update();
    }

    public static Context getAppContext(){
        return mContext;
    }

    public static void startApp() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        activity.finish();
    }
}
