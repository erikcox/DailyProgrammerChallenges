package rocks.ecox.dailyprogrammerchallenges.utility;

import android.os.Build;
import android.text.Html;

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
import rocks.ecox.dailyprogrammerchallenges.models.solution.ChildSolution;
import rocks.ecox.dailyprogrammerchallenges.models.solution.Solution;
import timber.log.Timber;

public class UpdateSolutions {

    public static void update(final String postId) {
        // Reddit API stuff
        String API = "https://www.reddit.com/";
        String subreddit = "dailyprogrammer";
        final String challenge = postId;

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
                    for (ChildSolution c : sol.getData().getChildren()) {
                        try {

                            // Check if comment is already in DB & kind is t1, NOT t3
                            // c.getData().getKind() always returns null
                            //if (!comments.contains(c.getData().getCommentId())) {
                                Solution s = new Solution();

                                s.setCommentId(c.getData().getCommentId());
                                s.setCommentParentId(challenge);

                                s.setCommentText(c.getData().getCommentText());

                                if (c.getData().getCommentTextHtml() != null) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        s.setCommentTextHtml(Html.fromHtml(c.getData().getCommentTextHtml(), Html.FROM_HTML_MODE_COMPACT).toString());
                                    } else {
                                        s.setCommentTextHtml(Html.fromHtml(c.getData().getCommentTextHtml()).toString());
                                    }
                                }

                                s.setCommentUps(c.getData().getCommentUps());

                                if (s.getCommentText() == null) {
                                    s.setShowComment(false);
                                }

                                s.save();
                            //}
                        } catch (NullPointerException e) {
                            // Check if Crashlytics is running before logging exception
                            if (Fabric.isInitialized()) {
                                Crashlytics.logException(e);
                            }
                            e.printStackTrace();
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
