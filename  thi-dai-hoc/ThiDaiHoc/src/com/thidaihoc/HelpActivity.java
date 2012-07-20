package com.thidaihoc;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_screen);
		TextView textContent = (TextView) findViewById(R.id.help_content);
		Resources res = getResources();
		textContent.setText(res.getString(R.string.help_content_muc_dich)
				+res.getString(R.string.help_content_chuc_nang)
				+ res.getString(R.string.help_content_bo_de)
				+ res.getString(R.string.help_content_test)
				+ res.getString(R.string.help_content_cap_nhat_bo_de)
				+ res.getString(R.string.help_content_cau_hinh));

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		this.getParent().onBackPressed();
	}

}