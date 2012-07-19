package com.quiz;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends Activity {

	private static final String TAG = "MediaPlayer";
	MediaPlayer mediaPlayer;// playing sound
	Animation animDropDown;// drop down
	Animation animAboutIn;// fade in

	ImageView imgQuiz;// drop down

	TextView textAbout;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		// retrieve QUIZ image
		imgQuiz = (ImageView) findViewById(R.id.splash_quiz_icon);
		textAbout = (TextView)findViewById(R.id.splash_aout);
		// setting animation
		animDropDown = AnimationUtils.loadAnimation(getBaseContext(),
				R.anim.drop_down);
		animDropDown.setStartOffset(1000);
		animDropDown.setDuration(2000);
		imgQuiz.startAnimation(animDropDown);

		animAboutIn = AnimationUtils.loadAnimation(getBaseContext(),
				R.anim.flipin);
		animAboutIn.setStartOffset(0);
		animAboutIn.setDuration(3000);
		textAbout.startAnimation(animAboutIn);
		// set onAnimationEnd to start MainActivity
		
		animDropDown.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				// retrieve and play sound
				try {
					mediaPlayer = MediaPlayer.create(getApplicationContext(),
							R.raw.intro);
					mediaPlayer.start();
				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, "error: " + e.getMessage(), e);
				}

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				if (mediaPlayer != null) {
					mediaPlayer.release();
					mediaPlayer = null;
				}
				animDropDown.reset();
				animAboutIn.reset();
				animAboutIn = AnimationUtils.loadAnimation(getBaseContext(),R.anim.flipout);
				animDropDown = AnimationUtils.loadAnimation(getBaseContext(),R.anim.flipout);
				
				textAbout.startAnimation(animAboutIn);
				imgQuiz.startAnimation(animDropDown);
				startActivity(new Intent(SplashActivity.this,
						HomeTabActivity.class));
				SplashActivity.this.finish();
			}
		});

	}
}