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

    public static class Event implements BaseColumns {
        public static final String TABLE = "events";

        public static final String LOCATION_ID = "location_id";
        public static final String DESCRIPTION = "description";
        public static final String DAY_OF_WEEK = "day_of_week";
        public static final String START_TIME = "start_time";
        public static final String END_TIME = "end_time";

        public static final String SORT_ORDER_DEFAULT = DAY_OF_WEEK + " DESC, " + START_TIME + " DESC";
    }

    public static class Favorite implements BaseColumns {
        public static final String TABLE = "favorites";

        public static final String LOCATION_ID = "location_id";
    }
}
