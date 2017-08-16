package rocks.ecox.dailyprogrammerchallenges.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MyWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}