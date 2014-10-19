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
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.util.Log;

public class SearchAndReceive extends AsyncTask<Void, Void, Void> {

	private MovieMainActivity mainactivity;
	private String searchString, sentFrom, url, searchCall, responseText;
	private String apiKey = "apikey=rjujpew8zdq758jp9wuvjteq";
	private Map<String, String> listItem;
	private List<Map<String, String>> resultList;
	
	public SearchAndReceive(MovieMainActivity mma, String search, String sender) {
		this.mainactivity = mma;
		searchString = search;
		sentFrom = sender;
		
		Log.i("MusicApp", "Inne i SearchAndReceive");
	}

	protected Void doInBackground(Void... params) {
		Log.i("MusicApp", "Inne i doInBackground.");
		
		resultList = new ArrayList<Map<String, String>>();
		
		if (sentFrom == "imageSearchButton") {
			url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?";
			searchCall = url + apiKey + "&q=";
		}
		else if (sentFrom == "searchSimBtn") {
			url= "http://api.rottentomatoes.com/api/public/v1.0/movies/770672122/similar.json?";
			searchCall = url + apiKey;
			//TODO: film-id måste med?! Men hittar inget id om man bara sökt efter en film.
		}
			
		try {
			responseText = requestAndResponse().toString();
			handleJsonResponse();
			
		} catch (ClientProtocolException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace(); 
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(Void results) {
		try
		{
			mainactivity.displayResult(resultList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private StringBuilder requestAndResponse() throws ClientProtocolException, IOException {
		
		StringBuilder string_builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(searchCall + searchString);
		
		HttpResponse response = client.execute(httpGet);
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		
		if (statusCode == 200) {
			Log.i("MusicApp", "Status: " + statusCode);
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;
		
			while ((line = reader.readLine()) != null) {
					string_builder.append(line);
			}
		}
		return string_builder;
	}
	
	private void handleJsonResponse() throws JSONException {
		
		JSONObject result = new JSONObject(responseText);
		JSONArray jsonArray = result.getJSONArray("movies");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			listItem = new HashMap<String, String>(2);
			listItem.put("title", jsonObject.getString("title"));
			listItem.put("year", jsonObject.getString("year"));
			resultList.add(listItem);
		}
	}
}
