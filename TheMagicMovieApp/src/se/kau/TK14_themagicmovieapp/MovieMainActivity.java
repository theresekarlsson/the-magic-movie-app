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
//import android.widget.Toast;

public class MovieMainActivity extends Activity implements OnClickListener {

	private SearchAndReceive search;
	private SimpleAdapter aa;				//ändra namn!
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_main);
		
		View imageSearchButton = findViewById(R.id.imageSearchButton);  
		imageSearchButton.setOnClickListener(this);						//sätter lyssnare på söksymbolen
		
		Log.i("MusicApp", "Inne i onCreate.");
		
		//TO DO gör knappar för lägg till favorit och sölk liknande
		//TO DO sätt lyssnare på dem också
		
	}
	@Override
	public void onClick(View v) {
		Log.i("MusicApp", "Inne i onClick.");
		switch (v.getId()) {
		case R.id.imageSearchButton:
			
			EditText searchFieldInput = (EditText) findViewById(R.id.searchField);
		
			String searchInputData = searchFieldInput.getText().toString().replace(' ', '+');
			search = new SearchAndReceive(MovieMainActivity.this, searchInputData);
			search.execute();

			/*
			CharSequence text = "Nu har vi klickat på sök";
			Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
			toast.show();
			break;
			*/
		}
		
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

	public void displayResult(List<Map<String, String>> result) {
		aa = new SimpleAdapter(MovieMainActivity.this, result, R.layout.list_layout, new String[] { "Title" }, new int[] { R.id.text1 });
		ListView lw = (ListView) findViewById(R.id.listView1);
		lw.setAdapter(aa);
		lw.setDividerHeight(5);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie_main, menu);
		return true;
	}
}
