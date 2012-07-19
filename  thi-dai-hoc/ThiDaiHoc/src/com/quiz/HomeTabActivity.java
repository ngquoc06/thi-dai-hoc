package com.quiz;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.quiz.view.CustomAdapter;

public class HomeTabActivity extends TabActivity {
	public static ViewFlipper flipperYourHome;
	TextView indexFlipper;
	TabHost tabHost;
	private Context context;
	private CustomAdapter customAdapter;
	private final ArrayList<String> Items = new ArrayList<String>();
	GridView gridView;
	private final int NUMBER_TOPIC = 4;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_tab);

		context = this;
		for (int i = 0; i < NUMBER_TOPIC; i++) {
			Items.add(Integer.toString(i));
		}
		gridView = (GridView) findViewById(R.id.home_tab_gridview);
		customAdapter = new CustomAdapter(context, Items);
		gridView.setAdapter(customAdapter);

		Resources res = this.getResources();
		tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("home")
				.setIndicator(this.getText(R.string.home),
						res.getDrawable(R.drawable.home))
				.setContent(R.id.hometab_relative_Flipper);
		tabHost.addTab(spec);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("history")
				.setIndicator(this.getText(R.string.history),
						res.getDrawable(R.drawable.history))
				.setContent(
						new Intent().setClass(context, HistoryActivity.class));
		tabHost.addTab(spec);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("setting")
				.setIndicator(this.getText(R.string.settings),
						res.getDrawable(R.drawable.setting))
				.setContent(
						new Intent().setClass(context, SettingsActivity.class));
		tabHost.addTab(spec);

		// Points tabs
		spec = tabHost
				.newTabSpec("help")
				.setIndicator(this.getText(R.string.help),
						res.getDrawable(R.drawable.help))
				.setContent(new Intent().setClass(this, HelpActivity.class));
		tabHost.addTab(spec);

		// Social tabs
		spec = tabHost
				.newTabSpec("about")
				.setIndicator(this.getText(R.string.about),
						res.getDrawable(R.drawable.info))
				.setContent(new Intent().setClass(context, AboutActivity.class));
		tabHost.addTab(spec);

		// Contact tabs
		tabHost.setCurrentTab(0);
	}

	/*
	 * Tam thoi close phan paging
	 * 
	 * public void showIndex(int page) { if (page == 0)
	 * indexFlipper.setBackgroundDrawable(getResources().getDrawable(
	 * R.drawable.home_pager1)); else if (page == 1)
	 * indexFlipper.setBackgroundDrawable(getResources().getDrawable(
	 * R.drawable.home_pager2)); else
	 * indexFlipper.setBackgroundDrawable(getResources().getDrawable(
	 * R.drawable.home_pager3)); }
	 * 
	 * @Override public boolean onTouchEvent(MotionEvent event) { // TODO
	 * Auto-generated method stub return gestureDetector.onTouchEvent(event); }
	 * 
	 * SimpleOnGestureListener simpleOnGestureListener = new
	 * SimpleOnGestureListener() {
	 * 
	 * @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float
	 * velocityX, float velocityY) { float sensitvity = 50; if ((e1.getX() -
	 * e2.getX()) > sensitvity) { flipperYourHome.setInAnimation(AnimationHelper
	 * .inFromRightAnimation()); flipperYourHome.setOutAnimation(AnimationHelper
	 * .outToLeftAnimation()); flipperYourHome.showNext(); //Log.i("showNext",
	 * "duoc goi"); showIndex(flipperYourHome.getDisplayedChild()); } else if
	 * ((e2.getX() - e1.getX()) > sensitvity) {
	 * flipperYourHome.setInAnimation(AnimationHelper .inFromLeftAnimation());
	 * flipperYourHome.setOutAnimation(AnimationHelper .outToRightAnimation());
	 * flipperYourHome.showPrevious(); //Log.i("showPrevious", "duoc goi");
	 * showIndex(flipperYourHome.getDisplayedChild()); } return true; } };
	 * GestureDetector gestureDetector = new GestureDetector(
	 * simpleOnGestureListener);
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		AlertDialog.Builder ab = new AlertDialog.Builder(context);
		ab.setMessage(getResources().getString(R.string.quit_confirm));
		ab.setPositiveButton(getResources().getString(R.string.yes),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		ab.setNegativeButton(getResources().getString(R.string.no),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
		ab.show();
	}

}
