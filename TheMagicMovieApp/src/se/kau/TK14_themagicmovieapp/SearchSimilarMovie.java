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
import android.view.View;
import android.widget.LinearLayout;

public class SearchSimilarMovie extends AsyncTask<Void, Void, Void> {

	private MovieMainActivity mainactivity;
	private String searchString, searchCall, responseText;
	private String apiKey = "apikey=rjujpew8zdq758jp9wuvjteq";
	private Map<String, String> listItem;
	private List<Map<String, String>> resultList;
	LinearLayout progressBar;
	
	
	public SearchSimilarMovie(MovieMainActivity mma, String search) {
		this.mainactivity = mma;
		searchString = search;
		progressBar = (LinearLayout) mma.findViewById(R.id.progressbar_frame);
	}

	protected Void doInBackground(Void... params) {
		Log.i("MyMovieApp", "Inne i doInBackground.");
		
		searchCall = "http://api.rottentomatoes.com/api/public/v1.0/movies/" + searchString + "/similar.json?" + apiKey;
	
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
	
	protected void onPreExecute() {
		progressBar.setVisibility(View.VISIBLE);
	}
	
	protected void onPostExecute(Void results) {
		try
		{
			mainactivity.displayResult(resultList);
			progressBar.setVisibility(View.GONE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* skickar förfrågan till api:t och mottar svar. */
	private StringBuilder requestAndResponse() throws ClientProtocolException, IOException {
		
		StringBuilder string_builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(searchCall);
		
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
	
	/* hanterar svar (resultat). Sparar undan de efterfrågade taggarna i en array, som i sin tur läggs i en array. */
	private void handleJsonResponse() throws JSONException {
		//TODO: hantera fler exceptions kanske? t.ex. om man inte får åtkomst till api:t alls (ingen mottagning)
		
		JSONObject result = new JSONObject(responseText);
		JSONArray jsonArray = result.getJSONArray("movies");
		resultList = new ArrayList<Map<String, String>>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			listItem = new HashMap<String, String>(2);
			listItem.put("title", jsonObject.getString("title"));
			listItem.put("year", jsonObject.getString("year"));
			listItem.put("id", jsonObject.getString("id"));
			resultList.add(listItem);
			Log.i("MyMovieApp", "listItem: " + listItem);
		}
	}
}
