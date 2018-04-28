package cr.ac.tec.meteleungolalmachismo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.FacebookCallback;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import static com.facebook.FacebookSdk.getApplicationContext;



public class FacebookManager{

    private CallbackManager callbackManager;

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
                    loginButton.setReadPermissions("email");

                    // Callback registration
                    loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            String username = loginResult.getAccessToken().getUserId();
                            Intent main = new Intent(getApplicationContext(), MainActivity.class);
                            main.putExtra("username", username);
                            getApplicationContext().startActivity(main);
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

}
