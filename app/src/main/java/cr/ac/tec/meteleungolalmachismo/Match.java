package cr.ac.tec.meteleungolalmachismo;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

public class Match {
    private JSONObject dataMatch;
    private String match_id;
    private String match_hometeam_name;
    private String match_awayteam_name;
    private String match_date;
    private String match_hometeam_score;
    private String match_awayteam_score;
    private String match_status;
    private String league_name;


    public Match(JSONObject dataMatch)
    {
        try{
            this.dataMatch = dataMatch;
            this.match_id = dataMatch.getString("_id");
            this.match_date = dataMatch.getString("schedule_date");
            this.match_hometeam_name = dataMatch.getString("team_season_home_name");
            this.match_awayteam_name = dataMatch.getString("team_season_away_name");
            this.match_hometeam_score = dataMatch.getString("number_goal_team_home");
            this.match_awayteam_score = dataMatch.getString("number_goal_team_away");
            this.match_status = dataMatch.getString("fixture_status");
            this.league_name = dataMatch.getString("league_name");
        }
        catch (Exception e)
        {
            Log.e("MATCH", "Error is match constructor. Error: " + e.toString());
        }

    }

    public String getMatch_status() {
        return match_status;
    }

    public void setMatch_status(String match_status) {
        this.match_status = match_status;
    }

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }

    public JSONObject getDataMatch() {
        return dataMatch;
    }

    public void setDataMatch(JSONObject dataMatch) {
        this.dataMatch = dataMatch;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getMatch_hometeam_name() {
        return match_hometeam_name;
    }

    public void setMatch_hometeam_name(String match_hometeam_name) {
        this.match_hometeam_name = match_hometeam_name;
    }

    public String getMatch_awayteam_name() {
        return match_awayteam_name;
    }

    public void setMatch_awayteam_name(String match_awayteam_name) {
        this.match_awayteam_name = match_awayteam_name;
    }

    public String getMatch_date() {
        return match_date;
    }

    public void setMatch_date(String match_date) {
        this.match_date = match_date;
    }



    public String getMatch_hometeam_score() {
        return match_hometeam_score;
    }

    public void setMatch_hometeam_score(String match_hometeam_score) {
        this.match_hometeam_score = match_hometeam_score;
    }

    public String getMatch_awayteam_score() {
        return match_awayteam_score;
    }

    public void setMatch_awayteam_score(String match_awayteam_score) {
        this.match_awayteam_score = match_awayteam_score;
    }

    public String getTime(){
        try
        {
            String date = this.dataMatch.getString("schedule_date");
            date = date.replace(".000Z", "");
            date = date.replace("2018-", "");
            return date;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
