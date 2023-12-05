package com.example.crimeslostsreport.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.crimeslostsreport.Activities.AnnouncementActivity;
import com.example.crimeslostsreport.Activities.MainActivity;
import com.example.crimeslostsreport.Models.User;
import com.example.crimeslostsreport.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
    double lat = 0, lng = 0, selectedLat = 0, selectedLng = 0;
    int visibility, announcementID;
    User user;

    public MapFragment() {
    }

    public MapFragment(User user, int announcementID, double lat, double lng, int visibility) {
        this.lng = lng;
        this.lat = lat;
        this.visibility = visibility;
        this.user = user;
        this.announcementID = announcementID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        Button confirmLocation = view.findViewById(R.id.confirmLocation);
        confirmLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AnnouncementActivity.class);
                i.putExtra("user", user);
                i.putExtra("announcementID", announcementID);
                i.putExtra("latitude", lat);
                i.putExtra("longitude", lng);
                startActivity(i);
                getActivity().finish();

            }
        });
        confirmLocation.setVisibility(visibility);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latLng = new LatLng(lat, lng);
                if (visibility == View.INVISIBLE) {
                    setMarker(latLng, googleMap);
                    return;
                }
                setMarker(latLng, googleMap);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, -100));
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        setMarker(latLng, googleMap);
                    }
                });
            }
        });
        return view;
    }

    private void setMarker(LatLng latLng, GoogleMap googleMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
        lat = latLng.latitude;
        lng = latLng.longitude;
        googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        googleMap.addMarker(markerOptions);
    }
}
