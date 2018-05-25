package cr.ac.tec.meteleungolalmachismo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
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
    ArrayList<Object> datos;


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
                        Toast.makeText(getApplicationContext(), "OKOKOK", Toast.LENGTH_LONG).show();

                    }
                    else if(String.valueOf(menuItem.getItemId()).equals(String.valueOf(R.id.logout)))
                    {

//                        Intent login = new Intent(MainActivity.this, LoginActivity.class);
//                        MainActivity.this.startActivity(login);
                    }
                    // Add code here to update the UI based on the item selected
                    // For example, swap UI fragments here
                    return true;
                }
            });

        View header = navigationView.getHeaderView(0);

        TextView tvuser = (TextView)header.findViewById(R.id.headerTV);
        tvuser.setText(username);


        lv = (ListView) findViewById(R.id.list);
        datos = new ArrayList<>();
        new GetMatches().execute();
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

    private class GetMatches extends AsyncTask<Void, Void, Void>{

        private String APIkey = "3e9154281f99813c6a45c9ffaab8bf2e9f87b0b3e79f81296c461df25d2df54c";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                //Read JSON response and print
                JSONArray paises = new JSONArray(getPaises());
                Log.e("RESPONSE", "Response from url: " + paises.toString());

                for(int a = 0; a < paises.length(); a++) //GetLigas
                {
                    JSONObject jsonPaises = paises.getJSONObject(a); //Json con country_id y country_name
                    int pais = jsonPaises.getInt("country_id");
                    Log.e("RESPONSE", String.valueOf(pais));
                    JSONArray ligas = new JSONArray(getLigaPorPais(pais));
                    for(int b = 0; b < ligas.length(); b++) //GetPartidos
                    {
                        Competition competition = new Competition(ligas.getJSONObject(b));
                        String liga = competition.getLeague_id();
                        datos.add(competition);

                        JSONArray partidos = new JSONArray(getPartidos(Integer.valueOf(liga)));
                        for(int c = 0; c < partidos.length(); c++)
                        {
                            Match match = new Match(partidos.getJSONObject(c));
                            datos.add(match);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Log.e("RESPONSE", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            lv.setAdapter(new CompetitionAdapter(MainActivity.this, datos));
        }

        public String getPaises(){
            try
            {
                String url = "https://apifootball.com/api/?action=get_countries&APIkey=" + APIkey;
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
            catch (Exception e) //Falta manejar bien el error.
            {
                Log.e("RESPONSE", e.getMessage());
                return "";
            }
        }

        public String getLigaPorPais(int pais){
            try
            {
                String url = "https://apifootball.com/api/?action=get_leagues&country_id="+ pais +"&APIkey=" + APIkey;
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
            catch (Exception e) //Falta manejar bien el error.
            {
                Log.e("RESPONSE", e.getMessage());
                return "";
            }
        }

        public String getPartidos(int liga)
        {
            LocalDate date = LocalDate.now();
            LocalDate daysAgo = LocalDate.now().minusDays(60);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String inicioFecha = date.format(formatter);
            String finalFecha = daysAgo.format(formatter);
            Log.e("TIME",finalFecha);
            Log.e("TIME",inicioFecha);
            try
            {
                //String url = "https://apifootball.com/api/?action=get_events&from="+inicioFecha+"&to="+finalFecha+"&league_id="+liga+"&APIkey=" + APIkey;
                String url = "https://apifootball.com/api/?action=get_events&from=2018-02-21&to=2018-03-21&league_id=63&APIkey=3e9154281f99813c6a45c9ffaab8bf2e9f87b0b3e79f81296c461df25d2df54c"; //Just for testing
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
            catch (Exception e) //Falta manejar bien el error.
            {
                Log.e("RESPONSE", e.getMessage());
                return "";
            }
        }
    }

}
