package se.kau.TK14_themagicmovieapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.util.Log;

public class HandleFavourites implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> favListItem;
	private List<Map<String, String>> favList = new ArrayList<Map<String, String>>();
	
	public void saveFavourite(String title, String year) throws Exception {
		
		Log.i("MyMovieApp", "Favourites. Inne i saveFavourite.");

		favListItem = new HashMap<String, String>(2);
		favListItem.put("title", title);
		favListItem.put("year", year);
		favList.add(favListItem);
		
		Log.i("MyMovieApp", "Favourite to add: " + favListItem);	
	}
	
	public List<Map<String, String>> getFavList() throws Exception {
		Log.i("HandleFavourites", "Skickar favList.");
		return favList;
	}
}
