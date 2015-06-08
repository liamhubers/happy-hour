package com.school.guidoschmitz.happyhours.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.school.guidoschmitz.happyhours.R;

public class LoginActivity extends FragmentActivity {

    private static final String TAG = "FacebookLogin";

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        this.callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "Succeed");
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "Cancelled");
            }

            @Override
            public void onError(FacebookException e) {
                Log.i(TAG, "Something went wrong");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.callbackManager.onActivityResult(requestCode, resultCode, data);

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}
