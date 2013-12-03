package com.team3.presentation;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.team3.R;
import com.team3.entities.LocationVO;

class FavouritesArrayAdapter extends ArrayAdapter<LocationVO> {

    /**
	 * 
	 */
	HashMap<LocationVO, Integer> mIdMap = new HashMap<LocationVO, Integer>();
	private Context context;
	
	
    public FavouritesArrayAdapter(Context context, int textViewResourceId,
        List<LocationVO> objects) {
      super(context, textViewResourceId, objects);
      this.context = context;
      for (int i = 0; i < objects.size(); ++i) {
        mIdMap.put(objects.get(i), i);
      }
    }

    @Override
    public long getItemId(int position) {
    	LocationVO item = getItem(position);
    	return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
    	return true;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.favourite_item, parent, false);
            LocationVO location = getItem(position);
            TextView locationIDTV = (TextView) rowView.findViewById(R.id.locationIDFav);
         //   locationIDTV.setText(location.getID());
            TextView locationNameTV = (TextView) rowView.findViewById(R.id.locationNameFav);
            locationNameTV.setText(location.getName());
            TextView locationAddressTV = (TextView) rowView.findViewById(R.id.locationAddressFav);
            locationAddressTV.setText(location.getAddress());
            return rowView;
    }

  }