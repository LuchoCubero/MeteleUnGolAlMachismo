package cr.ac.tec.meteleungolalmachismo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import org.json.JSONObject;


public final class LoginActivity extends AppCompatActivity {

    private CallbackManager FBcallbackManager;
    private com.twitter.sdk.android.core.Callback<TwitterSession> TWcallbackManager;
    private TwitterLoginButton TWLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_login);

        //FacebookLogin
        LoginButton FBloginButton = (LoginButton) findViewById(R.id.login_buttonFacebook);
        FBloginButton.setReadPermissions("public_profile");
        FBcallbackManager = CallbackManager.Factory.create();
        FBloginButton.registerCallback(FBcallbackManager, getFacebookCallBack());

        //TwitterLogin
        TWLoginButton = (TwitterLoginButton) findViewById(R.id.login_buttonTwitter);
        TWcallbackManager = getTwitterCallback();
        TWLoginButton.setCallback(TWcallbackManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FBcallbackManager.onActivityResult(requestCode, resultCode, data); //Facebook Callback
        TWLoginButton.onActivityResult(requestCode, resultCode, data); //Twitter Callback
    }

    public FacebookCallback<LoginResult> getFacebookCallBack() {
        return new FacebookCallback<LoginResult>() {
            //Los métodos getFacebookCallBack y  getTwitterCallback son
            // los métodos que resuelven lo que sucede con las respuestas de los APIs.
            @Override
            public void onSuccess(final LoginResult loginResult) {
                final String[] username = new String[1];
                GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                            try {
                                username[0] = object.getString("name");
                                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                                main.putExtra("username", username[0]);
                                LoginActivity.this.startActivity(main);
                                Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_LONG).show();
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

    public com.twitter.sdk.android.core.Callback<TwitterSession> getTwitterCallback()
    {
        return new com.twitter.sdk.android.core.Callback<TwitterSession>() {
            //Los métodos getFacebookCallBack y  getTwitterCallback
            // son los métodos que resuelven lo que sucede con las respuestas de los APIs.
            @Override
            public void success(Result<TwitterSession> result){
                // ... do something
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                String username = session.getUserName();
                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                main.putExtra("username", username);
                LoginActivity.this.startActivity(main);
            }

            @Override
            public void failure(TwitterException exception) {
                // ... do something
                Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    public void onResume(){
        super.onResume();
        if(isLoggedInFB())
        {
            Intent main = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(main);
        }
    }

    public boolean isLoggedInFB() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public boolean logOut()
    {
        if(isLoggedInFB())
        {
            LoginManager.getInstance().logOut();
            return true;
        }
        else {
            return false;
        }

    }


}
