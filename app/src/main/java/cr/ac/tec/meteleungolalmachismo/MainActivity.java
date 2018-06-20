package cr.ac.tec.meteleungolalmachismo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView lv;
    ArrayList<Object> data = null;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_name);

        final Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        mDrawerLayout = findViewById(R.id.drawer_layout);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
                    // close drawer when item is tapped
                    mDrawerLayout.closeDrawers();
                    //Toast.makeText(getApplicationContext(), String.valueOf(menuItem.getItemId()), Toast.LENGTH_LONG).show();

                    if(String.valueOf(menuItem.getItemId()).equals(String.valueOf(R.id.pref)))
                    {
                        Intent main = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(main);

                    }
                    else if(String.valueOf(menuItem.getItemId()).equals(String.valueOf(R.id.logout)))
                    {
                        LoginActivity loginActivity = new LoginActivity();
                        if(loginActivity.logOut())
                        {
                            Intent login = new Intent(MainActivity.this, LoginActivity.class);
                            MainActivity.this.startActivity(login);
                        }
//                         loginActivity.logOut();
//                         Intent login = new Intent(MainActivity.this, LoginActivity.class);
//                         MainActivity.this.startActivity(login);

                    }
                    // Add code here to update the UI based on the item selected
                    // For example, swap UI fragments here
                    return true;
                }
            });

        View header = navigationView.getHeaderView(0);

        TextView tvuser = (TextView)header.findViewById(R.id.headerTV);
        tvuser.setText(username);

        if(data == null)
        {
            lv = (ListView) findViewById(R.id.list);
            data = new ArrayList<>();
            new GetMatches().execute();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        filter();
    }

    private void filter()
    {
        SharedPreferences prefs =PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean firstdivisiona = prefs.getBoolean("firstdivisiona", false);
        boolean champioship = prefs.getBoolean("champioship", false);
        boolean facup = prefs.getBoolean("facup", false);
        boolean league1 = prefs.getBoolean("league1", false);
        boolean premierleague = prefs.getBoolean("premierleague", false);
        boolean ligue1 = prefs.getBoolean("ligue1", false);
        boolean bundesliga1 = prefs.getBoolean("1bundesliga", false);
        boolean bundesliga2 = prefs.getBoolean("2bundesliga", false);
        boolean championslegue = prefs.getBoolean("championslegue", false);
        boolean europaleague = prefs.getBoolean("europaleague", false);
        boolean worldcup = prefs.getBoolean("worldcup", false);
        boolean coppaitalia = prefs.getBoolean("coppaitalia", false);
        boolean seriea = prefs.getBoolean("seriea", false);
        boolean serieb = prefs.getBoolean("serieb", false);
        boolean eredivise = prefs.getBoolean("eredivise", false);
        boolean primeradivision = prefs.getBoolean("primeradivision", false);
        boolean segundab = prefs.getBoolean("segundab", false);

        ArrayList<Object> filtereddata = new ArrayList<>();
        if(firstdivisiona)
        {
            filtereddata.addAll(filterarray("139"));
        }

        if(champioship)
        {
            filtereddata.addAll(filterarray("12"));
        }

        if(facup)
        {
            filtereddata.addAll(filterarray("2"));
        }

        if(league1)
        {
            filtereddata.addAll(filterarray("6"));
        }

        if(premierleague)
        {
            filtereddata.addAll(filterarray("8"));
        }

        if(ligue1)
        {
            filtereddata.addAll(filterarray("123"));
        }

        if(bundesliga1)
        {
            filtereddata.addAll(filterarray("108"));
        }

        if(bundesliga2)
        {
            filtereddata.addAll(filterarray("107"));
        }

        if(championslegue)
        {
            filtereddata.addAll(filterarray("34"));
        }

        if(europaleague)
        {
            filtereddata.addAll(filterarray("55"));
        }

        if(worldcup)
        {
            filtereddata.addAll(filterarray("82"));
        }

        if(coppaitalia)
        {
            filtereddata.addAll(filterarray("99"));
        }

        if(seriea)
        {
            filtereddata.addAll(filterarray("101"));
        }

        if(serieb)
        {
            filtereddata.addAll(filterarray("98"));
        }

        if(eredivise)
        {
            filtereddata.addAll(filterarray("145"));
        }

        if(primeradivision)
        {
            filtereddata.addAll(filterarray("129"));
        }

        if(segundab)
        {
            filtereddata.addAll(filterarray("130"));
        }

        lv.setAdapter(new CompetitionAdapter(MainActivity.this, filtereddata));
    }

    private ArrayList<Object> filterarray(String code)
    {
        ArrayList<Object> filtereddata = new ArrayList<>();
        for(int a = 0; a < data.size(); a++)
        {
            if(data.get(a) instanceof Competition)
            {
                if(((Competition) data.get(a)).getLeague_id().equals(code))
                {
                    filtereddata.add(data.get(a));
                    for(int b = a+1; b < data.size(); b++)
                    {
                        if(data.get(b) instanceof Competition)
                        {
                            break;
                        }
                        else
                        {
                            filtereddata.add(data.get(b));
                        }
                    }
                    break;
                }
            }
        }
        return filtereddata;
    }

    private class GetMatches extends AsyncTask<Void, Void, Void>{

        private String refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI1YWRkMTRiNzI3OTNiYjU2ODI2NTg3NTQiLCJpYXQiOjE1Mjg5NTY1NjR9.JbeVKVxt92VGeN5XDiufW7Ti0qRm5xxyWkgCMZZ9wh4";
        private String accessToken = "";

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Downloading data, please wait.",Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            //lv.setAdapter(new CompetitionAdapter(MainActivity.this, data));
            filter();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Realiza el ciclo para ir pidiendo paises, ligas y partidos
            //El orden del arrayList importa, debe venir una liga y sus partidos debajo, de lo
            //contrarío puede no mostrar el orden correcto en la interfaz.
            try{
                accessToken = accessKey();
                JSONArray countries = getCountries();
                for(int a = 0; a < countries.length(); a++)
                {
                    JSONObject country = countries.getJSONObject(a);
                    JSONArray leagues = country.getJSONArray("leagues");
                    for(int b = 0; b < leagues.length(); b++)
                    {
                        JSONObject league = leagues.getJSONObject(b);
                        Log.e("LIGA", league.toString());
                        if(league.getInt("_id") != 83)
                        {
                            league.put("country_name", country.getString("name"));
                            Competition competition = new Competition(league);
                            data.add(competition);
                            JSONObject season = getLastSeason(league.getString("_id"));
                            JSONArray matches = getFixturesBySeason(season.getString("_id"));
                            for(int c = 0; c < matches.length(); c++)
                            {
                                JSONObject jsonMatch = matches.getJSONObject(c);
                                jsonMatch.put("league_name", competition.getLeague_name());
                                Match match = new Match(jsonMatch);
                                data.add(match);
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Log.e("RESPONSE Background", e.getMessage());
                return null;
            }
            return null;
        }

        private String accessKey()
        {
            //Para el uso del API se necesita generar un access token cada media hora por lo que
            // existe un método que lo realiza por nosotros (accessKey), para esta generación se
            // necesita enviar el refreshToken el cual es que se despliega en la cuenta creada de SportDeer.
            try
            {
                String url = "https://api.sportdeer.com/v1/accessToken?refresh_token=" + refreshToken;
                String response = getResponse(url);
                Log.e("RESPONSE", "Response from url: " + response.toString());
                JSONObject object = new JSONObject(response);
                return object.getString("new_access_token");
            }
            catch (Exception e)
            {
                Log.e("RESPONSE", e.getMessage());
                return null;
            }
        }

        private JSONArray getCountries()
        {
            //Saquen los países y ligas por país (getCountries),
            try
            {
                String url = "https://api.sportdeer.com/v1/countries?populate=leagues&access_token=";
                String response = getResponse(url);
                JSONObject object = new JSONObject(response);
                JSONArray data = object.getJSONArray("docs");
                return data;
            }
            catch (Exception e) //Falta manejar bien el error.
            {
                Log.e("RESPONSE", e.getMessage());
                return null;
            }
        }

        private JSONObject getLastSeason(String leagueID)
        {
            //Sacar la última temporada disponible por cada liga (getLastSeason)
            try
            {
                String url = "https://api.sportdeer.com/v1/leagues/"+leagueID+"/seasons?access_token=";
                String response = getResponse(url);
                JSONObject object = new JSONObject(response);
                JSONArray data = object.getJSONArray("docs");
                int length = data.length()-1;
                JSONObject last = data.getJSONObject(length);
                if(last.has("is_last_season"))
                {
                    if(last.getString("is_last_season") == "true")
                    {
                        return last;
                    }
                }
                else //Weird, in case of failure
                {
                    for(int a = 0; a < length; a++)
                    {
                        JSONObject last2 = data.getJSONObject(a);
                        if(last2.has("is_last_season"))
                        {
                            if(last2.getString("is_last_season") == "true")
                            {
                                return last2;
                            }
                        }
                    }
                }
                return null;
            }
            catch (Exception e) //Falta manejar bien el error.
            {
                Log.e("RESPONSE getLastSeason", e.getMessage());
                return null;
            }
        }

        private JSONArray getFixturesBySeason(String seasonId)
        {
            //Sacando los encuentros disponibles en el rango de tiempo disponible (getFixturesBySeason)
            //De momento, usando 15 dias + y - del día actual.
            try
            {
                LocalDate date = LocalDate.now().plusDays(15);
                LocalDate daysAgo = LocalDate.now().minusDays(15);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dateFrom = daysAgo.format(formatter);
                String dateTo = date.format(formatter);
                Log.e("TIME",dateFrom);
                Log.e("TIME",dateTo);
                String url = "https://api.sportdeer.com/v1/seasons/"+seasonId+"/fixtures?dateFrom="+dateFrom+
                        "&dateTo="+dateTo+"&access_token=";
                String response = getResponse(url);
                JSONObject object = new JSONObject(response);
                JSONArray data = object.getJSONArray("docs");
                return data;
            }
            catch (Exception e) //Falta manejar bien el error.
            {
                Log.e("RESPONSE", e.getMessage());
                return null;
            }
        }

        private String getResponse(String url)
        {
            //Para recibir cada respuesta se usa el método (getResponse)
            // recibiendo el URL y devolviendo la respuesta en formato String.
            try
            {
                url = url + accessToken;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                Log.e("RESPONSE","Sending 'GET' request to URL : " + url);
                Log.e("RESPONSE","Response Code : " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            }
            catch (Exception e)
            {
                Log.e("RESPONSE", e.getMessage());
                return null;
            }
        }
    }

}
