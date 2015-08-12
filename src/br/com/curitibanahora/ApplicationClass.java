package br.com.curitibanahora;

import java.util.HashMap;

import android.app.Application;
import android.provider.Settings.Secure;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.parse.SaveCallback;

public class ApplicationClass extends Application {
	private static final String PROPERTY_ID = "UA-52875041-1";

	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	public enum TrackerName {
		APP_TRACKER, // Tracker used only in this app.
		GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg:
						// roll-up tracking.
	}

	@Override
	public void onCreate() {
		super.onCreate();
		String android_id = Secure.getString(this.getContentResolver(),
				Secure.ANDROID_ID);
		Parse.initialize(this, "JHB2W0B9y1guh9h4JXZASVNiVfccz81kukiexs5I",
				"lffGSQueeWmkkPunTL04MaNKIx7qA5ITvnuSm6DO");
		ParseInstallation installation = ParseInstallation
				.getCurrentInstallation();
		installation.put("UniqueId", android_id);
		installation.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException arg0) {
				PushService.setDefaultPushCallback(ApplicationClass.this,
						MainActivity.class);

			}
		});
	//	List<String> subscribedChannels = installation.getCurrentInstallation().getList("channels");
		((ApplicationClass) getApplicationContext())
				.getTracker(ApplicationClass.TrackerName.APP_TRACKER);

	}

	synchronized Tracker getTracker(TrackerName trackerId) {
		if (!mTrackers.containsKey(trackerId)) {

			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics
					.newTracker(R.xml.analytics)
					: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics
							.newTracker(PROPERTY_ID) : analytics
							.newTracker(R.xml.analytics);
			mTrackers.put(trackerId, t);

		}
		return mTrackers.get(trackerId);
	}
}
