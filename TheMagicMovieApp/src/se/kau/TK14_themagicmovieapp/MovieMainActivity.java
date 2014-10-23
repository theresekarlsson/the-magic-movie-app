package se.kau.TK14_themagicmovieapp;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MovieMainActivity extends Activity implements OnClickListener {

	private SimpleAdapter anAdapter;
	private ListView listviewResults;
	private SearchAndReceive search;
	private SearchSimilarMovie search_similar_movie;
	private HandleFavourites handleFavs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_main);
		handleFavs = new HandleFavourites();
	}
	
	@Override
	public void onClick(View v) {
		Log.i("MyMovieApp", "Mainklass. Inne i onClick.");
		String btnClicked = null;
		
		
		switch (v.getId()) {
			case R.id.imageSearchButton:
				EditText searchFieldInput = (EditText) findViewById(R.id.searchField);
				String searchMovie = searchFieldInput.getText().toString().replace(' ', '+');
		        btnClicked = "imageSearchButton";
				search = new SearchAndReceive(MovieMainActivity.this, searchMovie, btnClicked);
				search.execute();
			break;
			
			case R.id.favButton:
				Intent intent = new Intent(MovieMainActivity.this, FavouriteMoviesActivity.class);
				intent.putExtra("HandleFavourites", handleFavs);
				startActivity(intent);
			break;
			
			case R.id.addToFavBtn:
			
				RelativeLayout itemToAdd = (RelativeLayout) v.getParent();
				TextView rowTitle = (TextView)itemToAdd.getChildAt(1);
		        TextView rowYear = (TextView)itemToAdd.getChildAt(3);
		        String title = rowTitle.getText().toString();
		        String year = rowYear.getText().toString(); 
            	Log.i("MyMovieApp", "Ska läggas till: " + title + ", " + year);
            	
				try {
					handleFavs.saveFavourite(title, year);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				CharSequence text = "You added " + title + " to your favourites";
		    	Toast toast = Toast.makeText(MovieMainActivity.this, text, Toast.LENGTH_SHORT);
		    	toast.show();
			break;
			
			case R.id.searchSimBtn:
				RelativeLayout itemRow = (RelativeLayout)v.getParent();
		        TextView rowChild = (TextView)itemRow.getChildAt(4);
		        String movieId = rowChild.getText().toString();
		        search_similar_movie = new SearchSimilarMovie(MovieMainActivity.this, movieId);
		        search_similar_movie.execute();
			break;
		}
	
	}
/* Presenterar sökresultatet i en lista på skärmen. */
	public void displayResult(List<Map<String, String>> resultList) {
		Log.i("MyMovieApp", "Main. Inne i displayResult");
		
		anAdapter = new SimpleAdapter(MovieMainActivity.this, resultList, R.layout.list_layout, new String[] { "title", "year", "id" }, 
				new int[] { R.id.text1, R.id.text2, R.id.movieId });
		listviewResults = (ListView) findViewById(R.id.listView1);
		listviewResults.setAdapter(anAdapter);
		listviewResults.setDividerHeight(5);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie_main, menu);
		return true;
	}
	
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