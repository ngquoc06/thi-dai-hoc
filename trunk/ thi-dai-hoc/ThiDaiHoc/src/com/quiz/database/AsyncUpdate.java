package com.quiz.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.quiz.Main;

public class AsyncUpdate extends AsyncTask<Context, Void, Boolean> {
	String out = "update that bai";
	public static final String URL = "https://dl.dropbox.com/u/7059745/questions.xml";

	@Override
	protected Boolean doInBackground(Context... context) {
		Log.i("AsyncUpdate", "do in backgroud");
		if (Main.getCommonDAL().importDataFromURL(URL)) {
			return true;
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {

		if (result) {
			// tao Toast message
			Toast msg = Toast.makeText(Main.getContext(), "Yes",
					Toast.LENGTH_LONG);
			msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2,
					msg.getYOffset() / 2);
			msg.show();
		} else {
			// tao Toast message
			Toast msg = Toast.makeText(Main.getContext(), "No",
					Toast.LENGTH_LONG);
			msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2,
					msg.getYOffset() / 2);
			msg.show();
		}
		Log.i("AsyncUpdate", "xong");
	}

}
