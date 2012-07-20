package com.thidaihoc;

import java.util.Collection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.thidaihoc.entities.Choice;
import com.thidaihoc.entities.Exam;
import com.thidaihoc.entities.Question;
import com.thidaihoc.entities.Score;
import com.thidaihoc.entities.Setting;
import com.thidaihoc.utils.ConverterHelper;
import com.thidaihoc.view.AnimationHelper;
import com.thidaihoc.view.QuestionView;
import com.thidaihoc.view.ResultView;

;

public class QuestionTest extends Activity {

	Context context;
	Vibrator vibratio;
	AlertDialog.Builder configexit;
	public static TextView timerUp;
	public static ViewFlipper flipper;
	public static RelativeLayout relative;
	public static Button btnPlay, btnPause, btnNext, btnPrev, btnFinish,
			btnBack;
	
	private static final String TAG = "QuestionTest";
	private static final int TIMER_REVIEW_INTERVAL = 5000; // = 5 seconds
	public static Score score = null; // luu ket qua test

	Setting setting;

	int duration; // tong so thoi gian test
	int noOfQuestion;
	int secondsPerQuestion; // so giay cho moi cau
	int secondReview = 0;
	long timer_rest; //
	public Boolean on_off_timer; // on/off timer countdown
	public static Boolean flagOnOff_PausePlay = false; // on/off che do tu next
														// question
	Boolean delayShowFirst = false;
	public static boolean isAlreadyClickFinish = false;
	public static MyCounter timer;
	public static TimerReview timerReview;

	/*
	 * ==============================================ON
	 * Create===============================================
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_questions);
		Log.i("QuestionTest - onCreate","accessed");
		// lay cac thong so setting
		context = this.getApplicationContext();
		setting = Main.getSettingDAL().getSetting();
		relative = (RelativeLayout) findViewById(R.id.RelativeLayoutBottom);
		flipper = (ViewFlipper) findViewById(R.id.question_flipper);
		btnNext = (Button) findViewById(R.id.btnQuestion_Next);
		btnPrev = (Button) findViewById(R.id.btnQuestion_Previous);
		btnPlay = (Button) findViewById(R.id.btnQuestion_Play);
		btnPause = (Button) findViewById(R.id.btnQuestion_Pause);
		btnFinish = (Button) findViewById(R.id.question_btnFinish);
		btnBack = (Button) findViewById(R.id.question_button_Back);
		timerUp = (TextView) findViewById(R.id.question_textView_countDown);
		vibratio = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		duration = TopicCoverActivity.duration;
		// duration = 15;
		timer_rest = (duration - (duration * 90) / 100) * 1000;
		secondsPerQuestion = setting.getSeconds();
		on_off_timer = setting.getTimer();
		noOfQuestion = TopicCoverActivity.actualNumberOfQuestions;
		// start countdowntimer
		if (on_off_timer) {
			timer = new MyCounter(duration * 1000, 1000);
			timer.start();
		}
		// lay question list tu TopicCoverActivity va add vao Flipper
		
		addViewToFlipper(TopicCoverActivity.questionList);

		/*
		 * Kiem tra, neu Function finish dc goi, tuc la isAlreadyClickFinish =
		 * true thi hien thi Button Play,Pause If isAlreadyClickFinish = false,
		 * invisible Button Play, Pause
		 */
		btnPlay.setVisibility(View.GONE);
		btnPause.setVisibility(View.GONE);

