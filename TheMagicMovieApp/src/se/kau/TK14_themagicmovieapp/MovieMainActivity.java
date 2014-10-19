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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MovieMainActivity extends Activity implements OnClickListener {

	private SearchAndReceive search;
	private SimpleAdapter anAdapter;
	private String sender, searchData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_main);
		Log.i("MusicApp", "Inne i onCreate.");
	}
	@Override
	public void onClick(View v) {
		Log.i("MusicApp", "Inne i onClick.");
		sender = null;
		searchData = null;
		
		switch (v.getId()) {
			case R.id.imageSearchButton:
				EditText searchFieldInput = (EditText) findViewById(R.id.searchField);
				searchData = searchFieldInput.getText().toString().replace(' ', '+');
				sender = "imageSearchButton";
				search = new SearchAndReceive(MovieMainActivity.this, searchData, sender);
				search.execute();
			break;
			
			case R.id.addToFavBtn:
				//TextView titleToAdd = (TextView) findViewById(R.id.text1);
				/*TODO: skapa favoritlista där filmen läggs till. I egen metod. */
				CharSequence text = "Added to favourites";
            	Toast toast = Toast.makeText(MovieMainActivity.this, text, Toast.LENGTH_SHORT);
            	toast.show();
			break;
			
			case R.id.searchSimBtn:
				TextView requestedTitle = (TextView) findViewById(R.id.text1);
    			searchData = requestedTitle.getText().toString().replace(' ', '+');
    			sender = "searchSimBtn";
    			search = new SearchAndReceive(MovieMainActivity.this, searchData, sender);
    			search.execute();
                Toast.makeText(MovieMainActivity.this,"Du har klickat på Search Similar Movies, " + sender, Toast.LENGTH_SHORT).show();
			break;
			
		}
		
	}
/* Presenterar sökresultatet i en lista på skärmen. */
	public void displayResult(List<Map<String, String>> resultList) {
		Log.i("MusicApp", "Inne i displayResult");
		
		anAdapter = new SimpleAdapter(MovieMainActivity.this, resultList, R.layout.list_layout, new String[] { "title", "year" }, 
				new int[] { R.id.text1, R.id.text2 });
		
		ListView listviewResults = (ListView) findViewById(R.id.listView1);
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