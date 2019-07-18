package com.memaro.transporter.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
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
import com.memaro.transporter.Activities.Models.Company;
import com.memaro.transporter.Activities.Models.LocationModelClass;
import com.memaro.transporter.Activities.Models.Request;
import com.memaro.transporter.Activities.Models.TransporterModel;
import com.memaro.transporter.R;

import java.util.ArrayList;
import java.util.List;

public class CompanySendRequest extends AppCompatActivity implements OnMapReadyCallback, OnLocationClickListener, PermissionsListener, OnCameraTrackingChangedListener, MapboxMap.OnMapClickListener {
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    //private LocationEngine locationEngine;
//private LocationLayerPlugin locationLayerPlugin;
//private Location location;
    private LocationComponent locationComponent;
    private boolean isInTrackingMode;

    Button startButton;
    public static Location originLocation; // user
    private LatLng markerLocation;
    private Point originPosition;
    public static Point destinationPosition; // point
    private Marker destinationMarker;
    private Marker companiesMarker;

    public static Request request;


    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    private ArrayList<TransporterModel> arrayList = new ArrayList<>();
    private ArrayList<Company> arrayListFinalPoints = new ArrayList<>();
    private TransporterModel driverInfoSelected;

    private int radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.map_box_key));
        setContentView(R.layout.activity_company_send_request);

        setContentView(R.layout.activity_company_send_request);

        mapView = findViewById(R.id.company_send_request_map_mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

//        arrayList.add(new LocationModelClass(29.976521, 31.257808, "Amir Company")); // 400 M
//        arrayList.add(new LocationModelClass(29.982180, 31.242667, "Mokhtar Company")); // + 1000 M
//        arrayList.add(new LocationModelClass(29.969801, 31.251000, "El Banna Company")); // + 1000 M

        reference.child("Company").child(user.getUid()).child("Drivers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    arrayList.add(snapshot.getValue(TransporterModel.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        startButton = findViewById(R.id.start_button);
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Distance
//
//                IconFactory iconFactory = IconFactory.getInstance(CompanySendRequest.this);
//                Bitmap bitmap = getBitmapFromVectorDrawable(CompanySendRequest.this, R.drawable.ic_car_green);
//                Icon icon = iconFactory.fromBitmap(bitmap);
//
//                for (int i = 0; i < arrayList.size(); i++)
//                {
//                    LatLng point =
//                            new LatLng(arrayList.get(i).getLatitude(), arrayList.get(i).getLongitude());
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.setTitle(arrayListFinalPoints.get(i).getCompanyId());
//
//                    markerOptions.setIcon(icon);
//                    markerOptions.setPosition(point);
//
//                    companiesMarker = mapboxMap.addMarker(markerOptions);
//                }

//            }
//        });

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

    private void showCars() {
        IconFactory iconFactory = IconFactory.getInstance(CompanySendRequest.this);
        Bitmap bitmap = getBitmapFromVectorDrawable(CompanySendRequest.this, R.drawable.ic_car_green);
        Icon icon = iconFactory.fromBitmap(bitmap);

        for (int i = 0; i < arrayList.size(); i++) {
            LatLng point =
                    new LatLng(arrayList.get(i).getLatitude(), arrayList.get(i).getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.setTitle(arrayList.get(i).getDriverId());

            markerOptions.setIcon(icon);
            markerOptions.setPosition(point);

            companiesMarker = mapboxMap.addMarker(markerOptions);
        }

    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        if (destinationMarker != null) {
            mapboxMap.removeMarker(destinationMarker);
        }

//        destinationMarker = mapboxMap.addMarker(new MarkerOptions().position(point));

        destinationPosition = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        originPosition = Point.fromLngLat(originLocation.getLongitude(), originLocation.getLatitude());


//        try {
//            float[] floats = new float[1];
//            Location.distanceBetween(originLocation.getLatitude(), originLocation.getLongitude(),
//                    point.getLatitude(), point.getLongitude(), floats);
//            ServiceRequestActivity.distance = (int) floats[0];
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
                    //Toast.makeText(ClientMapActivity.this, "Marker : " + marker.getTitle(), Toast.LENGTH_LONG).show();
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).getDriverId().equals(marker.getTitle())) {
                            driverInfoSelected = arrayList.get(i);
                            openCompanyDialog();
                        }
                    }
                }
                return true;
            }
        });
    }

    private void openCompanyDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(CompanySendRequest.this)
                .setTitle("Request order")
                .setMessage(" Are you sure to send this request to driver :  " + driverInfoSelected.getName()
                )
                .setPositiveButton("Send Request", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference.child("Company").child(user.getUid()).child("Drivers")
                                .child(driverInfoSelected.getDriverId()).child("SingleRequest").setValue(request);

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
                        Toast.makeText(CompanySendRequest.this, getString(R.string.tracking_enabled),
                                Toast.LENGTH_SHORT).show();
                        Location location = locationComponent.getLastKnownLocation();
                        if (location != null) {
                            showLocation(location);
                        } else {
                            Toast.makeText(CompanySendRequest.this, "Cant resolve location", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CompanySendRequest.this, getString(R.string.tracking_already_enabled),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            originLocation = locationComponent.getLastKnownLocation();

            showCars();


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
