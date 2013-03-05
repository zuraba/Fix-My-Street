package ge.android.fixmystreet;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ReportForm extends Activity implements OnClickListener {

	private Spinner spinner;
	private EditText title, addr, pic, details, author, mail, tel;
	private Button btnCam, btnSend;
	String[] latLong;

	private String[] category_id = {"24", "34", "30", "26", "25", "33", "27", "29", "32", "21", "22", "23", "28", "31", "35"};

	private static final int SELECT_PHOTO = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(ge.android.fixmystreet.R.layout.report_form);
		Intent intent = getIntent();
		String str = intent.getExtras().getString("extra");
		latLong = str.split(",");

		title = (EditText) findViewById(R.id.editText1);
		addr = (EditText) findViewById(R.id.editText2);
		pic = (EditText) findViewById(R.id.editText3);
		details = (EditText) findViewById(R.id.editText4);
		author = (EditText) findViewById(R.id.editText5);
		mail = (EditText) findViewById(R.id.editText6);
		tel = (EditText) findViewById(R.id.editText7);

		btnCam = (Button) findViewById(R.id.button1);
		btnCam.setOnClickListener(this);
		btnSend = (Button) findViewById(R.id.button2);
		btnSend.setOnClickListener(this);

		spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ReportForm.this, R.array.category, android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		pic.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.editText3){
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO);
		}

		if(v.getId() == R.id.button1){
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			startActivityForResult(intent, 0);
		}

		if(v.getId() == R.id.button2){
			final ReportFields reportForm = new ReportFields();
			reportForm.setLatitude(latLong[0].replace("lat/lng: (", ""));
			reportForm.setLongtitude(latLong[1].replace(")",""));

			if(TextUtils.isEmpty(title.getText()) 
					|| TextUtils.isEmpty(addr.getText())
					//					|| TextUtils.isEmpty(pic.getText())
					|| TextUtils.isEmpty(details.getText())
					|| TextUtils.isEmpty(author.getText())
					|| TextUtils.isEmpty(mail.getText())
					|| TextUtils.isEmpty(tel.getText())){
				Toast.makeText(ReportForm.this, "ყველა ველი სავალდებულოა", Toast.LENGTH_SHORT).show();
				return;
			} else {
				reportForm.setTitle(title.getText().toString());
				reportForm.setAddress(addr.getText().toString());
				reportForm.setPhoto(Utils.photoToByteArray(pic.getText().toString()));
				reportForm.setCategory(category_id[spinner.getSelectedItemPosition()]);
				reportForm.setDetails(details.getText().toString());
				reportForm.setAuthor(author.getText().toString());
				reportForm.setEmail(mail.getText().toString());
				reportForm.setTel(tel.getText().toString());
			}

			new AsyncTask<Void, Void, Void>(){
				ProgressDialog dialog = new ProgressDialog(ReportForm.this);
				@Override
				protected void onPreExecute() {
					dialog.setMessage("თქვენი შეტყობინება იგზავნება");
					dialog.show();
				}

				@Override
				protected Void doInBackground(Void... params) {

					try {
						Utils.doPost(reportForm);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					if(dialog.isShowing())
						dialog.dismiss();
					Toast.makeText(ReportForm.this, "შეტყობინება წარმატებით გაიგზავნა. დაელოდეთ დადასტურების ბმულს თქვენს მიერ მითითებულ ფოსტაზე", Toast.LENGTH_LONG);

					title.setText("");
					addr.setText("");
					pic.setText("");
					details.setText("");
					spinner.setSelection(0);
					author.setText("");
					mail.setText("");
					tel.setText("");
				}
			}.execute();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String filePath = null;

		if(resultCode == RESULT_OK){
			Uri selectedImage = data.getData();
			String[] filePathCol = {MediaStore.Images.Media.DATA};

			Cursor cursor = getContentResolver().query(selectedImage, filePathCol, null, null, null);
			cursor.moveToFirst();

			int colIndex = cursor.getColumnIndex(filePathCol[0]);
			filePath = cursor.getString(colIndex);
			pic.setText(filePath);
			cursor.close();
		}
	}
}
