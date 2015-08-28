package com.chase.sig.android;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Customer List Adapter Class to display all list Items in Rows
 * 
 */

public class CustomBaseAdapter extends BaseAdapter {
	Context context;
	List<Locations> rowItems;

	public CustomBaseAdapter(Context context, List<Locations> items) {
		this.context = context;
		this.rowItems = items;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView imageView;
		TextView name;
		TextView distance;
		TextView loct;
		TextView add;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		Log.d("Location 11 : ", rowItems.get(position).getAddress());

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.detailview, null);
			holder = new ViewHolder();
			holder.distance = (TextView) convertView
					.findViewById(R.id.distance);
			holder.name = (TextView) convertView.findViewById(R.id.name);

			holder.loct = (TextView) convertView.findViewById(R.id.locat);
			holder.add = (TextView) convertView.findViewById(R.id.address);

			holder.imageView = (ImageView) convertView
					.findViewById(R.id.iconss);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Locations rowItem = (Locations) getItem(position);

		//Set Data
		holder.distance
				.setText("Distance: " + rowItem.getDistance() + " miles");
		holder.name.setText(rowItem.getLocType().toUpperCase());
		holder.loct.setText(rowItem.getLabel());
		holder.add.setText(rowItem.getAddress() + ", " + rowItem.getCity()
				+ ", " + rowItem.getState());
		if (rowItem.getLocType().equalsIgnoreCase("branch")) {
			holder.imageView.setImageResource(R.drawable.bankicon);
		} else {
			holder.imageView.setImageResource(R.drawable.atmicon);
		}

		return convertView;
	}


	@Override
	public int getCount() {
		return rowItems.size();
	}

	@Override
	public Object getItem(int position) {
		return rowItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowItems.indexOf(getItem(position));
	}
}
