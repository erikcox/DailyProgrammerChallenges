package rocks.ecox.dailyprogrammerchallenges.utility;

import com.activeandroid.ActiveAndroid;
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

    public static void update(final String postId) {
        // Reddit API stuff
        String API = "https://www.reddit.com/";
        String subreddit = "dailyprogrammer";
        String challenge = postId;

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
        redditSolutions.getFeed(subreddit, challenge, new Callback<List<Solution>>() {
            @Override
            public void success(List<Solution> solutions, Response response) {
                for (Solution sol : solutions) {
                    for (ChildComment c : sol.getData().getChildren()) {
                        ActiveAndroid.beginTransaction();
                        try {
                            Timber.d("Solution RAW: %s", c.getData().getCommentText());

                            Solution s = new Solution();
                            s.setCommentParentId(c.getData().getCommentParentId());
                            Timber.d("Solution GET: %s", sol.getCommentParentId());
                            s.setCommentId(c.getData().getCommentId());
                            s.setCommentText(c.getData().getCommentText());
                            // TODO: add HTML comments with version condition
                            //s.setCommentTextHtml();
                            s.setCommentUps(c.getData().getCommentUps());
                            // TODO: add logic for showComment boolean
                            s.save();
                        } catch (NullPointerException e) {
                            // Check if Crashlytics is running before logging exception
                            if (Fabric.isInitialized()) {
                                Crashlytics.logException(e);
                            }
                            e.printStackTrace();
                        }
                        finally {
                            ActiveAndroid.endTransaction();
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e("Retrofit error on : %s | %s", postId, error.getMessage());
            }
        });
    }
}
