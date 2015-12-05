package barqsoft.footballscores;

import barqsoft.footballscores.utils.FootballScoreAlarmManager;

/**
 * Created by angie on 12/5/15.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FootballScoreAlarmManager.setScoresUpdateEveryHour(this);
    }
}
