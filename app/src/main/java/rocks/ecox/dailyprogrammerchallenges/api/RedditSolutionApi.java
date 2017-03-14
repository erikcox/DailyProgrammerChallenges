package rocks.ecox.dailyprogrammerchallenges.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rocks.ecox.dailyprogrammerchallenges.models.Solution;

public interface RedditSolutionApi {
    @GET("/r/{sub}/comments/{id}.json?depth=1")
    public void getFeed(@Path("sub") String sub, @Path("id") String id, Callback<List<Solution>> response);
}