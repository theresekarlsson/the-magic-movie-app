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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MovieMainActivity extends Activity implements OnClickListener {

	private SearchAndReceive search;
	private SimpleAdapter anAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_main);
		Log.i("MusicApp", "Inne i onCreate.");
		View imageSearchButton = findViewById(R.id.imageSearchButton); 
		imageSearchButton.setOnClickListener(this);						//sätter lyssnare på söksymbolen
		

	}
	@Override
	public void onClick(View v) {
		Log.i("MusicApp", "Inne i onClick.");
		switch (v.getId()) {
			case R.id.imageSearchButton:
			
			EditText searchFieldInput = (EditText) findViewById(R.id.searchField);
			String searchData = searchFieldInput.getText().toString().replace(' ', '+');
			search = new SearchAndReceive(MovieMainActivity.this, searchData);
			search.execute();
			break;
			/*
			case R.id.addToFavBtn:
			break;
			case R.id.searchSimBtn:
			break;
			*/
		}
		
	}

	public void displayResult(List<Map<String, String>> resultList) {
		Log.i("MusicApp", "Inne i displayResult");
		ListView listview = (ListView) findViewById(R.id.listView1);
		
		/* TODO: Kan jag inte använda samma onClick som när jag söker?? Gillar inte den här lösningen.. Har nu 3 onClick-metoder. FIXA! */
		anAdapter = new SimpleAdapter(MovieMainActivity.this, resultList, R.layout.list_layout, new String[] { "title", "year" }, new int[] { R.id.text1, R.id.text2 })
		{
	        @Override
	        public View getView (int position, View convertView, ViewGroup parent)
	        {
	            View v = super.getView(position, convertView, parent);

	            Button btnAddToFav=(Button)v.findViewById(R.id.addToFavBtn);
	            btnAddToFav.setOnClickListener(new OnClickListener() {

	                public void onClick(View arg0) {
	                	// TODO: skapa en lista, lägg till titeln där. Ska man kunna öppna denna lista? Isåfall, ny klass, layout, intent.
	                	
	                	CharSequence text = "Added to favourites";
	                	Toast toast = Toast.makeText(MovieMainActivity.this, text,Toast.LENGTH_SHORT);
	                	toast.show();
	                }
	            });
	            
	            Button btnSearchSim=(Button)v.findViewById(R.id.searchSimBtn);
	            btnSearchSim.setOnClickListener(new OnClickListener() {

	                public void onClick(View arg0) {
	                	
	                	/* TODO: hämta texten (=söksträng) från den item man är i/textview det gäller, och anropa searchandreceive. 
	                	 * Ska söka efter liknande filmer! Liknande i stil!! Om man klickr på searcg en gång funkar det. En gång till
	                	 * så krashar appen.
	                	 */
	                	TextView requestedTitle = (TextView) findViewById(R.id.text1);
	        			String searchData = requestedTitle.getText().toString().replace(' ', '+');
	        			search = new SearchAndReceive(MovieMainActivity.this, searchData);
	        			search.execute();
	        			
	                    Toast.makeText(MovieMainActivity.this,"Du har klickat på Search Similar Movies",Toast.LENGTH_SHORT).show();
	                }
	            });
	            return v;
	        }
	    };
		listview.setAdapter(anAdapter);
		listview.setDividerHeight(5);
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