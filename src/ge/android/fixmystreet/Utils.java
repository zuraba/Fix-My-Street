package ge.android.fixmystreet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;

public class Utils {
	
	static HttpResponse response;
	

		private static String url = "http://chemikucha.ge/reports/";

	public static void doPost(final ReportFields report) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

			entity.addPart("lat", new StringBody(report.getLatitude()));
			entity.addPart("lon", new StringBody(report.getLongtitude()));
			entity.addPart("geo", new StringBody("on"));
			entity.addPart("title", new StringBody(report.getTitle()));
			entity.addPart("street", new StringBody(report.getAddress()));
			entity.addPart("photo", report.getPhoto());
			entity.addPart("category_id", new StringBody(report.getCategory()));
			entity.addPart("desc", new StringBody(report.getDetails()));
			entity.addPart("author", new StringBody(report.getAuthor()));
			entity.addPart("email", new StringBody(report.getEmail()));
			entity.addPart("phone", new StringBody(report.getTel()));
			post.setEntity(entity);

			client.execute(post);
	}

	public static ByteArrayBody photoToByteArray(String path) {
		if(TextUtils.isEmpty(path)){
			return null;
		}else {
		Bitmap bitmap = BitmapFactory.decodeFile(path);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 75, bos);
		byte[] data = bos.toByteArray();
		ByteArrayBody bab = new ByteArrayBody(data, "myphoto.png");

		return bab;
		}
	}
}
