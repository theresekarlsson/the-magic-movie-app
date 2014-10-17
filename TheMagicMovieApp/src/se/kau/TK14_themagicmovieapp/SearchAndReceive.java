package se.kau.TK14_themagicmovieapp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.util.Log;

public class SearchAndReceive extends AsyncTask<Void, Void, Void> {

	MovieMainActivity mma;
	private String searchString;
	private String urlAddress = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?";
	private String responseText;
	private String apiKey = "apikey=rjujpew8zdq758jp9wuvjteq";
	private String searchCall = urlAddress + apiKey + "&q=";
	private Map<String, String> item;		//ändra namn!
	private List<Map<String, String>> resultList;	//ändra namn!
	
	public SearchAndReceive(MovieMainActivity mma, String s) {
		this.mma = mma;
		searchString = s;
		Log.i("MusicApp", "Inne i SearchAndReceive " + searchString);
	}

	@Override
	protected Void doInBackground(Void... params) {
		Log.i("MusicApp", "Inne i doInBackground.");
		
			resultList = new ArrayList<Map<String, String>>();
			StringBuilder string_builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(searchCall + searchString);
			Log.i("MusicApp", httpGet.getURI().toString());
			
			try {
				
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					Log.i("MusicApp", "Status: 200.");
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				
				while ((line = reader.readLine()) != null) {
						string_builder.append(line);
					}
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			responseText = string_builder.toString();
			
			try {
				JSONObject result = new JSONObject(responseText);
				JSONArray jsonArray = result.getJSONArray("movies");

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					item = new HashMap<String, String>(2);
					item.put("title", jsonObject.getString("title"));
					resultList.add(item);
					Log.i("MusicApp", item.toString());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}
	
	protected void onPostExecute(Void results) {
		try
		{
			mma.displayResult(resultList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
