package rocks.ecox.dailyprogrammerchallenges.models.solution;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataSolution {
    @SerializedName("children")
    @Expose
    private List<ChildSolution> children = new ArrayList<ChildSolution>();

    public List<ChildSolution> getChildren() {
        return children;
    }

    public void setChildren(List<ChildSolution> children) {
        this.children = children;
    }

}