		btnNext.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// stop timerReview neu da nhan Finish. Tuc la dang o che do
				// Review
				if (isAlreadyClickFinish) {
					btnPlay.setVisibility(View.VISIBLE);
					btnPause.setVisibility(View.GONE);
					if (flagOnOff_PausePlay) {
						timerReview.cancel();
						flagOnOff_PausePlay = false;
					}
					delayShowFirst = false;
				}
				flipperNext();

			}
		});
		btnPrev.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// stop timerReview neu da nhan Finish. Tuc la dang o che do
				// Review
				if (isAlreadyClickFinish) {
					btnPlay.setVisibility(View.VISIBLE);
					btnPause.setVisibility(View.GONE);
					if (flagOnOff_PausePlay) {
						timerReview.cancel();
						flagOnOff_PausePlay = false;
					}
					delayShowFirst = false;
				}
				flipperPrevious();
			}
		});
		btnPlay.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				btnPlay.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
				flagOnOff_PausePlay = true;
				delayShowFirst = false;
				timerReview = new TimerReview(noOfQuestion
						* TIMER_REVIEW_INTERVAL, TIMER_REVIEW_INTERVAL);
				timerReview.start();
			}
		});

		btnPause.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				btnPlay.setVisibility(View.VISIBLE);
				btnPause.setVisibility(View.GONE);
				flagOnOff_PausePlay = false;
				timerReview.cancel();
			}
		});
		btnBack.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				configExit();
			}
		});
	}

	/*
	 * xac nhan truoc khi nhan Nut Exit, hoac nut back
	 */
	public void configExit() {
		configexit = new AlertDialog.Builder(this);
		configexit
				.setMessage(
						getResources().getString(R.string.question_sureexit))
				.setPositiveButton(getResources().getString(R.string.yes),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// free up resources
								TopicCoverActivity.questionList.clear();
								flipper.removeAllViews();
								flipper.destroyDrawingCache();
								flipper = null;
								if (on_off_timer) {
									timer.cancel();
								}
								if (flagOnOff_PausePlay) {
									timerReview.cancel();
									flagOnOff_PausePlay = false;
								}
								isAlreadyClickFinish = false;
								Intent itent = new Intent(QuestionTest.this,
										HomeTabActivity.class);
								startActivity(itent);
								// close this activity
								QuestionTest.this.finish();
							}
						})
				.setNegativeButton(getResources().getString(R.string.no),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// xu li neu nguoi dung chon NO
								dialog.cancel();
							}
						}).show();

	}

	/*
	 * onTount
	 */
	private void flipperNext() {
		flipper.setInAnimation(AnimationHelper.inFromRightAnimation());
		flipper.setOutAnimation(AnimationHelper.outToLeftAnimation());
		flipper.showNext();
	}

	private void flipperPrevious() {
		flipper.setInAnimation(AnimationHelper.inFromLeftAnimation());
		flipper.setOutAnimation(AnimationHelper.outToRightAnimation());
		flipper.showPrevious();
	}

	SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener() {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			float sensitvity = 50;
			if ((e1.getX() - e2.getX()) > sensitvity) {
				flipperNext();

			} else if ((e2.getX() - e1.getX()) > sensitvity) {
				flipperPrevious();
			}
			return true;
		}

	};
	GestureDetector gestureDetector = new GestureDetector(
			simpleOnGestureListener);

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if ((isAlreadyClickFinish == false)
				|| (flipper.getDisplayedChild() != (flipper.getChildCount() - 1)))
			return gestureDetector.onTouchEvent(event);
		else
			return false;
	}

	/**
	 * Su kien click cho Button Finish
	 * 
	 * @param view
	 */
	public void onClickFinish(View view) {
		btnFinish.setVisibility(View.INVISIBLE);
		// neu da click finish 1 lan thi cac lan sau ko cac tinh toan nua.
		if (isAlreadyClickFinish) {
			flipper.setDisplayedChild(flipper.getChildCount() - 1);
			return;
		} else {
			completeTest();
		}

	}

	/*
	 * ------------------------------------------Function------------------------
	 * --------------------------
	 */
	public void addViewToFlipper(Collection<Question> questionList) {
		Log.i("QuestionTest1", "addViewToFlipper()");
		flipper.removeAllViews();
		int index = 1;
		for (Question question : questionList) {
			ScrollView scrollview = new ScrollView(this.getApplicationContext());
			scrollview.addView(new QuestionView(this.getApplicationContext(),
					question, index++, noOfQuestion, false));
			flipper.addView(scrollview);
			scrollview = null;
		}
	}

	/**
	 * Tra ve ket qua sau khi thi xong
	 * 
	 * @return thong tin Score (ket qua thi)
	 */
	public Score getScore() {
		ScrollView parentOfQuestionView = null;
		QuestionView questionView = null;
		Score score = new Score();
		int correct = 0;
		// dem so luong Child cua Flipper
		int numberOfViewsInFlipper = flipper.getChildCount();
		// khoi tao so cau khong tra
		int unanswered = numberOfViewsInFlipper;
		/*
		 * flag - kiem tra question da duoc chon
		 */

		// lap qua tung View (Question) (vd: co 20 questions = 20 views)
		for (int i = 0; i < numberOfViewsInFlipper; i++) {
			boolean flag = false;
			// Child cua flipper la mot ScrollView chua QuestionView
			parentOfQuestionView = (ScrollView) flipper.getChildAt(i);
			// lay QuestionView tu parentOfQuestionView
			questionView = (QuestionView) parentOfQuestionView.getChildAt(0);

			// lay CheckBox, Radio cho cac Choices
			if (questionView.getQuestion().getIsMulti() == 1) {
				// xu ly theo CheckBox
				boolean isCorrect = true;
				int childCount = questionView.getChildCount();
				for (int j = 1; j < childCount; j++) {
					if (questionView.getChildAt(j) instanceof CheckBox) {
						CheckBox checkbox = (CheckBox) questionView
								.findViewById(j);
						Choice choice = (Choice) checkbox.getTag();
						int isChecked = (checkbox.isChecked()) ? 1 : 0;

						if ((choice.getIsCorrect() == 1 && isChecked == 0)
								|| (choice.getIsCorrect() == 0 && isChecked == 1))
							isCorrect = false;
						if (isChecked == 1)
							flag = true;

					}
				}
				if (isCorrect)
					correct++;
			} else {
				// xu ly theo Radio
				RadioGroup radioGroup = (RadioGroup) questionView
						.findViewById(2012);
				int numberOfRadio = radioGroup.getChildCount();
				// lay tung Radio trong RadioGroup va kiem tra, doi chieu
				// gia tri
				for (int k = 0; k < numberOfRadio; k++) {
					RadioButton radio = (RadioButton) radioGroup.getChildAt(k);
					Choice choice = (Choice) radio.getTag();
					int isChecked = (radio.isChecked()) ? 1 : 0;

					// dem so luong cau tra loi dung
					if (choice.getIsCorrect() == 1 && isChecked == 1) {
						correct++;
					}
					if (isChecked == 1) {
						flag = true;

						// flagChecked[i][0] = k;
					}
				}
				// tang so
			}
			if (flag)
				unanswered--;
		}
		// Gan gia tri cho Score
		score.setTopic(TopicCoverActivity.topic.getId()); // topic question
		score.setTopicTitle(TopicCoverActivity.topic.getTitle());

		if (on_off_timer)
			score.setDuration(duration);
		else
			score.setDuration(0);

		score.setCorrect(correct); // so cau dung
		score.setUnanswered(unanswered); // so cau chua lam
		score.setNoOfQuestions(TopicCoverActivity.actualNumberOfQuestions);
		score.setPassedScore(setting.getPassedScore());
		score.setTime(ConverterHelper.getCurrentDateTime());
		score.setUsername(TopicCoverActivity.textUserName.getText().toString());
		// GC
		parentOfQuestionView = null;
		questionView = null;

		return score;
	}

	public void completeTest() {
		// thay doi giao dien,AN-HIEN cac thanh phan lien quan
		timerUp.setTextColor(Color.WHITE);
		timerUp.setText(R.string.result);
		relative.setVisibility(View.INVISIBLE);

		if (on_off_timer) {
			timer.cancel();
		}
		// Luu thong tin vua thi xong (Score ben SharedPref & History ben DB
		score = getScore();

		// add ResultView
		flipper.addView(new ResultView(this.getApplicationContext()));
		flipper.setDisplayedChild(flipper.getChildCount() - 1);

		// exam id = id_max + 1
		int examId = Main.getExamHistoryDAL().getMaxExamId() + 1;
		score.setExamId(examId);
		// save score vao SharePre
		Main.getScoreDAL().store(score);

		// luu History
		Exam history = new Exam();
		history.setId(examId);
		history.setTopic(TopicCoverActivity.topic.getTitle());
		history.setTakenDate(score.getTime());
		history.setQuestionList(TopicCoverActivity.questionList);
		history.setScoreId(score.getId());

		Log.i(TAG, "question list size = " + history.getQuestionList().size());

		// luu tru bo de thi vao History
		Main.getExamHistoryDAL().create(history);

		isAlreadyClickFinish = true;
		history = null;
	}

	/*
	 * ==================== CountdownTimer che do On Timer
	 * ==================================================
	 */
	Boolean showMessengerToast = true;

	public class MyCounter extends CountDownTimer {
		public MyCounter(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			timerUp.setText(R.string.finish_time_up);
			vibratio.vibrate(1000);
			completeTest();
			vibratio.cancel();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			timerUp.setText(ConverterHelper
					.getFormatCountDownTimerDuration(millisUntilFinished / 1000));
			if (millisUntilFinished <= timer_rest) {
				if (showMessengerToast) {
					Toast msg = Toast.makeText(context,
							getResources().getText(R.string.msg_timer_up),
							Toast.LENGTH_LONG);
					msg.getGravity();
					msg.setGravity(Gravity.BOTTOM, msg.getXOffset(),
							msg.getYOffset());
					msg.show();
					showMessengerToast = false;
				}
				timerUp.setTextColor(Color.YELLOW);
				if ((millisUntilFinished / 1000) % 2 == 0) {
					vibratio.vibrate(300);
					timerUp.setTextSize(20);
				} else
					timerUp.setTextSize(16);
			}
		}
	}

	// Timer che do Auto next Review question
	public class TimerReview extends CountDownTimer {
		public TimerReview(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			if (flipper.getDisplayedChild() == (flipper.getChildCount() - 1)) {
				this.cancel();
			} else {
				if (flagOnOff_PausePlay && delayShowFirst) {
					flipperNext();
				}
			}
			/*
			 * Khi nguoi dung dang o che do Review, nhan' button Play thi se
			 * show question ngay lap tuc. Bien nay co tac dung ko cho show
			 * question ngay khi nhan Play
			 */
			delayShowFirst = true;
		}

		@Override
		public void onFinish() {

		}
	}

	@Override
	public void onBackPressed() {
		// show hop thoai
		configExit();
	}
	
	
}
