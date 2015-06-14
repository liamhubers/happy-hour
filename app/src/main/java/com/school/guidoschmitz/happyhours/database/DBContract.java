package com.school.guidoschmitz.happyhours.database;

import android.provider.BaseColumns;

public class DBContract {
    public static class Location implements BaseColumns {
        public static final String TABLE = "locations";

        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String ADDRESS = "address";
        public static final String THUMBNAIL = "thumbnail";
        public static final String LAT = "lat";
        public static final String LON = "lon";

        public static final String SORT_ORDER_DEFAULT = NAME + " DESC";
    }
}
