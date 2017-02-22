package rocks.ecox.dailyprogrammerchallenges.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data_ {
    @SerializedName("id")
    @Expose
    private String postId;

    @SerializedName("title")
    @Expose
    private String postTitle;

    @SerializedName("selftext")
    @Expose
    private String postDescription;

    @SerializedName("author")
    @Expose
    private String postAuthor;

    @SerializedName("url")
    @Expose
    private String postUrl;

    @SerializedName("ups")
    @Expose
    private Integer ups;

    @SerializedName("created")
    @Expose
    private Integer postUtc;

    @SerializedName("num_comments")
    @Expose
    private Integer numberOfComments;

    public String getPostId() {
        return postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public Integer getUps() {
        return ups;
    }

    public Integer getPostUtc() {
        return postUtc;
    }

    public Integer getNumberOfComments() {
        return numberOfComments;
    }
}