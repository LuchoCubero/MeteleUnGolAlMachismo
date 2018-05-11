package cr.ac.tec.meteleungolalmachismo;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Twitter;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_name);

        Intent intent = getIntent();
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
                            //LOGOUT TO DO
                            TwitterManager tw = new TwitterManager();
                            tw.logout();
                            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                            getApplicationContext().startActivity(login);
                        }
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return true;
                    }
                });

        View header = navigationView.getHeaderView(0);

        TextView tvuser = (TextView)header.findViewById(R.id.headerTV);
        tvuser.setText(username);
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

}
