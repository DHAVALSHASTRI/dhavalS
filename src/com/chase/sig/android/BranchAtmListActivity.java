package com.chase.sig.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BranchAtmListActivity extends Activity {

	CurrentLocation currLoc;
	double lng;
	double lat;
	ListView lv;

	Locations[] locations;
	List<Locations> lst;
	CustomBaseAdapter adapter;
	String QueryURL;
	TextView loc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_branch_atm_list);
		 loc = (TextView) findViewById(R.id.location);
		lv = (ListView) findViewById(R.id.locList);

		currLoc = new CurrentLocation(this);
		loc.setText("NO Chase ATM/Branch FOUND Near your Location");
		new QueryLocAPI(lat,lng).execute();
		
	}

	class QueryLocAPI extends AsyncTask<string, string, string> {

		public QueryLocAPI(double lat, double lng) {
			QueryURL = "https://m.chase.com/PSRWeb/location/list.action?lat="+lat+"&lng="+lng;
		}

		@Override
		protected string doInBackground(string... params) {
			//QueryURL = "https://m.chase.com/PSRWeb/location/list.action?lat=40.147864&lng=-82.990959";
			String response = new String();
			JSONObject mainObject = null;
			JSONArray arr = null;
			URL url;

			try {
				//HTTP URL Connection to to query the data using latitude and longitude.
				url = new URL(QueryURL);
				HttpsURLConnection urlConnection = (HttpsURLConnection) url
						.openConnection();
				InputStream in = urlConnection.getInputStream();

				BufferedReader bfr = new BufferedReader(new InputStreamReader(
						in));
				String inputLine;
				while ((inputLine = bfr.readLine()) != null) {
					response += inputLine;
					Log.d(" Response ::", ">>" + response);
				}

				//JOSN OBJECT 
				mainObject = new JSONObject(response);

				// JOSN OBJECT  for LOCATIONS
				arr = mainObject.getJSONArray("locations");
				
				//GSON Builder
				Gson gsontest = new GsonBuilder().setPrettyPrinting()
						.disableHtmlEscaping().create();

				//GSON : convert fromJSON
				locations = gsontest
						.fromJson(arr.toString(), Locations[].class);
				
				//List Contains all Locations
				lst = Arrays.asList(locations);
				
				

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(string result) {
			
			if(lst.size()==0)
			{
				loc.setVisibility(View.VISIBLE);
			}
			// Customer Adapter to display all Items
			adapter = new CustomBaseAdapter(BranchAtmListActivity.this, lst);
			lv.setAdapter(adapter);
			
			// On Items Click of Each Items in row
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent i = new Intent();
					Bundle b = new Bundle();
										
					b.putString("Address", lst.get(position).getAddress());
					b.putString("Atm", lst.get(position).getAtms());
					b.putString("Bank", lst.get(position).getBank());
					b.putString("City", lst.get(position).getCity());
					b.putString("Distance", lst.get(position).getDistance());
					b.putString("Label", lst.get(position).getLabel());
					b.putString("LocType", lst.get(position).getLocType());
					b.putString("Phone", lst.get(position).getPhone());
					b.putString("State", lst.get(position).getState());
					b.putString("Type", lst.get(position).getType());
					b.putString("Zip", lst.get(position).getZip());
					
					b.putString("lat", lst.get(position).getLat());
					b.putString("lng", lst.get(position).getLng());
					b.putStringArray("Drive", lst.get(position).getDriveUpHrs());
					b.putStringArray("Lobby", lst.get(position).getLobbyHrs());
					b.putStringArray("Service", lst.get(position).getServices());
										
					//Put selected ATM/Branch data in bundle and sent to Detail Activity 
					i.putExtras(b);
					i.setClass(BranchAtmListActivity.this, DetailsActivity.class);
					startActivity(i);	
				}
			});					
		}
	}
}
