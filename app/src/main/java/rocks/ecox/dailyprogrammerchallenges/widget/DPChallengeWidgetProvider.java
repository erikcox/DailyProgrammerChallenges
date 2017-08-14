package rocks.ecox.dailyprogrammerchallenges.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;

import rocks.ecox.dailyprogrammerchallenges.R;
import rocks.ecox.dailyprogrammerchallenges.activities.SplashActivity;
import rocks.ecox.dailyprogrammerchallenges.adapters.ChallengeCursorAdapter;
import rocks.ecox.dailyprogrammerchallenges.utility.UpdateChallenges;

import static rocks.ecox.dailyprogrammerchallenges.models.challenge.Challenge.getCount;

/**
 * Implementation of App Widget functionality.
 */
public class DPChallengeWidgetProvider extends AppWidgetProvider implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        ChallengeCursorAdapter mAdapter;
        RecyclerView mRecyclerView;

        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.dpchallenge_widget_provider);
            Intent configIntent = new Intent(context, SplashActivity.class);

            mAdapter = new ChallengeCursorAdapter(context);
            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewChallenges);
            mRecyclerView.setLayoutManager(
                    new LinearLayoutManager(context));
            mRecyclerView.setAdapter(mAdapter);

            getLoaderManager().initLoader(0, null, this);

            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);

            remoteViews.setOnClickPendingIntent(R.id.widget, configPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        }
    }

    private LoaderManager getLoaderManager()
    {
        return null;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when a widget is enabled
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {

    }
}

