package rocks.ecox.dailyprogrammerchallenges.models;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "Challenges", id = BaseColumns._ID)
public class Challenge extends Model {
    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("data")
    @Expose
    private Data data;

    @Column(name = "post_id")
    private String postId;

    @Column(name = "title")
    private String postTitle;

    @Column(name = "clean_title")
    private String cleanedPostTitle;

    @Column(name = "description")
    private String postDescription;

    @Column(name = "description_html")
    private String postDescriptionHtml;

    @Column(name = "author")
    private String postAuthor;

    @Column(name = "timestamp")
    private Integer postUtc;

    @Column(name = "url")
    private String postUrl;

    @Column(name = "challenge_number")
    private Integer challengeNumber;

    @Column(name = "difficulty")
    private String challengeDifficulty;

    @Column(name = "ups")
    private Integer postUps;

    @Column(name = "num_comments")
    private Integer numberOfComments;

    @Column(name = "show_challenge")
    private boolean showChallenge = true;

    public Challenge() {
        super();
    }

    public Challenge(String postId, String postTitle, String cleanedPostTitle, String postDescription, String postDescriptionHtml, String postAuthor, int postUtc, String postUrl, int challengeNumber, String challengeDifficulty, int postUps, int numberOfComments, boolean showChallenge) {
        super();
        this.postId = postId;
        this.postTitle = postTitle;
        this.cleanedPostTitle = cleanedPostTitle;
        this.postDescription = postDescription;
        this.postDescriptionHtml = postDescriptionHtml;
        this.postAuthor = postAuthor;
        this.postUtc = postUtc;
        this.postUrl = postUrl;
        this.challengeNumber = challengeNumber;
        this.challengeDifficulty = challengeDifficulty;
        this.postUps = postUps;
        this.numberOfComments = numberOfComments;
        this.showChallenge = showChallenge;
    }

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

    public String getPostDescriptionHtml() {
        return postDescriptionHtml;
    }

    public void setPostDescriptionHtml(String postDescriptionHtml) {
        this.postDescriptionHtml = postDescriptionHtml;
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

    public boolean isShowChallenge() {
        return showChallenge;
    }

    public void setShowChallenge(boolean showChallenge) {
        this.showChallenge = showChallenge;
    }
}
