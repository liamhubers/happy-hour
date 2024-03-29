package com.school.guidoschmitz.happyhours.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.school.guidoschmitz.happyhours.Receiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends Activity {

    public static final String EXTRA_USERNAME = "name";
    public static final String EXTRA_PROFILE_ID = "id";

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

        //getHash();

        //loginButton = (Button) findViewById(R.id.login_button);
        //loginButton.setOnClickListener(this);
    }

    private void getHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.school.guidoschmitz.happyhours", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void checkForLogin(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            setUsername(currentAccessToken);
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

    private void setUsername(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
            accessToken,
            new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        Log.v("LoginActivity", response.toString());
                        username = object.getString("name");

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(EXTRA_USERNAME, username);
                        intent.putExtra(EXTRA_PROFILE_ID, object.getString("id"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    catch (Exception e) {
                        Log.d("Request", e.getMessage());
                    }
                }
            });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void setLoginManager() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                setUsername(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void loginWithFacebook(View v) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
    }

    public void loginWithoutFacebook(View v) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
