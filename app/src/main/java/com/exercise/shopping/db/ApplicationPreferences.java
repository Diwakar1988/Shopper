package com.exercise.shopping.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class ApplicationPreferences {

	private static final String TAG = ApplicationPreferences.class.getName();
	private static final String NAME = "pref_shopping";
	private static ApplicationPreferences _instance ;

	private interface Keys {
		public static final String AUTO_SYNC = "auto_sync";
		public static final String DB_CREATED = "db_created";
	}

	private SharedPreferences preferences;

	private ApplicationPreferences(Context context) {
		preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	}

	public static void init(Context context) {
		_instance = new ApplicationPreferences(context);
	}
	public static ApplicationPreferences getInstance() {
		return _instance;
	}
	
	
	
	/**
	 * This Method Clear shared preference.
	 */
	protected void clear() {
		Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
	}

	protected void commit() {
		preferences.edit().commit();
	}

	private boolean getBoolean(String key, boolean defaultValue) {
		if (preferences != null && key != null && preferences.contains(key)) {
			return preferences.getBoolean(key, defaultValue);
		}
		return defaultValue;
	}

	private void putBoolean(String key, boolean value) {
		try {
			if (preferences != null) {
				Editor editor = preferences.edit();
				editor.putBoolean(key, value);
				editor.commit();
			}
		} catch (Exception e) {
			Log.e(TAG, "Unable Put Boolean in Shared preference", e);
		}
	}
	public boolean isAutoSyncEnabled() {
		return getBoolean(Keys.AUTO_SYNC, true);
	}
	public void toggleAutoSyncEnabled() {
		putBoolean(Keys.AUTO_SYNC, !isAutoSyncEnabled());
	}
	public boolean isDBCreated() {
		return getBoolean(Keys.DB_CREATED, false);
	}
	public void setDBCreated() {
		putBoolean(Keys.DB_CREATED, !true);
	}
	

}