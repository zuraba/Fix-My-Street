package ge.android.fixmystreet.districts;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import ge.android.fixmystreet.R;
import android.app.Activity;
import android.os.Bundle;

public class IsaniSamgori extends Activity {
	
	private GoogleMap mMap;
	private static final LatLng ISANI = new LatLng(41.6723515, 44.91363209999997);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layoout);
			
		setUpMap();
	}
	
	private void setUpMap(){
		if(mMap == null)
			mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		 
		if (mMap != null){
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ISANI, 15));
			
		}
	}
}
