package se.kau.TK14_themagicmovieapp;

import java.util.HashMap;
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
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class HandleFavouritesActivity extends Activity implements OnClickListener {
		
	private ListView listviewFavourites;
	private Map<String, String> favListItem;
	private List<Map<String, String>> favList;
	private SimpleAdapter favAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favourite_layout);
		
		Log.i("MyMovieApp", "Favourites. Inne i onCreate.");
		
		displayFavourites();
	}


	@Override
	public void onClick(View v) {
		Log.i("MyMovieApp", "Favourites. Inne i onClick.");
		finish();
	}
	
	/* Flytta till egen klass? */
	public void saveFavourite(RelativeLayout itemToAdd) {
		
		// ska sparas i lista (finnas kvar när telefonen stängs av)
		TextView rowTitle = (TextView)itemToAdd.getChildAt(1);
        TextView rowYear = (TextView)itemToAdd.getChildAt(4);
        TextView rowId = (TextView)itemToAdd.getChildAt(5);
        String title = rowTitle.getText().toString();
        String year = rowYear.getText().toString(); 
        String id = rowId.getText().toString();
        
        favListItem = new HashMap<String, String>(2);
		favListItem.put("title", title);
		favListItem.put("year", year);
		favListItem.put("id", id);
		favList.add(favListItem);
		
		Log.i("MyMovieApp", "favListItem: " + favListItem);
		
	}
	
	public void displayFavourites() {
		favAdapter = new SimpleAdapter(HandleFavouritesActivity.this, favList, R.layout.fav_list_layout, new String[] { "title", "year", "id" }, 
				new int[] { R.id.favText1, R.id.favText2, R.id.favMovieId });
		listviewFavourites = (ListView) findViewById(R.id.listView2);
		listviewFavourites.setAdapter(favAdapter);
		listviewFavourites.setDividerHeight(5);
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

