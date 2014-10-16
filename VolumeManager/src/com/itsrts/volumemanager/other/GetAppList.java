package com.itsrts.volumemanager.other;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.itsrts.volumemanager.App;

public class GetAppList extends Thread {

	public static final String NEW = "NEW APP FOUND";
	public static final String FINISH = "SEACRH FINISHED FOR APPS";

	Searchable searchable;
	Activity act;

	public GetAppList(Searchable searchable, Activity act) {
		this.searchable = searchable;
		this.act = act;
	}

	@Override
	public void run() {
		SharedPreferences sp_last = act.getSharedPreferences("lastused",
				Context.MODE_PRIVATE);
		SharedPreferences sp_vol = act.getSharedPreferences("volume",
				Context.MODE_PRIVATE);

		PackageManager pm = act.getPackageManager();
		List<PackageInfo> packs = act.getPackageManager().getInstalledPackages(
				PackageManager.GET_ACTIVITIES);

		for (PackageInfo info : packs) {
			if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				continue;
			}

			// create the new app object
			App app = new App();
			app.setApp_name(info.applicationInfo.loadLabel(pm).toString());
			app.setPackage_name(info.applicationInfo.packageName);
			app.setImg(info.applicationInfo.loadIcon(pm));
			app.setLastused(sp_last.getLong(app.getPackage_name(), 1));
			app.setVolume(sp_vol.getInt(app.getPackage_name(), -1));

			send(app, NEW);
		}

		send(null, FINISH);
	}

	public void send(final App app, final String tag) {
		act.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				searchable.searchResult(app, tag);
			}
		});
	}

}
