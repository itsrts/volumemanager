package com.itsrts.volumemanager;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.IBinder;

public class BackgroundTask extends Service implements Runnable {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(this).start();
		return START_STICKY;
	}

	@Override
	public void run() {
		int last_vol = -1;
		String last_app = "";
		SharedPreferences sp = getSharedPreferences("myprefs",
				Context.MODE_PRIVATE);
		while (true) {
			try {
				List<RunningTaskInfo> appProcesses = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
						.getRunningTasks(1);

				if (appProcesses == null) {
					continue;
				}

				AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				if (last_vol == -1)
					last_vol = am.getStreamVolume(AudioManager.STREAM_MUSIC);

				for (RunningTaskInfo appProcess : appProcesses) {
					String curnt_app = appProcess.baseActivity.getPackageName();
					int curnt_vol = am
							.getStreamVolume(AudioManager.STREAM_MUSIC);

					// if the same app is same as the last app
					// continue the loop
					if (last_app.equals(curnt_app))
						continue;

					// as the visible app is changed
					// check for volume to be saved
					// check for volume to be changed

					// if the last_app volume is to be remembered
					// save the current volume
					if (sp.getInt(last_app, -1) != -1) {
						sp.edit().putInt(last_app, curnt_vol).commit();
					} else
						last_vol = curnt_vol;

					// if the curnt_app volume is to be remembered
					// set the volume
					if (sp.getInt(curnt_app, -1) != -1) {
						curnt_vol = sp.getInt(curnt_app, 0);
						am.setStreamVolume(AudioManager.STREAM_MUSIC,
								curnt_vol, 0);
					} else {
						curnt_vol = last_vol;
						am.setStreamVolume(AudioManager.STREAM_MUSIC,
								curnt_vol, 0);
					}

					// updating the value of
					last_app = curnt_app;
				}

			} catch (Exception e) {
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
