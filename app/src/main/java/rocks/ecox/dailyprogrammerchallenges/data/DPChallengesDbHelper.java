package rocks.ecox.dailyprogrammerchallenges.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static rocks.ecox.dailyprogrammerchallenges.data.DPChallengesContract.*;

public class DPChallengesDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "RedditData.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;

    // Constructor
    DPChallengesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // TODO: remove this if Active Android already does this, otherwise add Solution table too
    /**
     * Called when the Challenges database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE " + ChallengeEntry.TABLE_NAME + " (" +
                ChallengeEntry._ID + " INTEGER PRIMARY KEY, " +
                ChallengeEntry.COLUMN_POST_ID + " TEXT, " +
                ChallengeEntry.COLUMN_DIFFICULTY + " TEXT, " +
                ChallengeEntry.COLUMN_DIFFICULTY_NUM + " INTEGER, " +
                ChallengeEntry.COLUMN_CHALLENGE_NUM + " INTEGER, " +
                ChallengeEntry.COLUMN_TITLE + " TEXT, " +
                ChallengeEntry.COLUMN_DESCRIPTION + " TEXT, " +
                ChallengeEntry.COLUMN_DESCRIPTION_HTML + " TEXT, " +
                ChallengeEntry.COLUMN_AUTHOR + " TEXT, " +
                ChallengeEntry.COLUMN_COMMENTS_NUM + " INTEGER, " +
                ChallengeEntry.COLUMN_UPVOTES_NUM + " INTEGER, " +
                ChallengeEntry.COLUMN_URL + " TEXT, " +
                ChallengeEntry.COLUMN_TIMESTAMP + " INTEGER, " +
                ChallengeEntry.COLUMN_VISIBILITY + " INTEGER, ";

        db.execSQL(CREATE_TABLE);
    }


    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ChallengeEntry.TABLE_NAME);
        onCreate(db);
    }
}
