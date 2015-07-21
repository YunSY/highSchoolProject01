package kr.hs.emirim.myhighschoolproject.highschoolproject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class MapActivity extends NMapActivity
        implements NMapView.OnMapStateChangeListener, NMapView.OnMapViewTouchEventListener {
    double[] latArr;
    double[] LngArr;
    String[] nameArr;
    int length = 0;
    int json_length;

    private final static String apiKey = "b776f3e5d8562557861b733c6f0b35b9";
    NMapView mMapView = null;
    NMapController mMapController = null;
    LinearLayout MapContainer;
    NMapViewerResourceProvider mMapViewerResourceProvider = null;
    NMapOverlayManager mOverlayManager;
    NMapView.OnMapStateChangeListener onPOIdataStateChangeListener = null;

    NMapLocationManager mMapLocationManager = null;
    NMapCompassManager mMapCompassManager = null;
    NMapMyLocationOverlay mMyLocationOverlay = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        getPlaceJasonParsing();
        // create map view
        MapContainer = (LinearLayout) findViewById(R.id.map);

        mMapView = new NMapView(this);
        mMapView.setApiKey(apiKey);
        mMapView.setClickable(true);
        setContentView(mMapView);
        mMapView.setOnMapStateChangeListener(this);
        mMapView.setOnMapViewTouchEventListener(this);
        mMapController = mMapView.getMapController();

        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);

        int markerId = NMapPOIflagType.PIN;

        NMapPOIdata poiData = new NMapPOIdata(length, mMapViewerResourceProvider);
        poiData.beginPOIdata(length);
        //poiData.addPOIitem(127.0038022, 37.5770935 , "하하", markerId, 0);
        for (int i = 0; i < length; i++) {
            poiData.addPOIitem(LngArr[i], latArr[i], nameArr[i], markerId, 0);
            //poiData.addPOIitem(LngArr[i], latArr[i], "Pizza 777-111", markerId, 0);
        }

        poiData.endPOIdata();
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0);

        mMapLocationManager = new NMapLocationManager(this);
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);
        mMapCompassManager = new NMapCompassManager(this);
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);

        mMapView.setOnMapStateChangeListener(this);
    }   //onCreate메소드


    @Override
    public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onLongPressCanceled(NMapView nMapView) {

    }

    @Override
    public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {

    }

    @Override
    public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError errorInfo) {
        if (errorInfo == null) {
            //      startMyLocation();
            mMapController.setMapCenter(126.932645, 37.466661,12);
        } else {
            android.util.Log.e("NMAP", "onMapInitHanler : error=" + errorInfo.toString());
        }
    }

    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

    }

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {

    }

    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {

    }

    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

    }

    private void startMyLocation() {

        mMapLocationManager = new NMapLocationManager(this);
        mMapLocationManager
                .setOnLocationChangeListener(onMyLocationChangeListener);

        mMapController.setZoomLevel(11);
        boolean isMyLocationEnabled = mMapLocationManager
                .enableMyLocation(true);
        if (!isMyLocationEnabled) {
            Toast.makeText(
                    MapActivity.this,
                    "Please enable a My Location source in system settings",
                    Toast.LENGTH_LONG).show();

            Intent goToSettings = new Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(goToSettings);
            finish();
        }else{

        }
    }

    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {

        @Override
        public boolean onLocationChanged(NMapLocationManager locationManager,
                                         NGeoPoint myLocation) {

            if (mMapController != null) {
                mMapController.animateTo(myLocation);
            }
            Log.d("myLog", "myLocation  lat " + myLocation.getLatitude());
            Log.d("myLog", "myLocation  lng " + myLocation.getLongitude());


//            findPlacemarkAtLocation(myLocation.getLongitude(), myLocation.getLatitude());
            //위도경도를 주소로 변환

            return true;
        }

        private void stopMyLocation() {
            if (mMyLocationOverlay != null) {
                mMapLocationManager.disableMyLocation();

                if (mMapView.isAutoRotateEnabled()) {
                    mMyLocationOverlay.setCompassHeadingVisible(false);

                    mMapCompassManager.disableCompass();

                    mMapView.setAutoRotateEnabled(false, false);

                    MapContainer.requestLayout();
                }
            }
        }

        @Override
        public void onLocationUpdateTimeout(NMapLocationManager locationManager) {

            // stop location updating
            // Runnable runnable = new Runnable() {
            // public void run() {
            // stopMyLocation();
            // }
            // };
            // runnable.run();

            Toast.makeText(MapActivity.this,
                    "Your current location is temporarily unavailable.",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLocationUnavailableArea(
                NMapLocationManager locationManager, NGeoPoint myLocation) {

            Toast.makeText(MapActivity.this,
                    "Your current location is unavailable area.",
                    Toast.LENGTH_LONG).show();

            stopMyLocation();
        }

    };

    public void getPlaceJasonParsing() {
        String fileName = "highSchool.json";
        String result = "";

        try {


            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buff = new byte[size];
            is.read(buff);      //is에 있는 내용을 buff로 읽어옴
            is.close();
            result = new String(buff, "UTF-8");     //byte를 String으로

            JSONObject json = new JSONObject(result);
            JSONArray jArr = json.getJSONArray("data");    //테이블이름
            json_length = jArr.length();

            latArr = new double[json_length];
            LngArr = new double[json_length];
            nameArr = new String[json_length];

            for (int i = 0; i < json_length; i++) {
                json = jArr.getJSONObject(i);
                latArr[i] = Double.parseDouble(json.getString("lat"));
                LngArr[i] = Double.parseDouble(json.getString("lng"));
                nameArr[i] = json.getString("name");
                length++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}