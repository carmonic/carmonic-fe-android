package com.camsys.carmonic.constants;

import com.google.android.gms.maps.GoogleMap;

public class Constants {

    // Shared pref mode
    public static final String USER_KEY = "userKey";
    public static  String TAG = "CarmonicLog";


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public final static   int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    public static final String API_KEYS = "" ;


    public  class  EndPoint{

        private static final String BACKEND_URL = "https://ec2-35-177-219-101.eu-west-2.compute.amazonaws.com:8443";
        public  static final String  Base_URL_local = "https://192.168.43.237:8443";//192.168.43.237
        public  static final String  Base_URL_remote =   "https://ec2-35-177-219-101.eu-west-2.compute.amazonaws.com:8443";

    }

    public  class  SharedDataCst{

        public static final int PRIVATE_MODE = 0;
        public static final String PREF_NAME = "customer";
        public static final String IS_LOG_IN = "IsLoggedIn";
        public static final String IS_LOG_OUT = "IsLoggedOut";
        public static final String USER_KEY = "userKey";
        public static final  String TAG = "CarmonicLog";

    }

    public class Prices {
        public static final int CHANGE_TYRE = 200;
        public static final int FIX_ENGINE = 500;
        public static final int CHANGE_WINDSHIELD = 300;
    }

    public class ServiceName {
        public static final String CHANGE_TYRE = "Tyre Change";
        public static final String FIX_ENGINE = "Engine Fix";
        public static final String CHANGE_WINDSHIELD = "Windshield Change";
    }

    public class Connections {
        public static final String IMTERNET_NOT_AVAILABLE = "Internet not available";
        public static final String FIX_ENGINE = "Engine Fix";
        public static final String CHANGE_WINDSHIELD = "Windshield Change";
    }



    public class LocationConstants {

        public static final int SUCCESS_RESULT = 0;
        public static final int FAILURE_RESULT = 1;
        public static final String PACKAGE_NAME = "com.camsys.carmonic";
        public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
        public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
        public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
        public static final String LOCATION_DATA_AREA = PACKAGE_NAME + ".LOCATION_DATA_AREA";
        public static final String LOCATION_DATA_CITY = PACKAGE_NAME + ".LOCATION_DATA_CITY";
        public static final String LOCATION_DATA_STREET = PACKAGE_NAME + ".LOCATION_DATA_STREET";

    }

}
