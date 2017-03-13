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
import rocks.ecox.dailyprogrammerchallenges.models.ChildComment;
import rocks.ecox.dailyprogrammerchallenges.models.Solution;
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

        // Get solution json data
        redditSolutions.getFeed(subreddit, challenge, new Callback<Solution>() {
            @Override
            public void success(Solution solution, Response response) {
                for (ChildComment c : solution.getData().getChildren()) {
                    try {
                        // Check if solution already exists in DB
                        List<Solution> duplicateSolutions =
                                SQLiteUtils.rawQuery(Solution.class,
                                        "SELECT * FROM Solutions WHERE parent_id = ?",
                                        new String[] {c.getData().getCommentParentId() });

                        if (duplicateSolutions.size() == 0) {
                            // Set solution attributes
                            Solution sol = new Solution();

                            sol.setCommentParentId(c.getData().getCommentParentId());
                            sol.setCommentId(c.getData().getCommentId());
                            sol.setCommentText(c.getData().getCommentText());

                            if(c.getData().getCommentTextHtml() != null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    sol.setCommentTextHtml(Html.fromHtml(c.getData().getCommentTextHtml(), Html.FROM_HTML_MODE_COMPACT).toString());
                                } else {
                                    sol.setCommentTextHtml(Html.fromHtml(c.getData().getCommentTextHtml()).toString());
                                }
                            }

                            sol.setCommentUps(c.getData().getCommentUps());

                            // TODO: add logic for showComment

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
