package ge.android.fixmystreet;

import ge.android.fixmystreet.districts.MyMapActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DistrictList extends ListActivity {

	final private String[] districtsGE = {"ისანი-სამგორი",
			"ვაკე-საბურთალო",
			"გლდანი-ნაძალადედვი",
			"დიდგორი",
			"დიდუბე-ჩუღურეთი",
			"ძველი-თბილისი"};
	
	final private String[] locations = {"41.6723515, 44.91363209999997",
			"41.768634315325095, 44.740328304469585",
			"41.7581044, 44.826926800000024",
			"41.67169579999999, 44.654534600000034",
			"41.7151151, 44.816289600000005",
			"41.6936011, 44.7947772"
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(DistrictList.this, android.R.layout.simple_list_item_1, districtsGE));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String distLoc = locations[position];
//		String distName = districtsGE[position];

		Intent myMap = new Intent(DistrictList.this, MyMapActivity.class);
		myMap.putExtra("loc", distLoc);
//		myMap.putExtra("districtName", distName);
		startActivity(myMap);
	}
}
