package ge.android.fixmystreet.districts;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ge.android.fixmystreet.R;
import ge.android.fixmystreet.ReportForm;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MyMapActivity extends Activity implements OnMarkerClickListener, OnMapClickListener {

	private GoogleMap mMap;
	private double latitude, longtitude;
	private String latLong; // distName;
	private LatLng location;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.map_layoout);
		Intent intent = getIntent();
		latLong = intent.getExtras().getString("loc");
		//		distName = intent.getExtras().getString("districtName");
		String[] local = latLong.split(", ");
		latitude = Double.parseDouble(local[0]);
		longtitude = Double.parseDouble(local[1]);
		location = new LatLng(latitude, longtitude);

		setUpMap();
	}

	private void setUpMap(){
		if(mMap == null)
			mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();

		if (mMap != null){
			mMap.setOnMarkerClickListener(this);
			mMap.setOnMapClickListener(this);
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			mMap.setMyLocationEnabled(true);
			mMap.getMyLocation();
			mMap.setOnMapClickListener(this);
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
			mMap.addMarker(new MarkerOptions()
			//			.title(distName)
			//			.snippet("სულ შეტყობინებები რაიონში: ")
			.position(location)
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
		}
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		String latlng = marker.getPosition().toString();
		Intent reportIntent = new Intent(MyMapActivity.this, ReportForm.class);
		reportIntent.putExtra("extra", latlng);
		startActivity(reportIntent);
		return false;
	}

	@Override
	public void onMapClick(LatLng point) {
		mMap.addMarker(new MarkerOptions()
		.position(point)
		.draggable(true)
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

	}
}
