package com.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.quiz.database.SettingDAL;
import com.quiz.entities.Setting;

public class SettingsActivity extends Activity {

	public static final int NOQ_MIN = 10; // min value for numberOfQues
	public static final int SPQ_MIN = 10; // --- secPerQues
	public static final int PR_MIN = 40; // --- passed score required
	public static final int NOQ_MAX = 50; // max value for numberOfQues
	public static final int SPQ_MAX = 120; // max value for secPerQues
	public static final int PR_MAX = 100; // --- passed score required
	public static final int STEP = 5; // step for SeekBar

	private SettingDAL settingDAL;
	private Setting setting;

	private ToggleButton tbtn_timer;
	private Button btn_default;

	private TextView value_numberofquestions;
	private TextView value_secondperquestion;
	private TextView value_passedscore;
	private SeekBar seekbar_numberofquestions;
	private SeekBar seekbar_secperquestion;
	private SeekBar seekbar_passedscore;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_screen);

		tbtn_timer = (ToggleButton) findViewById(R.id.settings_toggle_btn_timer);
		btn_default = (Button) findViewById(R.id.settings_btn_default);

		value_numberofquestions = (TextView) findViewById(R.id.settings_value_numberofquestions);
		value_secondperquestion = (TextView) findViewById(R.id.settings_value_secperquestion);
		value_passedscore = (TextView) findViewById(R.id.settings_value_required_score);
		seekbar_numberofquestions = (SeekBar) findViewById(R.id.settings_seekbar_numberofquestions);
		seekbar_secperquestion = (SeekBar) findViewById(R.id.settings_seekbar_secperquestions);
		seekbar_passedscore = (SeekBar) findViewById(R.id.settings_seekbar_required_score);

		// lay gia tri cac tham so tu SharedPref
		settingDAL = Main.getSettingDAL();
		setting = settingDAL.getSetting();
		this.loadValue(setting);

		/*
		 * sau khi setSelection luu lai Position dang duoc chon de kiem tra
		 * nguoi dung co thay doi ngon ngu hay khong
		 */

		// number of question seeekbar event
		seekbar_numberofquestions
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						setting.setQuestions((seekBar.getProgress() / STEP)
								* STEP + NOQ_MIN);
						settingDAL.setSetting(setting);

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						value_numberofquestions.setText(Integer
								.toString((progress / STEP) * STEP + NOQ_MIN));
					}
				});

		// seconds per question seekbar event
		seekbar_secperquestion
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						setting.setSeconds((seekBar.getProgress() / STEP)
								* STEP + SPQ_MIN);
						settingDAL.setSetting(setting);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						value_secondperquestion.setText(Integer
								.toString((progress / STEP) * STEP + SPQ_MIN));

					}
				});

		// pass required seekbar event
		seekbar_passedscore
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						setting.setPassedScore((seekBar.getProgress() / STEP)
								* STEP + PR_MIN);
						settingDAL.setSetting(setting);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						value_passedscore.setText(Integer
								.toString((progress / STEP) * STEP + PR_MIN)
								+ "%");
					}
				});

		// timer event
		tbtn_timer.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				setting.setTimer(isChecked);
				settingDAL.setSetting(setting);
			}
		});

		// button default settings event

		btn_default.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				SettingsActivity.this.loadValue(settingDAL.reset());

				setting.setQuestions((seekbar_numberofquestions.getProgress() / STEP)
						* STEP + NOQ_MIN);
				setting.setSeconds((seekbar_secperquestion.getProgress() / STEP)
						* STEP + SPQ_MIN);
				setting.setPassedScore((seekbar_passedscore.getProgress() / STEP)
						* STEP + PR_MIN);
				settingDAL.setSetting(setting);

			}
		});

	}

	/*
	 * protected void onDestroy() { super.onDestroy();
	 * 
	 * 
	 * int position = spn_lang.getSelectedItemPosition(); if(position == 0) {
	 * setting.setLanguage("en"); settingDAL.setSetting(setting);
	 * 
	 * } else if(position == 1) { setting.setLanguage("fr");
	 * settingDAL.setSetting(setting);
	 * 
	 * } else if(position == 2){ setting.setLanguage("vi");
	 * settingDAL.setSetting(setting); } myconfig =
	 * getApplication().getResources().getConfiguration(); mylocale =
	 * setting.getLocale(); myconfig.locale = mylocale;
	 * getApplicationContext().getResources().updateConfiguration(myconfig,
	 * getApplicationContext().getResources().getDisplayMetrics());
	 * 
	 * }
	 */
	/**
	 * Load cac tham so setting len layout
	 * 
	 * @param setting
	 */
	private void loadValue(Setting setting) {

		// Number of Questions
		value_numberofquestions.setText("" + setting.getQuestions());
		seekbar_numberofquestions.setProgress(setting.getQuestions() - NOQ_MIN);

		// Seconds for each question
		value_secondperquestion.setText("" + setting.getSeconds());
		seekbar_secperquestion.setProgress(setting.getSeconds() - SPQ_MIN);

		// PassedScore
		value_passedscore.setText(setting.getPassedScore() + "%");
		seekbar_passedscore.setProgress(setting.getPassedScore() - PR_MIN);

		// Timer
		tbtn_timer.setChecked(setting.getTimer());

	}

	public void restartApp() {
		Intent newIntent = new Intent(this.getApplicationContext(),
				HomeTabActivity.class);

		finish();
		startActivity(newIntent);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		this.getParent().onBackPressed();
	}
}