package rocks.ecox.dailyprogrammerchallenges.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Media {
    @SerializedName("oembed")
    @Expose
    private Oembed_ oembed;
    @SerializedName("type")
    @Expose
    private String type;

    public Oembed_ getOembed() {
        return oembed;
    }

    public void setOembed(Oembed_ oembed) {
        this.oembed = oembed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}