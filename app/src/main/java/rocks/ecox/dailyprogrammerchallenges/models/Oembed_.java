package rocks.ecox.dailyprogrammerchallenges.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Oembed_ {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("thumbnail_width")
    @Expose
    private Integer thumbnailWidth;
    @SerializedName("provider_name")
    @Expose
    private String providerName;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("thumbnail_height")
    @Expose
    private Integer thumbnailHeight;

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Integer getThumbnailHeight() {
        return thumbnailHeight;
    }
}

