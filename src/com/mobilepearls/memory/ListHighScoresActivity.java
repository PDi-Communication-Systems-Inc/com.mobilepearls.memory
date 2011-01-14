package com.mobilepearls.memory;

import java.util.ArrayList;
import java.util.List;

import android.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mobilepearls.memory.HighScoreDatabase.HighScoreEntry;

public class ListHighScoresActivity extends ListActivity {

	public static String JUST_STORED = "com.mobilepearls.memory.just_stored";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		HighScoreDatabase db = HighScoreDatabase.getDatabase(this);
		final List<String> list = new ArrayList<String>();
		for (HighScoreEntry entry : db.getSortedHighScores()) {
			list.add(entry.score + " s - " + entry.name);
		}

		setContentView(com.mobilepearls.memory.R.layout.highscorelist);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.simple_list_item_1, list));

		Button closeButton = (Button) findViewById(com.mobilepearls.memory.R.id.closeButton);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// Intent intent = getIntent();
		// Bundle extras = intent.getExtras();
		// if (extras != null && extras.getBoolean(JUST_STORED)) {
		// AlertDialog.Builder alert = new AlertDialog.Builder(this);
		// alert.setMessage("Do you want to share your high score?");
		// alert.setCancelable(true);
		// alert.setNegativeButton("No", null);
		// alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int whichButton) {
		// share();
		// }
		// });
		// alert.show();
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(com.mobilepearls.memory.R.menu.highscore_menu, menu);
		return true;
	}

	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		// Toast.makeText(this, "You have selected " + presidents[position], Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == com.mobilepearls.memory.R.id.shareHighScoreMenuItem) {
			share();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	void share() {
		HighScoreDatabase db = HighScoreDatabase.getDatabase(this);
		List<HighScoreEntry> list = db.getSortedHighScores();
		if (list.isEmpty()) {
			Toast.makeText(this, "No high score to share yet!", Toast.LENGTH_SHORT).show();
			return;
		}
		StringBuilder buffer = new StringBuilder("My high score in Memory (http://mobilepearls.com):\n\n");
		for (HighScoreEntry entry : list) {
			buffer.append(entry.score).append(" s - ").append(entry.name).append('\n');
		}
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType("text/plain");
		// sendIntent.putExtra("sms_body", "The SMS text");
		intent.putExtra(Intent.EXTRA_TEXT, buffer.toString());
		intent.putExtra(Intent.EXTRA_SUBJECT, "Memory high score");
		startActivity(Intent.createChooser(intent, "Share high score"));
	}

}
