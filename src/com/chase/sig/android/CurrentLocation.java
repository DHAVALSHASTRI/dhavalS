package com.chase.sig.android;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/*
 * Class will get Current Location as Latitude and Longitude using Location Manager
 */
public class CurrentLocation implements LocationListener {

	private final Context mContext;
	String provider;
	Location loc;
	LocationManager lm;
	double lng;
	double lat;

	// Constructor
	public CurrentLocation(Context context) {
		this.mContext = context;
		initLocation();
	}

	
	public void initLocation() {
		// get location service
		lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		
		// GPS Provider
		if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) 
		{
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,1000, this);
			 if (lm != null) {
			   loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			 }			
		}
		// NETWORK Provider
		else if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,1000, this);
			 if (lm != null) {
			   loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			 }
			
		}
		//Get latitude and long from Location
		 if (loc != null) {
			  lng = loc.getLongitude();
				lat = loc.getLatitude();
		  }
	}

	public double getLat()
	{
		return lat;
	}
	
	public double getLng()
	{
		return lng;
	}
	
	// If you want location on changing place also than use below method
	// otherwise remove all below methods and don't implement location listener
	@Override
	public void onLocationChanged(Location arg0) {
		lng = loc.getLongitude();
		lat = loc.getLatitude();

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
