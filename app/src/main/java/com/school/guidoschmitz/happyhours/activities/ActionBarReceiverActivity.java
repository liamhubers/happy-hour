package com.school.guidoschmitz.happyhours.activities;

import android.support.v7.app.ActionBarActivity;
import com.school.guidoschmitz.happyhours.Receiver;

public class ActionBarReceiverActivity extends ActionBarActivity {
    protected Receiver receiver;

    @Override
    protected void onStop()
    {
        try {
            unregisterReceiver(receiver);
        } catch(Exception e) {

        }
        super.onStop();
    }
}
