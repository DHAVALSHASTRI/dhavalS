package com.chase.sig.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Class will display the details such as Address, Distance, Access, Lobby Hours of BANK/ATM
 * @author 
 *
 */
public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moredetails);
		
		TextView Title = (TextView) findViewById(R.id.title);
		TextView address = (TextView) findViewById(R.id.detailadd);
		TextView distnace = (TextView) findViewById(R.id.detaildist);
		TextView detailaccess = (TextView) findViewById(R.id.detailaccess);
		TextView lobbyHr = (TextView) findViewById(R.id.lobbyHr);
		Button getMap = (Button ) findViewById(R.id.gotMAP);
		RelativeLayout lobby = (RelativeLayout)findViewById(R.id.lobbyHrs);
		
		//BUNDLE to get data from previous Activity	
		final Bundle b = getIntent().getExtras();

		Title.setText(b.getString("Label"));
		
		address.setText(b.getString("Address") + "\n" + b.getString("City")
				+ "\n" + b.getString("State") + "," + b.getString("Zip"));
		
		distnace.setText(b.getString("Distance") + " miles");
		
		detailaccess.setText(b.getString("Type"));
		
		if(b.getStringArray("Lobby")!=null){
		String[] arr = b.getStringArray("Lobby");

		String display = "";
		for (int i = 1; i < arr.length; i++) {
			display = display + arr[i] + " Hr \n";
		}
		lobbyHr.setText(display);
		}
		else{
			lobby.setVisibility(View.GONE);
		}
		
		
		/**
		 *on Button Click: display The Google MAP with Lat and Long
		 */
		getMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Uri gmmIntentUri = Uri.parse("geo:0,0?q="+b.getString("lat")+","+b.getString("lng")+b.getString("Label"));
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
				mapIntent.setPackage("com.google.android.apps.maps");
				if (mapIntent.resolveActivity(getPackageManager()) != null) {
				    startActivity(mapIntent);
				}
				
			}
		});
		
		
		
	}
}
