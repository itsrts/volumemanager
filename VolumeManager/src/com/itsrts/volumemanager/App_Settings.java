package com.itsrts.volumemanager;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class App_Settings extends DialogFragment implements
		OnSeekBarChangeListener, OnClickListener {

	boolean changed = false;
	App app;
	Button save, close;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);

		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.setTitle("Set volume for this App");
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}

	public App_Settings setApp(App app) {
		this.app = app;
		return this;
	}

	TextView name;
	SeekBar sb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v;
		v = inflater.inflate(R.layout.app_settings, null);
		name = (TextView) v.findViewById(R.id.appname);
		sb = (SeekBar) v.findViewById(R.id.appvol);
		save = (Button) v.findViewById(R.id.app_save);
		save.setOnClickListener(this);

		close = (Button) v.findViewById(R.id.app_close);
		close.setOnClickListener(this);

		AudioManager am = (AudioManager) getActivity().getSystemService(
				Context.AUDIO_SERVICE);

		int max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int vol = am.getStreamVolume(AudioManager.STREAM_MUSIC);

		sb.setMax(max);
		name.setText(app.getApp_name());
		sb.setProgress(vol);
		if (app.volume != -1)
			sb.setProgress(app.volume);

		sb.setOnSeekBarChangeListener(this);

		return v;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		changed = true;
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.app_save:
			dismiss();
			
			if (!changed)
				return;

			SharedPreferences sp_last = getActivity().getSharedPreferences(
					"lastused", Context.MODE_PRIVATE);
			app.updateTime();
			sp_last.edit().putLong(app.getPackage_name(), app.getLastused())
					.commit();

			SharedPreferences sp_vol = getActivity().getSharedPreferences(
					"volume", Context.MODE_PRIVATE);
			app.setVolume(sb.getProgress());
			sp_vol.edit().putInt(app.getPackage_name(), app.getVolume())
					.commit();
			dismiss();
			break;
		case R.id.app_close:
			dismiss();
		default:
			break;
		}
	}
}
