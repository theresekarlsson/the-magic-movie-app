package se.kau.TK14_themagicmovieapp;

import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FavouriteMoviesActivity extends Activity implements OnClickListener {
	
	private HandleFavourites handleFavs;
	private ListView listviewFavourites;
	private List<Map<String, String>> favList;
	private SimpleAdapter favAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourite_layout);
		
		Log.i("MyMovieApp", "Favourites. Inne i onCreate.");
		
		displayFavourites();
	}
	
	public void displayFavourites() {
		
		Log.i("MyMovieApp", "Favourites. Inne i displayFavourites.");
		
		try {
			favList = handleFavs.getFavList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		favAdapter = new SimpleAdapter(FavouriteMoviesActivity.this, favList, R.layout.fav_list_layout, new String[] { "title", "year", "id" }, 
				new int[] { R.id.favText1, R.id.favText2, R.id.favMovieId });
		
		listviewFavourites = (ListView) findViewById(R.id.listView2);
		listviewFavourites.setAdapter(favAdapter);
		listviewFavourites.setDividerHeight(5);
	}
	
	@Override
	public void onClick(View v) {
		Log.i("MyMovieApp", "Favourites. Inne i onClick.");
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

