package nu.info.zeeshan.rnf;

import nu.info.zeeshan.utility.Constants;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Patterns;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_settings);
		addPreferencesFromResource(R.xml.activity_settings);
		// getSupportedFragmentManager().beginTransaction().replace(R.id.setting_fragment_holder,new
		// FragmentSettings()).commit();
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences spf, String key) {
		SharedPreferences ospf = getApplicationContext().getSharedPreferences(
				getString(R.string.pref_filename), Context.MODE_PRIVATE);
		if (key.equalsIgnoreCase(getString(R.string.pref_limit))) {
			ospf.edit()
					.putString(key,
							spf.getString(key, Constants.DEFAULT_FEED_LIMIT))
					.commit();
		} else if (valid(spf.getString(key, ""))) {

			ospf.edit().remove(key).commit();
			ospf.edit()
					.putString(key, spf.getString(key, Constants.EMPTY_FEED))
					.commit();

		} else {
			Toast.makeText(getApplicationContext(),
					getString(R.string.invalid_input), Toast.LENGTH_SHORT)
					.show();
		}
	}

	public boolean valid(String url) {
		if (url.isEmpty() || url.length() == 0)
			return false;
		return Patterns.WEB_URL.matcher(url).matches();
	}

	@Override
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}
}