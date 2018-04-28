package cr.ac.tec.meteleungolalmachismo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import static com.facebook.FacebookSdk.getApplicationContext;

public class LoginActivity extends AppCompatActivity {

    public FacebookManager facebokManager;
    public TwitterManager twitterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //APIs initialize
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Twitter.initialize(this);

        //Manager start
        facebokManager = new FacebookManager();
        twitterManager = new TwitterManager();

        setContentView(R.layout.activity_login);

        twitterManager.loginTwitter((TwitterLoginButton)findViewById(R.id.login_buttonTwitter));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebokManager.getCallbackManager().onActivityResult(requestCode, resultCode, data);

        if (twitterManager.getTwitterButton() != null) {
            twitterManager.getTwitterButton().onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onFacebookClick(View view)
    {
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_buttonFacebook);
        facebokManager.loginFacebook(loginButton);
    }
}
