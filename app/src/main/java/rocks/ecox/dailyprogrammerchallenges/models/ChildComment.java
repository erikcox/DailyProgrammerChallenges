package rocks.ecox.dailyprogrammerchallenges.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildComment {

    @SerializedName("data")
    @Expose
    private DataComment_ data;

    public DataComment_ getData() {
        return data;
    }

    public void setData(DataComment_ data) {
        this.data = data;
    }

}
