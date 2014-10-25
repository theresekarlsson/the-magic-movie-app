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
	private SearchMovie search_movie;
	private SearchSimilarMovie search_similar_movie;
	private HandleFavourites handle_favs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_main);
		handle_favs = new HandleFavourites();
	}
	
	@Override
	public void onClick(View v) {
		Log.i("MyMovieApp", "MovieMainActivity. Inne i onClick.");
		switch (v.getId()) {
		
			case R.id.favButton:
				Intent intent = new Intent(MovieMainActivity.this, FavouriteMoviesActivity.class);
				intent.putExtra("HandleFavourites", handle_favs);
				Log.i("MyMovieApp", "MovieMainActivity. About to go to FavouriteActivity.");
				startActivity(intent);
			break;
			
			case R.id.imageSearchButton:
				EditText searchFieldInput = (EditText) findViewById(R.id.searchField);
				String searchMovie = searchFieldInput.getText().toString();
				try {
					if (searchFieldInput.getText().toString().trim().length() < 1) { //Kollar så rutan inte är tom.
						throw new NullPointerException();
					}
					else {
						searchMovie.replace(' ', '+');
						Log.i("MyMovieApp", "MovieMainActivity. Search for movie: " + searchMovie);
						search_movie = new SearchMovie(MovieMainActivity.this, searchMovie);
						search_movie.execute();
					}
				} catch (NullPointerException e) {
					CharSequence text = "Insert title/part of title.";
			    	Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
			    	toast.show();
				}
			break;
			
			case R.id.searchSimBtn:
				RelativeLayout itemRow = (RelativeLayout)v.getParent();
		        TextView rowChild = (TextView)itemRow.getChildAt(4);
		        String movieId = rowChild.getText().toString();
		        Log.i("MyMovieApp", "MovieMainActivity. Search similar movie-ID: " + movieId);
		        search_similar_movie = new SearchSimilarMovie(MovieMainActivity.this, movieId);
		        search_similar_movie.execute();
			break;
			
			case R.id.addToFavBtn:
				RelativeLayout itemToAdd = (RelativeLayout) v.getParent();
				TextView rowTitle = (TextView)itemToAdd.getChildAt(1);
		        TextView rowYear = (TextView)itemToAdd.getChildAt(3);
		        TextView rowId = (TextView)itemToAdd.getChildAt(4);
		        String title = rowTitle.getText().toString();
		        String year = rowYear.getText().toString();
		        String id = rowId.getText().toString();
            	
				Log.i("MyMovieApp", "MovieMainActivity. About to send movie to HandleFavourites");
				handle_favs.saveFavourite(this, title, year, id);
				
		        CharSequence text = "You added " + title + " to your favourites";
		    	Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		    	toast.show();
			break;
		}
	}
	
	/* Tar emot och presenterar sökresultatet i en listview. */
	public void displayResult(List<Map<String, String>> resultList) {
		Log.i("MyMovieApp", "MovieMainActivity. Presenting search result");
		
		anAdapter = new SimpleAdapter(MovieMainActivity.this, resultList, R.layout.list_layout, 
		        new String[] { "title", "year", "id" }, 
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