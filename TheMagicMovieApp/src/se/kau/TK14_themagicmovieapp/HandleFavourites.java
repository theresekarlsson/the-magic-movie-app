package se.kau.TK14_themagicmovieapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;


public class HandleFavourites  extends AsyncTask<Void, Void, Void> {
	
	private FavouriteMoviesActivity handleFavs;
	private Map<String, String> favListItem;
	private List<Map<String, String>> favList;
	private String titleToAdd, yearToAdd, idToAdd;
	

	public HandleFavourites(String title, String year, String id) {
		titleToAdd = title;
		yearToAdd = year;
		idToAdd = id;
	}

	@Override
	protected Void doInBackground(Void... params) {
		saveFavourite();
		return null;
	}
	
	public void saveFavourite() {
		
		Log.i("MyMovieApp", "Favourites. Inne i saveFavourite.");
		
        favListItem = new HashMap<String, String>(2);
		favListItem.put("title", titleToAdd);
		favListItem.put("year", yearToAdd);
		favListItem.put("id", idToAdd);
		favList = new ArrayList<Map<String, String>>();
		favList.add(favListItem);
		
		Log.i("MyMovieApp", "favListItem: " + favListItem);	
	}
	
	protected void onPostExecute(Void results) {
		try
		{
			handleFavs.displayFavourites(favList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
