package cr.ac.tec.meteleungolalmachismo;

import android.util.Log;
import org.json.JSONObject;

public class Competition {

    private String league_id;
    private String league_name;
    private String country_id;
    private String country_name;
    private JSONObject dataCompetition;

    public Competition(JSONObject dataCompetition)
    {
        try
        {
            this.dataCompetition = dataCompetition;
            this.league_id = dataCompetition.getString("_id");
            this.league_name = dataCompetition.getString("name");
            this.country_id = dataCompetition.getString("id_country");
            this.country_name = dataCompetition.getString("country_name");
        }
        catch (Exception e)
        {
            Log.e("MATCH", "Error is match constructor. Error: " + e.toString());
        }
    }

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }

    public String getLeague_id() {
        return league_id;
    }

    public void setLeague_id(String league_id) {
        this.league_id = league_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }
}
