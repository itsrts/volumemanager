package com.itsrts.volumemanager;

import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter implements OnClickListener {

	ArrayList<App> list;
	FragmentActivity act;

	public MyAdapter(FragmentActivity act, ArrayList<App> list) {
		this.act = act;
		this.list = list;
	}

	@Override
	public int getCount() {
		if (list == null)
			return 0;
		else
			return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = act.getLayoutInflater();
		View v = inflater.inflate(R.layout.app, null);
		ImageView iv = (ImageView) v.findViewById(R.id.app_image);
		TextView name = (TextView) v.findViewById(R.id.app_name);
		TextView pack = (TextView) v.findViewById(R.id.app_package);

		name.setText(list.get(position).getApp_name());
		pack.setText(list.get(position).getPackage_name());
		iv.setImageDrawable(list.get(position).getImg());

		v.setTag(list.get(position));
		v.setOnClickListener(this);

		return v;
	}

	@Override
	public void onClick(View v) {
		if (!(v.getTag() instanceof App))
			return;
		App app = (App) v.getTag();
		App_Settings app_Settings = new App_Settings();
		app_Settings.setApp(app);
		app_Settings.show(act.getSupportFragmentManager(), "");
	}

}
