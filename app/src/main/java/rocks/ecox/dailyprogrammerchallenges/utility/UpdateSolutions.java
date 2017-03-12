package rocks.ecox.dailyprogrammerchallenges.utility;

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
import rocks.ecox.dailyprogrammerchallenges.api.RedditSolutionApi;
import rocks.ecox.dailyprogrammerchallenges.models.Challenge;
import rocks.ecox.dailyprogrammerchallenges.models.Child;
import timber.log.Timber;

public class UpdateSolutions {

    public static void update() {
        // Reddit API stuff
        String API = "https://www.reddit.com/";
        String subreddit = "dailyprogrammer";
        String challenge = "5yaiin";

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        .serializeNulls()
                        .create()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API)
                .build();

        final RedditSolutionApi redditSolutions = restAdapter.create(RedditSolutionApi.class);

        // Get challenge json data
        redditSolutions.getFeed(subreddit, challenge, new Callback<Challenge>() {
            @Override
            public void success(Solution solution, Response response) {
                for (Child c : solution.getData().getChildren()) {
                    try {
                        // Check if challenge already exists in DB
                        // TODO: create Solutions DB and Solution class
                        List<Solution> duplicateSolutions =
                                SQLiteUtils.rawQuery(Solution.class,
                                        "SELECT * FROM Solutions WHERE post_id = ?",
                                        new String[] {c.getData().getPostId() });

                        if (duplicateSolutions.size() == 0) {
                            // Set challenge attributes
                            Solution sol = new Solution();

                            sol.setPostId(c.getData().getPostId());
                            sol.setPostTitle(c.getData().getPostTitle());
                            sol.setPostDescription(c.getData().getPostDescription());

                            if(c.getData().getPostDescriptionHtml() != null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    sol.setPostDescriptionHtml(Html.fromHtml(c.getData().getPostDescriptionHtml(), Html.FROM_HTML_MODE_COMPACT).toString());
                                } else {
                                    sol.setPostDescriptionHtml(Html.fromHtml(c.getData().getPostDescriptionHtml()).toString());
                                }
                            }

                            sol.setPostAuthor(c.getData().getPostAuthor());
                            sol.setPostUrl(c.getData().getPostUrl());
                            sol.setPostUps(c.getData().getUps());
                            sol.setPostUtc(c.getData().getPostUtc());
                            sol.setNumberOfComments(c.getData().getNumberOfComments());

                            String title = sol.getPostTitle();
                            String id = sol.getPostId();

                            // Set challenge number from title field
                            sol.setChallengeNumber(DataParsing.getChallengeNumber(title));

                            // Set difficulty from title field
                            sol.setChallengeDifficulty(DataParsing.getChallengeDifficulty(title));

                            // Set difficulty number flag for sorting
                            sol.setChallengeDifficultyNumber(DataParsing.getChallengeDifficultyNumber(sol.getChallengeDifficulty()));

                            // Set cleanedPostTitle field
                            sol.setCleanedPostTitle(DataParsing.getCleanPostTitle(title));

                            if (sol.getChallengeDifficulty().equals("Not a valid challenge")) {
                                sol.setShowChallenge(false);
                            } else {
                                sol.setShowChallenge(true);
                            }

                            sol.save();
                        }
                    } catch (NullPointerException e) {
                        // Check if Crashlytics is running before logging exception
                        if (Fabric.isInitialized()) {
                            Crashlytics.logException(e);
                        }
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e("Retrofit error: %s", error.getMessage());
            }
        });
    }
}
