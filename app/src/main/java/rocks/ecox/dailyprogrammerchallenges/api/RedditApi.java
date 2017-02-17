package rocks.ecox.dailyprogrammerchallenges.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rocks.ecox.dailyprogrammerchallenges.models.Challenge;

public interface RedditApi {
    // TODO: edit this so that you can get all, not just the first 100
    @GET("/r/{sub}.json?limit=100")
    public void getFeed(@Path("sub") String sub, Callback<Challenge> response);
}
