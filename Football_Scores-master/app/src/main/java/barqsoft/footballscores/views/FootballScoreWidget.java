package barqsoft.footballscores.views;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import barqsoft.footballscores.R;
import barqsoft.footballscores.activities.MainActivity;
import barqsoft.footballscores.services.StackWidgetService;

/**
 * Created by angie.
 */
public class FootballScoreWidget extends AppWidgetProvider {
    private static String TAG = FootballScoreWidget.class.getSimpleName();

    public static final String OPEN_ACTIVITY_ACTION = "barqsoft.footballscores.widgets.OPEN_ACTIVITY_ACTION";
    public static final String EXTRA_MATCH_ID = "barqsoft.footballscores.widgets.EXTRA_MATCH_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(OPEN_ACTIVITY_ACTION)) {
            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
        super.onReceive(context, intent);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate");

        for (int i=0; i<appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, StackWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setRemoteAdapter(appWidgetId, R.id.stack_view, intent);
            views.setEmptyView(R.id.stack_view, R.id.empty_view);

            Intent toastIntent = new Intent(context, FootballScoreWidget.class);
            toastIntent.setAction(FootballScoreWidget.OPEN_ACTIVITY_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}