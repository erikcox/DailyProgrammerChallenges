package rocks.ecox.dailyprogrammerchallenges.models;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "Solutions", id = BaseColumns._ID)
public class Solution extends Model {

    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("data")
    @Expose
    private DataComment data;

    @Column(name = "parent_id")
    private String commentParentId;

    @Column(name = "comment_id")
    private String commentId;

    @Column(name = "body")
    private String commentText;

    @Column(name = "body_html")
    private String commentTextHtml;

    @Column(name = "ups")
    private int commentUps = 0;

    @Column(name = "show_comment")
    private boolean showComment = true;

    public Solution() {
        super();
    }

    public Solution(String commentParentId, String commentId, String commentText, String commentTextHtml, int commentUps, boolean showComment) {
        super();
        this. commentParentId = commentParentId;
        this. commentId = commentId;
        this.commentText = commentText;
        this.commentTextHtml = commentTextHtml;
        this.commentUps = commentUps;
        this.showComment = showComment;
    }

    public DataComment getData() {
        return data;
    }

    public void setData(DataComment data) {
        this.data = data;
    }

    public String getCommentParentId() {
        return commentParentId;
    }

    public void setCommentParentId(String commentParentId) {
        this.commentParentId = commentParentId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentTextHtml() {
        return commentTextHtml;
    }

    public void setCommentTextHtml(String commentTextHtml) {
        this.commentTextHtml = commentTextHtml;
    }

    public int getCommentUps() {
        return commentUps;
    }

    public void setCommentUps(int commentUps) {
        this.commentUps = commentUps;
    }

    public boolean isShowComment() {
        return showComment;
    }

    public void setShowComment(boolean showComment) {
        this.showComment = showComment;
    }

}
