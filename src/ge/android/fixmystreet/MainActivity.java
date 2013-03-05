package ge.android.fixmystreet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Thread intro = new Thread(){
			public void run(){
				try{
					sleep(2000);
				} catch(InterruptedException ie) {
					ie.printStackTrace();
				} finally {
					Intent districtsIntent = new Intent(MainActivity.this, DistrictList.class);
					startActivity(districtsIntent);
				}
			}
		};
		intro.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
