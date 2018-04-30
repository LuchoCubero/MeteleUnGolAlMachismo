package cr.ac.tec.meteleungolalmachismo;

import android.content.Intent;
import android.os.AsyncTask;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import static com.facebook.FacebookSdk.getApplicationContext;

public class TwitterManager {

    private TwitterLoginButton TwitterButton;

    public TwitterLoginButton getTwitterButton() {
        return TwitterButton;
    }

    public void setTwitterButton(TwitterLoginButton twitterButton) {
        TwitterButton = twitterButton;
    }

    public void loginTwitter(final TwitterLoginButton button )
    {
        try{
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    TwitterButton = button;
                    TwitterButton.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
                        @Override
                        public void success(Result<TwitterSession> result){
                            // ... do something
                            TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                            TwitterAuthToken authToken = session.getAuthToken();
                            String token = authToken.token;
                            String secret = authToken.secret;
                            String username = session.getUserName();
                            Intent main = new Intent(getApplicationContext(), MainActivity.class);
                            main.putExtra("username", username);
                            getApplicationContext().startActivity(main);
                        }

                        @Override
                        public void failure(TwitterException exception) {
                            // ... do something
                            Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
        catch (Exception e)
        {

        }

    }

    public void logout()
    {
         //Empty
    }

}
