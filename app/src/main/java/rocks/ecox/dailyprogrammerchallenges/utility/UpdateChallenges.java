package rocks.ecox.dailyprogrammerchallenges.utility;

import android.text.Html;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rocks.ecox.dailyprogrammerchallenges.api.RedditApi;
import rocks.ecox.dailyprogrammerchallenges.models.Challenge;
import rocks.ecox.dailyprogrammerchallenges.models.Child;

public class UpdateChallenges {

    public static void update() {
        // Reddit API stuff
        String API = "https://www.reddit.com/";
        String subreddit = "dailyprogrammer";
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        final RedditApi challenge = restAdapter.create(RedditApi.class);

        // Regex stuff for data cleaning of the title
        final String titlePattern = "([^\\]]+)$";
        final String challengeNumberPattern = "([\\?]*)#(\\d*)";
        // Create Pattern objects
        final Pattern tp = Pattern.compile(titlePattern);
        final Pattern cnp = Pattern.compile(challengeNumberPattern);

        // Get json data
        challenge.getFeed(subreddit, new Callback<Challenge>() {
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

                        // Set challenge number from title field
                        Matcher cnm = cnp.matcher(challenge.getPostTitle());
                        if (cnm.find()) {
                            try {
                                challenge.setChallengeNumber(Integer.parseInt(cnm.group().substring(1)));
                            } catch (NumberFormatException e) {
                                Log.e("ERROR", e + " " + cnm.group().substring(1));
                            }

                            Log.d("REGEX", "Challenge Number: " + challenge.getChallengeDifficulty());
                        }

                        // Set difficulty from title field
                        String lowerTitle = challenge.getPostTitle().toLowerCase();
                        if (lowerTitle.contains("easy")) {
                            Log.d("REGEX", "Difficulty: " + "Easy");
                        } else if (lowerTitle.contains("intermediate")) {
                            Log.d("REGEX", "Difficulty: " + "Intermediate");
                        } else if (lowerTitle.contains("hard")) {
                            Log.d("REGEX", "Difficulty: " + "Hard");
                        } else {
                            Log.d("REGEX", "Difficulty: " + "No difficulty" + " " + lowerTitle);
                        }

                        // Set cleanedPostTitle field
                        Matcher tm = tp.matcher(challenge.getPostTitle());

                        if (tm.find()) {
                            challenge.setCleanedPostTitle(tm.group().substring(1));
                            Log.d("REGEX", "Cleaned Title: " + challenge.getCleanedPostTitle());
                        }

                        Log.d("DEBUG", "Done processing post id: " + challenge.getPostId());
                    } catch (NullPointerException e) {
                        Log.e("ERROR setting data", e.toString() + " Source id --> " + challenge.getPostId());
                    }
                }

                // TODO: Set the data to the views here
                try {
                    Log.d("DEBUG", "Title: " + challenge.getCleanedPostTitle());
//                    title.setText(challenge.getTitle());
//                    sub.setText("/r/" + challenge.getSubreddit());
//                    ups.setText("" + NumberFormat.getNumberInstance(Locale.getDefault()).format(challenge.getUps()));
                } catch (NullPointerException e) {
                    Log.e("ERROR setting UI", e.toString() + " id: " + challenge.getPostId());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR", error.getMessage());
            }
        });
    }
}
