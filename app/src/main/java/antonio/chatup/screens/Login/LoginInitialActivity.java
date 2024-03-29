package antonio.chatup.screens.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import antonio.chatup.R;
import antonio.chatup.data.ChatupSingleton;
import antonio.chatup.data.HTTP_Directories;
import antonio.chatup.data.HTTP_Methods;
import antonio.chatup.data.Requests;
import antonio.chatup.screens.ViewRooms.MainActivity;

public class LoginInitialActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login_initial);

        configFacebook();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,
                resultCode, data);
    }

    private void configFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String email = object.getString("email");
                                    accessServer(email, loginResult);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Log.e("cancel", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e("erro", exception.getMessage());
            }
        });
    }

    private void accessServer(final String email, final LoginResult loginResult) {
        Thread thr = new Thread(new Runnable() {
            public void run() {
                final ChatupSingleton chatup = ChatupSingleton.getInstance();

                final JSONObject obj;
                try {
                    String token = loginResult.getAccessToken().getToken().toString();

                    obj = chatup.createJSON(Requests.USER_LOGIN, "token", token);
                    JSONObject response = chatup.get(HTTP_Directories.USER_SERVICE, HTTP_Methods.POST, obj);

                    if (response != null && response.has("success")) {
                        chatup.set(email, response.getJSONObject("success").getString("token"));

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                } catch (JSONException e) {
                    Log.e("LoginInitialActivity", "Invalid arguments", e);
                }
            }
        });

        //thr.start();

        ChatupSingleton.getInstance().set("email", "token");

        //TODO Delete these 3 lines
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}
