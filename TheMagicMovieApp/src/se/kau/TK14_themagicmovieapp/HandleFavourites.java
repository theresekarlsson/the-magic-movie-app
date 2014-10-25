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
import android.widget.Toast;

public class HandleFavourites implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> favListItem;
	private List<Map<String, String>> favList = new ArrayList<Map<String, String>>();
	private String fileName = "my_movie_favourites.txt";
	private String separator = ";;";
	
	public void saveFavourite(MovieMainActivity mma, String title, String year, String id) {
		
		Log.i("MyMovieApp", "HandleFavourites. Movie received");
		StringBuilder favToSave = new StringBuilder();
		favToSave.append(title + separator + year + separator + id + "\n");
		Log.i("MyMovieApp", "HandleFavourites. Favourite to add: " + favToSave);	

		try {
			FileOutputStream fileOut = mma.openFileOutput(fileName, Context.MODE_APPEND);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOut);
	        outputStreamWriter.append(favToSave.toString());
	        outputStreamWriter.close();
	        Log.i("MyMovieApp", "HandleFavourites. Movie written to file.");

		} catch (IOException e) {
            Log.e("MyMovieApp", "Writing to file failed: " + e.toString());
   
            CharSequence text = "Could not save movie as favourite";
	    	Toast ftoast = Toast.makeText(mma, text, Toast.LENGTH_SHORT);
	    	ftoast.show();
        } 
	}

	
	public List<Map<String, String>> getFavList(FavouriteMoviesActivity fma) throws Exception {
		try {
	        InputStream inputStream = fma.openFileInput(fileName);
	        if ( inputStream != null ) 
	        {
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = null;

	            while ( (receiveString = bufferedReader.readLine()) != null ) {
	            	Log.i("MyMovieApp", "HandleFavourites. String read from file: " + receiveString);
	            	String[] separated = receiveString.split(";;");
	            	favListItem = new HashMap<String, String>(2);
	            	favListItem.put("title", separated[0].toString());
	            	favListItem.put("year", separated[1].toString());
	            	favListItem.put("id", separated[2].toString());
	            	Log.i("MyMovieApp","HandleFavourites. Movie converted to hashmap: " + favListItem);
	            	favList.add(favListItem);
	            }
	            inputStream.close();
	        }
	    }
	    catch (FileNotFoundException e) {
	        Log.e("MyMovieApp", "File not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("MyMovieApp", "Can not read file: " + e.toString());
	    }
		Log.i("MyMovieApp", "HandleFavourites. About to send favList to FavouriteActivity.");
		return favList;
	}
}
