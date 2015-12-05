package barqsoft.footballscores.services;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import barqsoft.footballscores.R;
import barqsoft.footballscores.adapters.ScoresAdapter;
import barqsoft.footballscores.data.DatabaseContract;
import barqsoft.footballscores.models.Widget;
import barqsoft.footballscores.utils.Utilies;
import barqsoft.footballscores.views.FootballScoreWidget;

/**
 * Created by clerks on 9/20/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Widget> mWidgetItems = new ArrayList<Widget>();
    private Context mContext;
    private int mAppWidgetId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {

        Date fragmentdate = new Date(System.currentTimeMillis());
        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
        String[] selectionArgs = new String[1];
        selectionArgs[0] = mformat.format(fragmentdate);

        Cursor cursor = mContext.getContentResolver().query(DatabaseContract.scores_table.buildScoreWithDate(),
                null, null, selectionArgs, null);

        while (cursor.moveToNext()) {
            mWidgetItems.add(new Widget(cursor.getString(ScoresAdapter.COL_HOME),
                    cursor.getString(ScoresAdapter.COL_AWAY),
                    cursor.getInt(ScoresAdapter.COL_HOME_GOALS),
                    cursor.getInt(ScoresAdapter.COL_AWAY_GOALS)));
        }
        cursor.close();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        mWidgetItems.clear();
    }

    public int getCount() {
        return mWidgetItems.size();
    }

    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.football_score_widget);

        Widget item = mWidgetItems.get(position);
        views.setTextViewText(R.id.homeNameTextView, item.getHome());
        String score = Utilies.getScores(item.getHomeGoals(), item.getAwayGoals());
        views.setTextViewText(R.id.scoreTextView, score);
        views.setTextViewText(R.id.awayNameTextView, item.getAway());

        Bundle extras = new Bundle();
        extras.putInt(FootballScoreWidget.EXTRA_MATCH_ID, position);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        views.setOnClickFillInIntent(R.id.homeNameTextView, fillInIntent);


        return views;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
    }
}
