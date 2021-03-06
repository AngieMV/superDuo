package barqsoft.footballscores.receivers;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import barqsoft.footballscores.data.DatabaseContract;

/**
 * Created by angie.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = AlarmReceiver.class.getSimpleName();

    public static final String ACCOUNT = "dummyaccount";

    public static final String ACCOUNT_TYPE = "barqsoft.footballscores.datasync";

    private ContentResolver contentResolver;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Alarm fire to download the football scores.");
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);

        ContentResolver.requestSync(newAccount, DatabaseContract.CONTENT_AUTHORITY, settingsBundle);

    }
}