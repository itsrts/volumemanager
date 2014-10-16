package com.itsrts.volumemanager;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.itsrts.volumemanager.other.GetAppList;
import com.itsrts.volumemanager.other.Searchable;

public class MainActivity extends FragmentActivity implements Updatable,
		Searchable {

	ArrayList<App> list;
	ListView lv;
	MyAdapter adapter;
	ProgressBar pb;

	@Override
	protected void onResume() {
		super.onResume();
		update();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// setContentView(R.layout.app_settings);

		setContentView(R.layout.activity_main);

		lv = (ListView) findViewById(R.id.listView1);
		if (lv == null)
			return;
		pb = (ProgressBar) findViewById(R.id.apppb);
		list = new ArrayList<>();

		adapter = new MyAdapter(this, list);

		lv.setAdapter(adapter);
		new GetAppList(this, this).start();
		Intent in = new Intent();
		in.setClass(this, BackgroundTask.class);
		startService(in);

	}

	@Override
	public void update() {
		if (list == null || adapter == null)
			return;
		Collections.sort(list);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void searchResult(App app, String tag) {
		if (tag.equals(GetAppList.NEW)) {
			list.add(app);
			pb.setVisibility(View.VISIBLE);
		}
		if (tag.equals(GetAppList.FINISH)) {
			pb.setVisibility(View.INVISIBLE);
			update();
		}
	}
}
