package se.kau.TK14_themagicmovieapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

public class HandleFavourites implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> favListItem = new HashMap<String, String>(2);;
	private List<Map<String, String>> favList = new ArrayList<Map<String, String>>();
	private String fileName = "my_movie_favourites.txt";
	private String separator = ";;";
	
	public void saveFavourite(MovieMainActivity mma, String title, String year, String id) throws Exception {
		
		Log.i("MyMovieApp", "Favourites. Inne i saveFavourite.");
	
		StringBuilder favToSave = new StringBuilder();
		favToSave.append(title + separator + year + separator + id);
		Log.i("MyMovieApp", "Favourite to add: " + favToSave);	

		try {
			FileOutputStream fileOut = mma.openFileOutput(fileName, Context.MODE_PRIVATE);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOut);
	        outputStreamWriter.append(favToSave.toString());
	        outputStreamWriter.close();
	        Log.i("MyMovieApp", "Favourites. Written to file.");
		} catch (IOException e) {
            Log.i("MyMovieApp", "Write to file failed: " + e.toString());
        } 
	}

	
	public List<Map<String, String>> getFavList(FavouriteMoviesActivity fma) throws Exception {
		Log.i("HandleFavourites", "Skickar favList.");
		
		try {
	        InputStream inputStream = fma.openFileInput(fileName);

	        if ( inputStream != null ) {
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = null;

	            while ( (receiveString = bufferedReader.readLine()) != null ) {
	            	Log.i("MyMovieApp", "Favourites. Line from file: " + receiveString);
	            }

	            inputStream.close();
	            //ret = stringBuilder.toString();
	        }
	    }
	    catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
	    }
		
		return favList;
	}
}
