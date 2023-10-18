package com.example.stylesphere.ui.dashboard;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.example.stylesphere.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import com.example.stylesphere.databinding.FragmentDashboardBinding;

import org.json.JSONException;
import org.json.JSONObject;


public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private TextView location;
    private TextView status;
    private TextView temp;
    String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
    private TextView date;
    private ImageView clothing1;
    private ImageView clothing2;
    private Button getWeather;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    //Used to be ActivityResultLauncher<String[]> ,, make sure that this doesnt crash app later
    private double latitude;
    private double longitude;
    String apiKey = "f0ad6112130a345e7a07c586812dcb41\n";



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register for permission result here
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        // Permission is granted, you can now proceed with your task
                        getLastLocation();
                    } else {
                        // Permission denied, handle it accordingly
                        // You might want to show a message to the user or ask for permission again
                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                }
        );
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       // textView = root.findViewById(R.id.text_dashboard);
        location = root.findViewById(R.id.location);
        status = root.findViewById(R.id.weatherDescription);
        temp = root.findViewById(R.id.temperature);
        date = root.findViewById(R.id.editTextDate);
        date.setText(currentDate);
        clothing1 = root.findViewById(R.id.clothing1);
        clothing2 = root.findViewById(R.id.clothing2);
        getWeather = root.findViewById(R.id.weatherButton);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getActivity());

        // method to get the location
        getWeather.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // You don't have the permission, request it
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                } else {
                    // Permission is already granted, proceed with your task
                    getLastLocation();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {
            // check if location is enabled
            if (isLocationEnabled()) {
                // getting last location from FusedLocationClient object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location1 = task.getResult();
                        if (location1 == null) {
                            requestNewLocationData();
                        } else {
                            latitude= location1.getLatitude();
                            longitude = location1.getLongitude();
                            //location.setText(location1.getLatitude() + " " + location1.getLongitude());
                            getWeatherFromAPI(latitude, longitude);                        }
                    }
                });
            } else {
                Toast.makeText(this.getActivity(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available, request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest object with appropriate methods
        LocationRequest mLocationRequest = LocationRequest.create()
                .setInterval(5)
                .setFastestInterval(0)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(1);;

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude= mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            //location.setText("Latitude: " + mLastLocation.getLatitude() + " Longitude: " + mLastLocation.getLongitude());
            getWeatherFromAPI(latitude, longitude);
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location on Android 10.0 and higher, use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void checkPermissionAndRequest() {
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // You don't have the permission, request it
            //String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            // Permission is already granted, proceed with your task
            getLastLocation();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    private void getWeatherFromAPI(final double latitude, final double longitude) {
        Thread networkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Construct the URL for the API call
                    String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude +"&appid=" + apiKey + "&units=imperial";
                    URL url = new URL(apiUrl);

                    // Open a connection to the URL
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    // Read the response from the API
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse the JSON response
                    final JSONObject jsonResponse = new JSONObject(response.toString());

                    // Update the UI on the main thread
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                location.setText(jsonResponse.getString("name"));
                                double temperature = jsonResponse.getJSONObject("main").getDouble("temp");
                                temp.setText(temperature + "");
                                status.setText(jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    // Close the connection
                    connection.disconnect();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        networkThread.start();
    }



}