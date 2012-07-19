package com.quiz;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity {

	Button btnDonate;
	private TextView textcontent;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_screen);

		textcontent = (TextView) findViewById(R.id.about_text_content);
		btnDonate = (Button) findViewById(R.id.donationButton);
		textcontent.setText(getResources().getString(R.string.version) + "\n"
				+ getResources().getString(R.string.description) + "\n"
				+ getResources().getString(R.string.authors) + "\n"
				+ getResources().getString(R.string.org) + "\n"
				+ getResources().getString(R.string.email));
	}

	public void btnDonateOnclick(View v) {
		Intent browserIntent = new Intent(
				Intent.ACTION_VIEW,
				Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=thanhhuy27%40gmail%2ecom&lc=FR&item_name=VSAT%2d%20Vietnamese%20Students%20Android%20Team&item_number=Friday2012&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted"));
		startActivity(browserIntent);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		this.getParent().onBackPressed();
	}

}