package com.ocr.observador.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ocr.observador.MainActivity;
import com.ocr.observador.R;
import com.ocr.observador.events.DrawMarkersEvent;
import com.ocr.observador.events.MarkerClickedEvent;
import com.ocr.observador.events.StartRegisteringUserEvent;
import com.ocr.observador.model.ModelMarker;
import com.ocr.observador.utilities.AndroidBus;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
    private static View view;

    public static Bus mapBus;

    List<ModelMarker> markerList;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
            ButterKnife.inject(this, view);

        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        mapBus = new AndroidBus();
        mapBus.register(this);

        setUpMapIfNeeded();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();

        MainActivity.bus.post(getResources().getString(R.string.titlesMapFragment));
    }

    @Subscribe
    public void handleLocation(LatLng latLng) {
        CameraUpdate cameraUpdateFactory = CameraUpdateFactory.newLatLng(latLng);
        mMap.moveCamera(cameraUpdateFactory);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
    }

    @Subscribe
    public void drawMarkers(DrawMarkersEvent event) {
        markerList = getMarkers();
        MarkerOptions options = new MarkerOptions();
        for (ModelMarker modelMarker : markerList) {
            options.position(new LatLng(modelMarker.latitude, modelMarker.longitude));
            options.title(modelMarker.title);
            options.snippet(modelMarker.national_id);
            mMap.addMarker(options);
        }
    }


    /**
     * DB query
     *
     * @return db Markers
     */
    public List<ModelMarker> getMarkers() {
        return new Select().
                from(ModelMarker.class).
                execute();
    }


    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment. (THIS TOOK 2 HOURS)
            mMap = ((SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setMyLocationEnabled(true);

        // Click

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // the marker id must come like m0, m1, m2. So we remove the first character to get an integer
//                String markerStringId = marker.getId().substring(1);
                String markerStringId = marker.getSnippet();
                MainActivity.bus.post(new MarkerClickedEvent(MarkerClickedEvent.Type.STARTED, 1, markerStringId));
            }
        });

        // Initiate loadings

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                MainActivity.bus.post(new StartRegisteringUserEvent(StartRegisteringUserEvent.Type.STARTED, 1));
            }
        });
    }


}
