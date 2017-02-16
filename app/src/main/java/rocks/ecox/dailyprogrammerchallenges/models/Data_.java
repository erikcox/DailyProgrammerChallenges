package rocks.ecox.dailyprogrammerchallenges.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data_ {
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("subreddit")
    @Expose
    private String subreddit;
    @SerializedName("media")
    @Expose
    private Media media;
    @SerializedName("over_18")
    @Expose
    private Boolean over18;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ups")
    @Expose
    private Integer ups;

    public String getDomain() {
        return domain;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public Media getMedia() {
        return media;
    }

    public Boolean getOver18() {
        return over18;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public Integer getUps() {
        return ups;
    }

}