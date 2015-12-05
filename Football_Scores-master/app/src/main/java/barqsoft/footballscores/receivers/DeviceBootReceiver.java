package barqsoft.footballscores.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import barqsoft.footballscores.utils.FootballScoreAlarmManager;

/**
 * Created by angie.
 */
public class DeviceBootReceiver extends BroadcastReceiver {
    private static final String TAG = DeviceBootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Boot completed, enabling AlarmManger to update football scores");
        FootballScoreAlarmManager.setScoresUpdateEveryHour(context);
    }
}
