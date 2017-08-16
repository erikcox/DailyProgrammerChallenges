package rocks.ecox.dailyprogrammerchallenges.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import rocks.ecox.dailyprogrammerchallenges.R;
import rocks.ecox.dailyprogrammerchallenges.activities.SplashActivity;
import rocks.ecox.dailyprogrammerchallenges.adapters.ChallengeCursorAdapter;
import rocks.ecox.dailyprogrammerchallenges.data.DPChallengesContract;
import rocks.ecox.dailyprogrammerchallenges.utility.UpdateChallenges;
import timber.log.Timber;

import static rocks.ecox.dailyprogrammerchallenges.models.challenge.Challenge.getCount;

/**
 * Implementation of App Widget functionality.
 */
public class DPChallengeWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_DETAILS_ACTIVITY = "ACTION_DETAILS_ACTIVITY";
    public static final String EXTRA_SYMBOL = "SYMBOL";
    private static final String TAG = "SimpleAppWidgetProvider";
    private static final String REFRESH_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        String sortQuery = "";
        String sortBy = "DESC";

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(

                    context.getPackageName(),
                    R.layout.dpchallenge_widget_provider

            );

//            Cursor cursor = context.getContentResolver().query(
//                    DPChallengesContract.BASE_CONTENT_URI,
//                    new String[]{"count(*)"},
//                    null,
//                    null,
//                    null
//            );

            final Cursor cursor = context.getContentResolver().query(DPChallengesContract.ChallengeEntry.CONTENT_URI,
                    null,
                    "show_challenge = 1 AND completed_challenge = 0" + sortQuery,
                    null,
                    DPChallengesContract.ChallengeEntry.COLUMN_CHALLENGE_NUM + " " + sortBy + ", "
                            + DPChallengesContract.ChallengeEntry.COLUMN_DIFFICULTY_NUM + " ASC");


            if(cursor != null) {
                cursor.moveToFirst();

                views.setTextViewText(R.id.tv_widgetChallenge, context.getString(R.string.title_programming_challenges));
                
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }

            cursor.close();
        }

    }
}

