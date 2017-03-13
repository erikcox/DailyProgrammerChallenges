package rocks.ecox.dailyprogrammerchallenges.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataComment_ {
    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("parent_id")
    @Expose
    private String commentParentId;

    @SerializedName("id")
    @Expose
    private String commentId;

    @SerializedName("body")
    @Expose
    private String commentText;

    @SerializedName("body_html")
    @Expose
    private String commentTextHtml;

    @SerializedName("ups")
    @Expose
    private int commentUps;

    public String getKind() {
        return kind;
    }

    public String getCommentParentId() {
        return commentParentId;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getCommentTextHtml() {
        return commentTextHtml;
    }

    public int getCommentUps() {
        return commentUps;
    }

}