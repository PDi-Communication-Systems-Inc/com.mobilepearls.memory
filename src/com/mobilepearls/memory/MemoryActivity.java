package com.mobilepearls.memory;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mobilepearls.memory.MemoryGame.GameListener;

public class MemoryActivity extends Activity {

	private static final String GAME_KEY = "game";

	MemoryGame game;
	private MemoryView view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			game = (MemoryGame) savedInstanceState.getSerializable(GAME_KEY);
		}
		if (game == null) {
			game = new MemoryGame(6, 6);
		}
		setContentView(R.layout.main);

		GameListener listener = new GameListener() {
			@Override
			public void gameOver(MemoryGame game) {
				// do nothing
			}

			public void gamePaused(MemoryGame game) {
				// do nothing
			}

			public void gameResumed(MemoryGame game) {
				// do nothing
			}

			@Override
			public void gameStarted(MemoryGame game) {
				// do nothing
			}
		};
		game.setListener(listener);

		view = (MemoryView) findViewById(R.id.android_memoryview);

		if (game.isWaitingForTimeout()) {
			// game was aborted while waiting for timeout - do it now
			// slight penalty since full timeout is needed:
			view.startTimeoutCountdown();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.restart_menu) {
			game.restart();
			view.invalidate();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onPause();
		game.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (game.isStarted()) {
			game.resume();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(GAME_KEY, game);
	}

}