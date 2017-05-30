package rocks.ecox.dailyprogrammerchallenges.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.RemoteViews;

import rocks.ecox.dailyprogrammerchallenges.R;
import rocks.ecox.dailyprogrammerchallenges.activities.SplashActivity;
import rocks.ecox.dailyprogrammerchallenges.utility.UpdateChallenges;

import static rocks.ecox.dailyprogrammerchallenges.models.challenge.Challenge.getCount;

/**
 * Implementation of App Widget functionality.
 */
public class DPChallengeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = "0"; //context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.dpchallenge_widget_provider);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final CharSequence[] widgetText = {"0"};
        // Check # of entries in db
        final int numChallenges = getCount();

        // Run UpdateChallenges
        UpdateChallenges.update();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                final int currentCount = getCount();
                Runnable r = new Runnable() { public void run() { widgetText[0] = String.valueOf(currentCount - numChallenges); } };
                r.run();
            }
        }, 30000);

        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.dpchallenge_widget_provider);
            Intent configIntent = new Intent(context, SplashActivity.class);

            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);

            remoteViews.setOnClickPendingIntent(R.id.widget, configPendingIntent);
            remoteViews.setTextViewText(R.id.appwidget_text, widgetText[0]);
            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when a widget is enabled
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

