package rocks.ecox.dailyprogrammerchallenges.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class DPChallengesContract {

    public static final String AUTHORITY = "rocks.ecox.dailyprogrammerchallenges";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    public static final String PATH_CHALLENGES = "challenges";

    /* ChallengeEntry is an inner class that defines the contents of the Challenges table */
    public static final class ChallengeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHALLENGES).build();

        // Challenges table and column names
        public static final String TABLE_NAME = "Challenges";

        // Since ChallengeEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the ones below
        public static final String COLUMN_POST_ID = "post_id";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_CHALLENGE_NUM = "challenge_number";
        public static final String COLUMN_TITLE = "clean_title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_COMMENTS_NUM = "num_comments";
        public static final String COLUMN_UPVOTES_NUM = "ups";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_VISIBILITY = "show_challenge";

    }
}
