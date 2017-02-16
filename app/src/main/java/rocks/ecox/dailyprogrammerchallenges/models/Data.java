package rocks.ecox.dailyprogrammerchallenges.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {
    @SerializedName("children")
    @Expose
    private List<Child> children = new ArrayList<Child>();

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}