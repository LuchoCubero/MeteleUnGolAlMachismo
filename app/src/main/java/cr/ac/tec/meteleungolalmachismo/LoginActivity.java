package cr.ac.tec.meteleungolalmachismo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONObject;

import static com.facebook.FacebookSdk.getApplicationContext;

public final class LoginActivity extends AppCompatActivity {

    public TwitterManager twitterManager;
    private CallbackManager FBcallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //FacebookLogin
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_buttonFacebook);
        loginButton.setReadPermissions("public_profile");
        FBcallbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(FBcallbackManager, getFacebookCallBack());

        //APIs initialize
        AppEventsLogger.activateApp(this);
        Twitter.initialize(this);

        //Manager start
        twitterManager = new TwitterManager();

        twitterManager.loginTwitter((TwitterLoginButton)findViewById(R.id.login_buttonTwitter));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FBcallbackManager.onActivityResult(requestCode, resultCode, data); //Facebook Callback
        if (twitterManager.getTwitterButton() != null) {
            twitterManager.getTwitterButton().onActivityResult(requestCode, resultCode, data);
        }
    }

    public FacebookCallback<LoginResult> getFacebookCallBack() {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                //Some code here
                final String[] username = new String[1];
                GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            // Application code
                            try {
                                username[0] = object.getString("name");
                                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                                main.putExtra("username", username[0]);
                                LoginActivity.this.startActivity(main);
                                Toast.makeText(LoginActivity.this, "OKOKOK", Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Log.v("LoginActivity", e.toString());
                            }
                            Log.v("LoginActivity", response.toString());
                        }

                    });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                //Some code here
            }

            @Override
            public void onError(FacebookException exception) {
                //Some code here
            }
        };
    }
}
