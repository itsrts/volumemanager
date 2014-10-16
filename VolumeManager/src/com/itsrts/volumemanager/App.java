package com.itsrts.volumemanager;

import java.util.Calendar;

import android.graphics.drawable.Drawable;

public class App implements Comparable<App> {
	String package_name;
	String app_name;
	Drawable img;
	int volume;// -1 if not to be custom
	long lastused = 1;

	public void updateTime() {
		Calendar c = Calendar.getInstance();
		setLastused(c.getTimeInMillis());
	}

	public void setLastused(long lastused) {
		this.lastused = lastused;
	}

	public long getLastused() {
		return lastused;
	}

	public String getPackage_name() {
		return package_name;
	}

	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public Drawable getImg() {
		return img;
	}

	public void setImg(Drawable img) {
		this.img = img;
	}

	@Override
	public int compareTo(App another) {
		if (lastused > another.lastused)
			return -1;
		if (lastused < another.lastused)
			return 1;
		return app_name.compareTo(another.app_name);
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

}
