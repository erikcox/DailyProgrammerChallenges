package rocks.ecox.dailyprogrammerchallenges.utility;

import android.text.Html;
import android.util.Log;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rocks.ecox.dailyprogrammerchallenges.api.RedditApi;
import rocks.ecox.dailyprogrammerchallenges.models.Challenge;
import rocks.ecox.dailyprogrammerchallenges.models.Child;

import static android.R.attr.id;

public class UpdateChallenges {

    public static void update() {
        // Reddit API stuff
        String API = "https://www.reddit.com/";
        String subreddit = "dailyprogrammer";
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        final RedditApi challenge = restAdapter.create(RedditApi.class);

        // Get json data
        challenge.getFeed(subreddit, new Callback<Challenge>() {
            @Override
            public void success(Challenge challenge, Response response) {
                for (Child c : challenge.getData().getChildren()) {
                    try {
                        // Set challenge attributes
                        // TODO: remove redundant gets, use variables (e.g. getTitle)
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
                            challenge.setShowPost(false);
                        }

                        // Set cleanedPostTitle field
                        challenge.setCleanedPostTitle(DataParsing.getCleanPostTitle(title));
                        Log.d("DEBUG", "Done processing post id: " + id);
                    } catch (NullPointerException e) {
                        Log.e("ERROR setting data", e.toString() + " Source id --> " + id);
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR", error.getMessage());
            }
        });
    }
}