package com.quiz.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtility {

	/**
	 * @param activity
	 * @return internet connection status of the device
	 */
	public boolean isInternetConnected(Activity activity) {
		ConnectivityManager cm = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * @param strURL
	 *            Dia chi web dan toi file tren internet
	 * @return Data Stream trang thai ket noi: thanh cong: 200
	 *         (HttpStatus.SC_OK) => !NULL that bai 404
	 *         (HttpStatus.SC_NOT_FOUND) => NULL
	 */
	public static InputStream checkFileAvailableStatus(String strURL) {
		InputStream xmlStream = null;
		URLConnection connection = null;
		try {
			URL url = new URL(strURL);
			connection = url.openConnection();
			if (connection instanceof HttpURLConnection) {
				HttpURLConnection httpConn = (HttpURLConnection) connection;
				httpConn.setDoInput(true);
				httpConn.setRequestProperty("charset", "utf-8");
				httpConn.setConnectTimeout(30 * 1000); // 30s
				int status = httpConn.getResponseCode();
				if (status == HttpStatus.SC_OK) {
					xmlStream = httpConn.getInputStream();
					// close Connection
					// httpConn.disconnect(); // dong nay se gay error
					httpConn = null;
					connection = null;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return xmlStream;
	}
}
