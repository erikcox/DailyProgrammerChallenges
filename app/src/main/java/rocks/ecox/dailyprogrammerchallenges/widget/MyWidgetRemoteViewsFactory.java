package rocks.ecox.dailyprogrammerchallenges.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import rocks.ecox.dailyprogrammerchallenges.R;
import rocks.ecox.dailyprogrammerchallenges.data.DPChallengesContract;
import timber.log.Timber;

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;

    public MyWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        if (mCursor != null) {
            mCursor.close();
        }

        final long identityToken = Binder.clearCallingIdentity();
        Uri uri = DPChallengesContract.ChallengeEntry.CONTENT_URI;
        mCursor = mContext.getContentResolver().query(uri,
                null,
                null,
                null,
                DPChallengesContract.ChallengeEntry.COLUMN_POST_ID + " DESC");

        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null || !mCursor.moveToPosition(position)) {
            return null;
        }

        String challengeLabel = mCursor.getString(3) + ": [" + mCursor.getString(1) + "] " + mCursor.getString(4);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.dpchallenge_widget_list_item);
        rv.setTextViewText(R.id.widgetItemChallengeNameLabel, challengeLabel);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return mCursor.moveToPosition(position) ? mCursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
