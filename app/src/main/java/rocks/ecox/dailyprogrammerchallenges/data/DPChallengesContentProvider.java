package rocks.ecox.dailyprogrammerchallenges.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import static rocks.ecox.dailyprogrammerchallenges.data.DPChallengesContract.*;
import static rocks.ecox.dailyprogrammerchallenges.data.DPChallengesContract.ChallengeEntry.TABLE_NAME;

public class DPChallengesContentProvider extends ContentProvider {

    // Challenges directoryCHALLENGE_WITH_ID
    public static final int CHALLENGES = 100;
    // Single challenge
    public static final int CHALLENGE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Directory
        uriMatcher.addURI(AUTHORITY, PATH_CHALLENGES, CHALLENGES);
        // Single challenge
        uriMatcher.addURI(AUTHORITY, PATH_CHALLENGES + "/#", CHALLENGE_WITH_ID);

        return uriMatcher;

    }

    // Member variable for a DPChallengesDbHelper that's initialized in the onCreate() method
    private DPChallengesDbHelper mDPCDbHelper;


    /* onCreate() is where you should initialize anything you’ll need to setup
    your underlying data source.
    In this case, you’re working with a SQLite database, so you’ll need to
    initialize a DbHelper to gain access to it.
     */
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDPCDbHelper = new DPChallengesDbHelper(context);
        return true;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // Get access to the Challenges database (to write new data to)
        final SQLiteDatabase db = mDPCDbHelper.getWritableDatabase();

        // Write URI matching code to identify the match for the challenges directory
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case CHALLENGES:
                // Inserting values into Challenges table
                long id = db.insert(TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(ChallengeEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            // TODO: add CHALLENGE_WITH_ID insert
            // Set the value for the returnedUri and write the default case for unknown URI's
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }


    // Implement query to handle requests for data by URI
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Get access to underlying database (read-only for query)
        final SQLiteDatabase db = mDPCDbHelper.getReadableDatabase();

        // Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        // Query for the challenges directory and write a default case
        switch (match) {
            // Query for the challenges directory
            case CHALLENGES:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Query for the challenges with a specific id
            case CHALLENGE_WITH_ID:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;
    }


    // Implement delete to delete a single row of data
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        // Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase db = mDPCDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Keep track of the number of deleted challenges
        int challengesDeleted; // starts as 0

        // Write the code to delete a single row of data
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case CHALLENGE_WITH_ID:
                // Get the challenge ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                challengesDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items deleted
        if (challengesDeleted != 0) {
            // A challenge was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of challenges deleted
        return challengesDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        // Keep track of if an update occurs
        int challengesUpdated;

        // match code
        int match = sUriMatcher.match(uri);


        switch (match) {
            case CHALLENGE_WITH_ID:
                // Update a single task by getting the id
                String id = uri.getPathSegments().get(1);
                // Using selections
                challengesUpdated = mDPCDbHelper.getWritableDatabase().update(TABLE_NAME, values, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }

        if (challengesUpdated != 0) {
            // Set notifications if a challenge was updated
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return number of challenges updated
        return challengesUpdated;
    }


    @Override
    public String getType(@NonNull Uri uri) {

        // match code
        int match = sUriMatcher.match(uri);

        switch (match) {
            case CHALLENGES:
                // Directory
                return "vnd.android.cursor.dir" + "/" + DPChallengesContract.AUTHORITY + "/" + DPChallengesContract.PATH_CHALLENGES;
            case CHALLENGE_WITH_ID:
                // Single challenge
                return "vnd.android.cursor.item" + "/" + DPChallengesContract.AUTHORITY + "/" + DPChallengesContract.PATH_CHALLENGES;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

}