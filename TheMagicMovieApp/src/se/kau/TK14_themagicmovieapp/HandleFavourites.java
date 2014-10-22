package se.kau.TK14_themagicmovieapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.util.Log;

public class HandleFavourites  {
	
	private Map<String, String> favListItem;
	private List<Map<String, String>> favList;
	String fileName = "my_favourite_movies";
	
	public void saveFavourite(String title, String year, String id) throws Exception {
		
		Log.i("MyMovieApp", "Favourites. Inne i saveFavourite.");

		favListItem = new HashMap<String, String>(2);
		favListItem.put("title", title);
		favListItem.put("year", year);
		favListItem.put("id", id);
		favList = new ArrayList<Map<String, String>>();
		favList.add(favListItem);
		
		Log.i("MyMovieApp", "favListItem: " + favListItem);	
		
		/*
		fileName = "favourite_movies.txt";
		FileOutputStream outputStream;
		outputStream = new FileOutputStream(fileName);
		
		ObjectOutputStream objectOutputStream= new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(favList);
		objectOutputStream.close();
		*/
	}
	

	public List<Map<String, String>> getFavList() throws StreamCorruptedException, IOException, ClassNotFoundException, FileNotFoundException {
		Log.i("HandleFavourites", "inne i getFavList.");
		/*
		FileInputStream inputStream  = new FileInputStream(fileName);
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

		readFavList = new ArrayList<Map<String, String>>();
		ArrayList<Map<String, String>> readFavList = (ArrayList<Map<String, String>>) objectInputStream.readObject();
		inputStream.close();
		*/
		return favList;
	}
	
}
