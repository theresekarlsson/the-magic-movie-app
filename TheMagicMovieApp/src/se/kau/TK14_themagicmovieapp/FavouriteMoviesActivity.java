package se.kau.TK14_themagicmovieapp;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FavouriteMoviesActivity extends Activity implements OnClickListener {
	
	private ListView listviewFavourites;
	private List<Map<String, String>> favList;
	private SimpleAdapter favAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourite_layout);
		Log.i("MyMovieApp", "FavouritesActivity. Inne i onCreate.");
		
		Intent intent = getIntent();
		HandleFavourites handleFavs = (HandleFavourites) intent.getSerializableExtra("HandleFavourites");
		
		try {
			favList = handleFavs.getFavList(this); //Hämtar favoritlista
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		favAdapter = new SimpleAdapter(FavouriteMoviesActivity.this, favList, R.layout.fav_list_layout, new String[] { "title", "year" }, 
				new int[] { R.id.favText1, R.id.favText2 });
		
		listviewFavourites = (ListView) findViewById(R.id.listView2);
		listviewFavourites.setAdapter(favAdapter);
		listviewFavourites.setDividerHeight(5);
	}
	
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

