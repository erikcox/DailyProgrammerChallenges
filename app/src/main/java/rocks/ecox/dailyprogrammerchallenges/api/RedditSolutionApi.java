package rocks.ecox.dailyprogrammerchallenges.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rocks.ecox.dailyprogrammerchallenges.models.Challenge;
import rocks.ecox.dailyprogrammerchallenges.models.Solution;

public interface RedditSolutionApi {
    @GET("/r/{sub}/comments/{id}.json?")
    public void getFeed(@Path("sub") String sub, @Path("id") String id, Callback<Solution> response);
}