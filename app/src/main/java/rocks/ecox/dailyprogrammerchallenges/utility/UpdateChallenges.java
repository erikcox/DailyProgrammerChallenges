package rocks.ecox.dailyprogrammerchallenges.utility;

import android.text.Html;

import com.crashlytics.android.Crashlytics;

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
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        final RedditApi redditData = restAdapter.create(RedditApi.class);

        // Get json data
        redditData.getFeed(subreddit, new Callback<Challenge>() {
            @Override
            public void success(Challenge challenge, Response response) {
                for (Child c : challenge.getData().getChildren()) {
                    try {
                        // Set challenge attributes
                        challenge.setPostId(c.getData().getPostId());
                        challenge.setPostTitle(Html.fromHtml(c.getData().getPostTitle()).toString());
                        challenge.setPostDescription(Html.fromHtml(c.getData().getPostDescription()).toString());
                        challenge.setPostAuthor(c.getData().getPostAuthor());
                        challenge.setPostUrl(c.getData().getPostUrl());
                        challenge.setPostUps(c.getData().getUps());
                        challenge.setPostUtc(c.getData().getPostUtc());
                        challenge.setNumberOfComments(c.getData().getNumberOfComments());

                        String title = challenge.getPostTitle();
                        String id = challenge.getPostId();

                        // Set challenge number from title field
                        challenge.setChallengeNumber(DataParsing.getChallengeNumber(title));

                        // Set difficulty from title field
                        challenge.setChallengeDifficulty(DataParsing.getChallengeDifficulty(title));

                        if (challenge.getChallengeDifficulty().equals("Not a valid challenge")) {
                            challenge.setShowChallenge(false);
                        } else {
                            challenge.setShowChallenge(true);
                        }

                        // Set cleanedPostTitle field
                        challenge.setCleanedPostTitle(DataParsing.getCleanPostTitle(title));
                        challenge.save();
                        Timber.d("Done processing post id: %s", id);
                    } catch (NullPointerException e) {
                        Crashlytics.logException(e);
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
