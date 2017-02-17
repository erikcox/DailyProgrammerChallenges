package rocks.ecox.dailyprogrammerchallenges.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Challenge {
    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("data")
    @Expose
    private Data data;

    private String postId;
    private String postTitle;
    private String cleanedPostTitle;
    private String postDescription;
    private String postAuthor;
    private Integer postUtc;
    private String postUrl;
    private Integer challengeNumber;
    private String challengeDifficulty;
    private Integer postUps;
    private Integer numberOfComments;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getCleanedPostTitle() {
        return cleanedPostTitle;
    }

    public void setCleanedPostTitle(String cleanedPostTitle) {
        this.cleanedPostTitle = cleanedPostTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }

    public Integer getPostUtc() {
        return this.postUtc;
    }

    public void setPostUtc(Integer postUtc) {
        this.postUtc = postUtc;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public Integer getChallengeNumber() {
        return challengeNumber;
    }

    public void setChallengeNumber(Integer challengeNumber) {
        this.challengeNumber = challengeNumber;
    }

    public String getChallengeDifficulty() {
        return challengeDifficulty;
    }

    public void setChallengeDifficulty(String challengeDifficulty) {
        this.challengeDifficulty = challengeDifficulty;
    }

    public Integer getPostUps() {
        return postUps;
    }

    public void setPostUps(Integer postUps) {
        this.postUps = postUps;
    }

    public Integer getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

}
