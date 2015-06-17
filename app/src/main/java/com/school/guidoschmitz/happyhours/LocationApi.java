package com.school.guidoschmitz.happyhours;

import android.os.AsyncTask;
import android.util.Log;

import com.school.guidoschmitz.happyhours.database.DBContract;
import com.school.guidoschmitz.happyhours.fragments.MainFragment;
import com.school.guidoschmitz.happyhours.repositories.location.LocationRepository;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LocationApi extends AsyncTask<String, String, String> {

    private final String URL = "http://happy-hours.guidoschmitz.nl/locations";
    private LocationRepository repository;
    private MainFragment fragment;

    public LocationApi(LocationRepository repository, MainFragment fragment) {
        this.repository = repository;
        this.fragment = fragment;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;

        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(URL));

            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else{
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            Log.e("LocationApi", e.toString());
        }

        return responseString;
    }

    @Override
    protected void onPostExecute(String JSON) {
        try{
            JSONArray array = new JSONArray(JSON);

            repository.getDatabase().execSQL("DELETE FROM " + DBContract.Location.TABLE);

            for (int i = 0; i < array.length(); i++) {
                repository.save(repository.toLocation(new JSONObject(array.get(i).toString())));
            }

            this.fragment.setData();
        } catch(Exception e) {

        }
    }
}
