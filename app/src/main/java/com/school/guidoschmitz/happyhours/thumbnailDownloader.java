package com.school.guidoschmitz.happyhours;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.school.guidoschmitz.happyhours.models.Location;
import com.school.guidoschmitz.happyhours.models.RoundImage;

import java.net.URL;

/**
 * Created by Liam Hubers on 17-6-2015.
 */
public class thumbnailDownloader extends AsyncTask<String, String, Bitmap> {

    private ImageView thumbnail;
    private Location location;

    public thumbnailDownloader(ImageView thumbnail, Location location) {
        this.thumbnail = thumbnail;
        this.location = location;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            URL url = new URL(location.getThumbnail());
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(Exception e) { }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
        thumbnail.setImageDrawable(new RoundImage(bmp));
    }
}
