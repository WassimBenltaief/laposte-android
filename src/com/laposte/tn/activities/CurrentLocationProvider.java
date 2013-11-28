package com.laposte.tn.activities;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.LocationSource;

public class CurrentLocationProvider implements LocationSource, LocationListener, android.location.LocationListener
{
    private OnLocationChangedListener listener;
    private LocationManager locationManager;

    public CurrentLocationProvider(Context context)
    {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void activate(OnLocationChangedListener listener)
    {
        this.listener = listener;
        LocationProvider gpsProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
        if(gpsProvider != null)
        {
            locationManager.requestLocationUpdates(gpsProvider.getName(), 0, 2, this);
        }

        LocationProvider networkProvider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
        LocationProvider passiveProvider = locationManager.getProvider(LocationManager.PASSIVE_PROVIDER);
        if(networkProvider != null) {
            locationManager.requestLocationUpdates(networkProvider.getName(), 0, 0, this);
        }
        if(passiveProvider != null) {
        	locationManager.requestLocationUpdates(passiveProvider.getName(), 0, 0, this);
        }
    }

    @Override
    public void deactivate()
    {
        locationManager.removeUpdates(this);
    }


    @Override
    public void onProviderDisabled(String provider)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        // TODO Auto-generated method stub

    }

	@Override
	public void onLocationChanged(Location location) {

        if(listener != null)
        {
            listener.onLocationChanged(location);
        }
		
	}
}