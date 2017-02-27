package rocks.ecox.dailyprogrammerchallenges.utility;

import android.os.Build;
import android.text.Html;

import com.activeandroid.util.SQLiteUtils;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rocks.ecox.dailyprogrammerchallenges.api.RedditApi;
import rocks.ecox.dailyprogrammerchallenges.models.Challenge;
import rocks.ecox.dailyprogrammerchallenges.models.Child;
import timber.log.Timber;

import static android.R.attr.id;

public class UpdateChallenges {

    public static void update() {
        // Reddit API stuff
        String API = "https://www.reddit.com/";
        String subreddit = "dailyprogrammer";
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        final RedditApi redditData = restAdapter.create(RedditApi.class);

        // Get json data
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
                            ch.setPostTitle(Html.fromHtml(c.getData().getPostTitle()).toString());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                ch.setPostDescription(Html.fromHtml(c.getData().getPostDescription(), Html.FROM_HTML_MODE_COMPACT).toString());
                            } else {
                                ch.setPostDescription(Html.fromHtml(c.getData().getPostDescription()).toString());
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

                            if (ch.getChallengeDifficulty().equals("Not a valid challenge")) {
                                ch.setShowChallenge(false);
                            } else {
                                ch.setShowChallenge(true);
                            }

                            // Set cleanedPostTitle field
                            ch.setCleanedPostTitle(DataParsing.getCleanPostTitle(title));
                            ch.save();
                            Timber.d("Done processing post id: %s", id);
                        } else {
                            Timber.d("Number of matching post id's for %s: %s", c.getData().getPostId(), duplicateChallanges.size());
                        }
                    } catch (NullPointerException e) {
                        // Need to initialize Crashlytics first
//                        Crashlytics.logException(e);
                        Timber.e("ERROR setting data to id %s. Exception: %s", id, e.toString());
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Timber.d("Retrofit error: %s", error.getMessage());
            }
        });
    }
}
