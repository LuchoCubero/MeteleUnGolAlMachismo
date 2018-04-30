package cr.ac.tec.meteleungolalmachismo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.FacebookCallback;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONObject;
import static com.facebook.FacebookSdk.getApplicationContext;


public class FacebookManager{

    private CallbackManager callbackManager;
    private AccessToken accessToken;

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public FacebookManager()
    {
        callbackManager = CallbackManager.Factory.create();
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public void setCallbackManager(CallbackManager manager)
    {
        this.callbackManager = manager;
    }

    public void loginFacebook(final LoginButton loginButton) {
        try{
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    loginButton.setReadPermissions("public_profile");
                    // Callback registration
                    loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            final String[] username = new String[1];
                            GraphRequest request = GraphRequest.newMeRequest(
                                    loginResult.getAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(
                                                JSONObject object,
                                                GraphResponse response) {
                                            // Application code
                                            try{
                                                username[0] = object.getString("name");
                                                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                                main.putExtra("username", username[0]);
                                                getApplicationContext().startActivity(main);
                                            }
                                            catch(Exception e)
                                            {

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
                            // App code
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            // App code
                            Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "A ocurrido un error", Toast.LENGTH_LONG).show();
        }
    }

    public void logout() //Not working
    {
        if(accessToken!=null)
        {
            LoginManager.getInstance().logOut();
            Toast.makeText(getApplicationContext(), "A ocurrido un error", Toast.LENGTH_LONG).show();
        }
    }
}
