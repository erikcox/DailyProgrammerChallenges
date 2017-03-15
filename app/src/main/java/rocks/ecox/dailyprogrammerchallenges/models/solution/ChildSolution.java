package rocks.ecox.dailyprogrammerchallenges.models.solution;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildSolution {

    @SerializedName("data")
    @Expose
    private DataSolution_ data;

    public DataSolution_ getData() {
        return data;
    }

    public void setData(DataSolution_ data) {
        this.data = data;
    }

}
