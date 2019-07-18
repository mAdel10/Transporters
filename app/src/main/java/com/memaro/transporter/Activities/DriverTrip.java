package com.memaro.transporter.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.util.SortedList;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.memaro.transporter.Activities.Models.Request;
import com.memaro.transporter.Activities.Models.TransporterModel;
import com.memaro.transporter.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverTrip extends AppCompatActivity implements OnMapReadyCallback, OnLocationClickListener, PermissionsListener, OnCameraTrackingChangedListener, MapboxMap.OnMapClickListener {
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    //private LocationEngine locationEngine;
    //private LocationLayerPlugin locationLayerPlugin;
    //private Location location;
    private LocationComponent locationComponent;
    private boolean isInTrackingMode;

    Button startButton;
    private Location originLocation;
    private LatLng markerLocation;
    private Point originPosition;
    private Point destinationPosition;
    private Marker destinationMarker;
    private Marker companiesMarker;

    private NavigationMapRoute navigationMapRoute;
    final static String TAG = "MainActivity";
    DirectionsRoute currentRoute;


    //ArrayList<Model> arrayList = new ArrayList<>();
    private int radius;
    public static Request request;
    final private TransporterModel data = DriverLogInActivity.driverData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.map_box_key));
        MapboxNavigation navigation = new MapboxNavigation
                (getApplicationContext(), getString(R.string.map_box_key));

        setContentView(R.layout.activity_driver_trip);

        setContentView(R.layout.activity_driver_trip);

        mapView = findViewById(R.id.driver_trip_mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

//        arrayList.add(new ModelClass(29.976521, 31.257808, "Amir Company")); // 400 M
//        arrayList.add(new ModelClass(29.984121, 31.254444, "Kamal Company")); // 600 M
//        arrayList.add(new ModelClass(29.982180, 31.242667, "Mokhtar Company")); // + 1000 M
//        arrayList.add(new ModelClass(29.969801, 31.251000, "El Banna Company")); // + 1000 M

        startButton = findViewById(R.id.driver_trip_start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Distance
        //        ArrayList<ModelClass> arrayListFinalPoints = new ArrayList<>();
//                Location userLocation = new Location("");
//                userLocation.setLatitude(originLocation.getLatitude());
 //               userLocation.setLongitude(originLocation.getLongitude());

//                for (int i = 0; i < arrayList.size(); i++) {
//                    double lat = arrayList.get(i).getLatitude();
//                    double lng = arrayList.get(i).getLongitude();
//                    String companyName = arrayList.get(i).getCompanyName();
//
//                    Location companyLocation = new Location("");
//                    companyLocation.setLatitude(lat);
//                    companyLocation.setLongitude(lng);
//
//                    float distance = companyLocation.distanceTo(userLocation);
//
//                    if (distance < radius) {
//                        arrayListFinalPoints.add(new ModelClass(lat, lng, companyName));
//                    }
//                }
//
//                if (companiesMarker != null) {
//                    mapboxMap.removeMarker(companiesMarker);
//                }
//
//                IconFactory iconFactory = IconFactory.getInstance(MainActivity.this);
//                Bitmap bitmap = getBitmapFromVectorDrawable(MainActivity.this, R.drawable.ic_company);
//                Icon icon = iconFactory.fromBitmap(bitmap);
//
//                for (int i = 0; i < arrayListFinalPoints.size(); i++) {
//                    LatLng point =
//                            new LatLng(arrayListFinalPoints.get(i).getLatitude(), arrayListFinalPoints.get(i).getLongitude());
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.setTitle(arrayListFinalPoints.get(i).getCompanyName());
//
//                    markerOptions.setIcon(icon);
//                    markerOptions.setPosition(point);
//
//                    companiesMarker = mapboxMap.addMarker(markerOptions);
//                }


                try {

                    NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                            .directionsRoute(currentRoute)
                            .shouldSimulateRoute(true)
                            .build();

                    NavigationLauncher.startNavigation(DriverTrip.this, options);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(DriverTrip.this, "Please wait until drawing route", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = AppCompatResources.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        if (destinationMarker != null) {
            mapboxMap.removeMarker(destinationMarker);
        }

        //destinationMarker = mapboxMap.addMarker(new MarkerOptions().position(point));

  //      destinationPosition = Point.fromLngLat(point.getLongitude(), point.getLatitude());
//        originPosition = Point.fromLngLat(originLocation.getLongitude(), originLocation.getLatitude());



        //getRoute(originPosition, destinationPosition);
//        startButton.setEnabled(true);
//        startButton.setBackgroundResource(R.color.mapbox_blue);

//        try {
//            float[] floats = new float[1];
//            Location.distanceBetween(originLocation.getLatitude(), originLocation.getLongitude(),
//                    point.getLatitude(), point.getLongitude(), floats);
//            radius = (int) floats[0];
//            radius += 500;
//            Toast.makeText(this, "Distance : " + floats[0] + ", Radius : " + radius, Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }

        return true;
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);
        mapboxMap.setStyle(Style.DARK, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
            }
        });

        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                if (marker.getTitle() != null) {
                    Toast.makeText(DriverTrip.this, "Marker : " + marker.getTitle(), Toast.LENGTH_LONG).show();
                    openCompanyDialog(marker.getTitle());
                }
                return true;
            }
        });

        Point driverLocation = Point.fromLngLat(data.getLongitude(), data.getLatitude());
        Point userLocation = Point.fromLngLat(request.getUserLongitude(), request.getUserLatitude());
        Point orderLocation = Point.fromLngLat(request.getOrderLongitude(), request.getOrderLatitude());

        Toast.makeText(this, "" +data.getLongitude()+ data.getLatitude()
                        +request.getUserLongitude() + request.getUserLatitude()
                , Toast.LENGTH_SHORT).show();

        getRoute(driverLocation, userLocation);

    }

    private void getRoute(Point originPosition, Point destinationPosition) {
        NavigationRoute.builder(DriverTrip.this)
                .accessToken(getString(R.string.map_box_key))
                .origin(originPosition)
                .destination(destinationPosition)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, check right user and access token");
                            return;
                        } else if (response.body().routes().size() == 0) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap);

                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        Log.e(TAG, "Error : " + t.getMessage());
                    }
                });
    }

    private void openCompanyDialog(String markerTittle) {
        AlertDialog alertDialog = new AlertDialog.Builder(DriverTrip.this)
                .setTitle("Request order")
                .setMessage("Are you sure to start your request with " + markerTittle)
                .setPositiveButton("Send Request", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DriverTrip.this, "Request Send", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        alertDialog.show();
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Create and customize the LocationComponent's options
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(this)
                    .elevation(5)
                    .accuracyAlpha(.6f)
                    //.accuracyColor(Color.RED)
                    //.foregroundDrawable(R.drawable.android_custom_location_icon)
                    .build();

            // Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();

            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .locationComponentOptions(customLocationComponentOptions)
                            .build();

            // Activate with options
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            // Add the location icon click listener
            locationComponent.addOnLocationClickListener(this);

            // Add the camera tracking listener. Fires if the map camera is manually moved.
            locationComponent.addOnCameraTrackingChangedListener(this);

            findViewById(R.id.back_to_camera_tracking_mode).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isInTrackingMode) {
                        isInTrackingMode = true;
                        locationComponent.setCameraMode(CameraMode.TRACKING);
                        locationComponent.zoomWhileTracking(16f);
                        Toast.makeText(DriverTrip.this, getString(R.string.tracking_enabled),
                                Toast.LENGTH_SHORT).show();
                        Location location = locationComponent.getLastKnownLocation();
                        if (location != null) {
                            showLocation(location);
                        } else {
                            Toast.makeText(DriverTrip.this, "Cant resolve location", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DriverTrip.this, getString(R.string.tracking_already_enabled),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            originLocation = locationComponent.getLastKnownLocation();


        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    private void showLocation(Location location) {
        Toast.makeText(this,
                "Lat : " + location.getLatitude() + ", Long : " + location.getLongitude(),
                Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings({"MissingPermission"})
    @Override
    public void onLocationComponentClick() {
        if (locationComponent.getLastKnownLocation() != null) {
            Toast.makeText(this, String.format(getString(R.string.current_location) +
                    locationComponent.getLastKnownLocation().getLatitude() +
                    locationComponent.getLastKnownLocation().getLongitude()), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCameraTrackingDismissed() {
        isInTrackingMode = false;
    }

    @Override
    public void onCameraTrackingChanged(int currentMode) {
        // Empty on purpose
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
