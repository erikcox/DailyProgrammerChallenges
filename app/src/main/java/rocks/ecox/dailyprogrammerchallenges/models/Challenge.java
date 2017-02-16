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

    private String subreddit;
    private Media media;
    private Boolean over18;
    private String url;
    private String title;
    private Integer ups;
    private String thumbnailUrl;
    private String provider = "None";

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Boolean getOver18() {
        return over18;
    }

    public void setOver18(Boolean over18) {
        this.over18 = over18;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUps() {
        return ups;
    }

    public void setUps(Integer ups) {
        this.ups = ups;
    }

    public String getKind() {
        return kind;
    }

    public Data getData() {
        return data;
    }

}
