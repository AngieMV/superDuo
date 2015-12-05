package barqsoft.footballscores.models;

/**
 * Created by angie.
 */
public class Widget {
    private final String home;
    private final String away;
    private final int homeGoals;
    private final int awayGoals;

    public Widget(String home, String away, int homeGoals, int awayGoals) {
        this.home = home;
        this.away = away;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public String getHome() {
        return home;
    }

    public String getAway() {
        return away;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

}
