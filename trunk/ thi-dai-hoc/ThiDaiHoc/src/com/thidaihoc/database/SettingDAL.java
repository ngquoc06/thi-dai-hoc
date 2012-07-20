package com.thidaihoc.database;

import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;

import com.thidaihoc.Main;
import com.thidaihoc.entities.Setting;

public class SettingDAL {
	Context context;
	public static final int MODE = Context.MODE_PRIVATE;
	public static final String SETTING_QUESTIONS = "questions";
	public static final String SETTING_SECONDS = "seconds";
	public static final String SETTING_TIMER = "timer";
	public static final String SETTING_PASSED_SCORE = "passed_score";
	public static final String SETTING_HISTORY_SIZE = "history_size";
	public static final String SETTING_LANGUAGE = "language";
	public static final String PREF_NAME = "SettingManager";

	public static final String TAG = "SettingManager";

	public SettingDAL(Context context) {
		this.context = context;
	}

	public Editor getEditor(String prefName) {
		return this.context.getSharedPreferences(prefName, MODE).edit();
	}

	/**
	 * Luu tru cac thong so cau hinh
	 * 
	 * @param setting
	 * @return true neu nhu save thanh cong
	 */
	public boolean setSetting(Setting setting) {
		Editor editor = this.getEditor(PREF_NAME);
		editor.putInt(SETTING_QUESTIONS, setting.getQuestions());
		editor.putInt(SETTING_SECONDS, setting.getSeconds());
		editor.putInt(SETTING_PASSED_SCORE, setting.getPassedScore());
		editor.putBoolean(SETTING_TIMER, setting.getTimer());
		editor.putInt(SETTING_HISTORY_SIZE, setting.getHistorySize());
		editor.putString(SETTING_LANGUAGE, setting.getLanguage());
		return editor.commit();
	}

	public Setting getSetting() {
		Setting setting = new Setting();

		SharedPreferences pref = this.context.getSharedPreferences(PREF_NAME,
				MODE);
		if (pref.contains(SETTING_QUESTIONS)) {
			setting.setQuestions(pref.getInt(SETTING_QUESTIONS, 20));
			setting.setSeconds(pref.getInt(SETTING_SECONDS, 20));
			setting.setPassedScore(pref.getInt(SETTING_PASSED_SCORE, 40));
			setting.setTimer(pref.getBoolean(SETTING_TIMER, true));
			setting.setHistorySize(pref.getInt(SETTING_HISTORY_SIZE, 5));
			setting.setLanguage(pref.getString(SETTING_LANGUAGE, "en"));
		}

		return setting;
	}

	/**
	 * @return Default setting after reset
	 */
	public Setting reset() {
		Setting setting = new Setting();
		Editor editor = this.getEditor(PREF_NAME);
		editor.putInt(SETTING_QUESTIONS, setting.getQuestions());
		editor.putInt(SETTING_SECONDS, setting.getSeconds());
		editor.putInt(SETTING_PASSED_SCORE, setting.getPassedScore());
		editor.putBoolean(SETTING_TIMER, setting.getTimer());
		editor.putInt(SETTING_HISTORY_SIZE, setting.getHistorySize());
		editor.putString(SETTING_LANGUAGE, setting.getLanguage());
		editor.commit();
		return setting;
	}

	/**
	 * Remove all values of Pref
	 */
	public void clear() {
		this.context.getSharedPreferences(PREF_NAME, MODE).edit().clear()
				.commit();
	}

	/**
	 * Cap nhat cau hinh ngon ngu
	 * 
	 * @param context
	 * @param setting
	 */
	public void updateLanguage(Context ctx) {
		Configuration config = new Configuration();

		Setting setting = Main.getSettingDAL().getSetting();
		Locale myLocale = setting.getLocale();

		String lang = myLocale.getLanguage();
		if (lang != null) {
			config.locale = myLocale;
		} else {
			config.locale = Locale.getDefault();
		}

		ctx.getResources().updateConfiguration(config, null);
	}

}
