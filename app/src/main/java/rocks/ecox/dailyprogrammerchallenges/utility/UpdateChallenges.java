package rocks.ecox.dailyprogrammerchallenges.utility;

import android.content.Context;
import android.os.Build;
import android.text.Html;

import com.activeandroid.util.SQLiteUtils;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import okhttp3.OkHttpClient;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rocks.ecox.dailyprogrammerchallenges.activities.SplashActivity;
import rocks.ecox.dailyprogrammerchallenges.api.RedditChallengeApi;
import rocks.ecox.dailyprogrammerchallenges.models.challenge.Challenge;
import rocks.ecox.dailyprogrammerchallenges.models.challenge.Child;
import timber.log.Timber;

public class UpdateChallenges {

    public static void update(final Context context) {
        // Reddit API stuff
        String API = "https://www.reddit.com/";
        String subreddit = "dailyprogrammer";
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        .serializeNulls()
                        .create()))
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(API)
                .build();

        final RedditChallengeApi redditData = restAdapter.create(RedditChallengeApi.class);

        // Get challenge json data
        redditData.getFeed(subreddit, new Callback<Challenge>() {
            @Override
            public void success(Challenge challenge, Response response) {
                for (Child c : challenge.getData().getChildren()) {
                    try {
                        // Check if challenge already exists in DB
                        List<Challenge> duplicateChallanges =
                                SQLiteUtils.rawQuery(Challenge.class,
                                        "SELECT * FROM Challenges WHERE post_id = ?",
                                        new String[] {c.getData().getPostId() });

                        if (duplicateChallanges.size() == 0) {
                            // Set challenge attributes
                            Challenge ch = new Challenge();

                            ch.setPostId(c.getData().getPostId());
                            ch.setPostTitle(c.getData().getPostTitle());
                            ch.setPostDescription(c.getData().getPostDescription());

                            if(c.getData().getPostDescriptionHtml() != null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    ch.setPostDescriptionHtml(Html.fromHtml(c.getData().getPostDescriptionHtml(), Html.FROM_HTML_MODE_COMPACT).toString());
                                } else {
                                    ch.setPostDescriptionHtml(Html.fromHtml(c.getData().getPostDescriptionHtml()).toString());
                                }
                            }

                            ch.setPostAuthor(c.getData().getPostAuthor());
                            ch.setPostUrl(c.getData().getPostUrl());
                            ch.setPostUps(c.getData().getUps());
                            ch.setPostUtc(c.getData().getPostUtc());
                            ch.setNumberOfComments(c.getData().getNumberOfComments());

                            String title = ch.getPostTitle();
                            String id = ch.getPostId();

                            // Set challenge number from title field
                            ch.setChallengeNumber(DataParsing.getChallengeNumber(title));

                            // Set difficulty from title field
                            ch.setChallengeDifficulty(DataParsing.getChallengeDifficulty(title));

                            // Set difficulty number flag for sorting
                            ch.setChallengeDifficultyNumber(DataParsing.getChallengeDifficultyNumber(ch.getChallengeDifficulty()));

                            // Set cleanedPostTitle field
                            ch.setCleanedPostTitle(DataParsing.getCleanPostTitle(title));

                            if (ch.getChallengeDifficulty().equals("Not a valid challenge")) {
                                ch.setShowChallenge(false);
                            } else {
                                ch.setShowChallenge(true);
                            }

                            ch.save();
                        }
                    } catch (NullPointerException e) {
                        // Check if Crashlytics is running before logging exception
                        if (Fabric.isInitialized()) {
                            Crashlytics.logException(e);
                        }
                            e.printStackTrace();
                    }
                }
                SplashActivity.startApp(context);
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e("Retrofit error: %s", error.getMessage());
            }
        });

    }
}
