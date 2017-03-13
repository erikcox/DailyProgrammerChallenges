package rocks.ecox.dailyprogrammerchallenges.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataComment {
    @SerializedName("children")
    @Expose
    private List<ChildComment> children = new ArrayList<ChildComment>();

    public List<ChildComment> getChildren() {
        return children;
    }

    public void setChildren(List<ChildComment> children) {
        this.children = children;
    }
}