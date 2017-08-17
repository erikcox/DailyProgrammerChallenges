package rocks.ecox.dailyprogrammerchallenges.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import rocks.ecox.dailyprogrammerchallenges.R;
import rocks.ecox.dailyprogrammerchallenges.activities.DetailActivity;
import rocks.ecox.dailyprogrammerchallenges.activities.MainActivity;
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

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // Parameters for cursor query
        String sortQuery = "";
        String sortBy = "DESC";

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(

                    context.getPackageName(),
                    R.layout.dpchallenge_widget_provider

            );

            // Old cursor code
//            Cursor cursor = context.getContentResolver().query(
//                    DPChallengesContract.BASE_CONTENT_URI,
//                    new String[]{"count(*)"},
//                    null,
//                    null,
//                    null
//            );

            // Newer cursor code
//            final Cursor cursor = context.getContentResolver().query(DPChallengesContract.ChallengeEntry.CONTENT_URI,
//                    null,
//                    "show_challenge = 1 AND completed_challenge = 0" + sortQuery,
//                    null,
//                    DPChallengesContract.ChallengeEntry.COLUMN_CHALLENGE_NUM + " " + sortBy + ", "
//                            + DPChallengesContract.ChallengeEntry.COLUMN_DIFFICULTY_NUM + " ASC");
//
//
//            if(cursor != null) {
//                cursor.moveToFirst();
//            }
//
//            cursor.close();

            // Click event handler for the title, launches the app when the user clicks on title
            Intent titleIntent = new Intent(context, MainActivity.class);
            PendingIntent titlePendingIntent = PendingIntent.getActivity(context, 0, titleIntent, 0);
            views.setOnClickPendingIntent(R.id.tv_widgetChallenge, titlePendingIntent);


            Intent intent = new Intent(context, MyWidgetRemoteViewsService.class);
            views.setRemoteAdapter(R.id.widgetListView, intent);

            // template to handle the click listener for each item
            Intent clickIntentTemplate = new Intent(context, DetailActivity.class);
            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntentTemplate)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widgetListView, clickPendingIntentTemplate);

            appWidgetManager.updateAppWidget(appWidgetId, views);

//            Intent intent = new Intent(context, MyWidgetRemoteViewsService.class);
            views.setRemoteAdapter(R.id.widgetListView, intent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, DPChallengeWidgetProvider.class));
        context.sendBroadcast(intent);
    }


    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // Refresh all  widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, DPChallengeWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widgetListView);
        }
        super.onReceive(context, intent);
    }
}
