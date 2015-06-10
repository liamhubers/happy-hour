package com.school.guidoschmitz.happyhours.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.school.guidoschmitz.happyhours.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends Activity implements View.OnClickListener {

    public static final String EXTRA_USERNAME = "profile";

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private Button loginButton;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        checkForLogin(AccessToken.getCurrentAccessToken());

        setAccessTokenTracker();
        setLoginManager();

        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
    }

    private void checkForLogin(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            //setUserName(currentAccessToken);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(EXTRA_USERNAME, username);
            startActivity(intent);
        }
    }

    private void setAccessTokenTracker() {
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                checkForLogin(newAccessToken);
            }
        };
    }

//    private void setUserName(AccessToken accessToken) {
//        GraphRequest.newMeRequest(accessToken,
//            new GraphRequest.GraphJSONObjectCallback() {
//                @Override
//                public void onCompleted(JSONObject object, GraphResponse response) {
//                    try {
//                        username = object.getString("name");
//                    }
//                    catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).executeAsync();
//    }

    private void setLoginManager() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Profile profile = Profile.getCurrentProfile();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //intent.putExtra(EXTRA_USERNAME, profile.getName());
                startActivity(intent);
            }

            @Override
            public void onCancel() { }

            @Override
            public void onError(FacebookException e) { }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
