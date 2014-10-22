package se.kau.TK14_themagicmovieapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.util.Log;

public class HandleFavourites  {
	
	private String titleToAdd, yearToAdd, idToAdd;
	private Map<String, String> favListItem;
	private List<Map<String, String>> favList;
	
	public void saveFavourite(String title, String year, String id) throws Exception {
		
		Log.i("MyMovieApp", "Favourites. Inne i saveFavourite.");
		
		titleToAdd = title;
		yearToAdd = year;
		idToAdd = id;
		
		favListItem = new HashMap<String, String>(2);
		favListItem.put("title", titleToAdd);
		favListItem.put("year", yearToAdd);
		favListItem.put("id", idToAdd);
		favList = new ArrayList<Map<String, String>>();
		favList.add(favListItem);
		
		Log.i("MyMovieApp", "favListItem: " + favListItem);	
	}
	
	public List<Map<String, String>> getFavList() {
		Log.i("HandleFavourites", "inne i getFavList.");
		return favList;
	}
}
