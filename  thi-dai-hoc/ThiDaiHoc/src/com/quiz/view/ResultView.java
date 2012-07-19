package com.quiz.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.quiz.QuestionTest;
import com.quiz.R;
import com.quiz.entities.Choice;
import com.quiz.entities.Score;

public class ResultView extends LinearLayout {
	LayoutParams pl;
	LinearLayout linear;

	private static final int FONT_SIZE = 14;
	// private static final String TAG = "ResultView";
	static ViewFlipper flipper;
	boolean isAlreadyReview = false;

	public ResultView(Context context) {
		super(context);

		// khoi tao vao dinh dang Linear chua button Review,Re-test
		linear = new LinearLayout(context);
		linear.setOrientation(HORIZONTAL);
		LayoutParams paramLinear = new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		paramLinear.gravity = Gravity.CENTER_HORIZONTAL;
		linear.setLayoutParams(paramLinear);

		// build layout parameter objects which will set up size of elements
		LayoutParams params1 = new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
		this.setBackgroundColor(Color.BLACK);
		this.setOrientation(LinearLayout.VERTICAL);
		this.setLayoutParams(params1);
		Score score = QuestionTest.score;

		// khai bao cac dieu khien
		TextView topicTitle = new TextView(context);
		TextView txtUnanswered = new TextView(context);
		TextView txtTaken = new TextView(context);
		TextView txtDuration = new TextView(context);
		TextView txtCorrect = new TextView(context);
		TextView txtResult = new TextView(context);
		TextView textUsername = new TextView(context);
		Button btnReview = new Button(context);
		Button btnRetest = new Button(context);

		topicTitle.setTextColor(Color.WHITE);
		topicTitle.setTextSize(FONT_SIZE);
		topicTitle.setText(getContext().getResources().getText(R.string.topic)
				+ " " + score.getTopicTitle().trim());

		textUsername.setText(getContext().getResources().getText(
				R.string.usernameText)
				+ " " + score.getUsername().trim());

		// add noi dung Control
		txtDuration.setText(getContext().getResources().getText(
				R.string.duration)
				+ " " + score.getDurationFormat());
		txtUnanswered.setText(getContext().getResources().getText(
				R.string.unanswer)
				+ " " + score.getUnanswered());
		txtTaken.setText(getContext().getResources().getText(R.string.taken)
				+ " " + score.getTime());

		double total = score.getTotal();
		txtCorrect.setText(getContext().getResources()
				.getText(R.string.correct)
				+ " "
				+ score.getCorrect()
				+ "/"
				+ score.getNoOfQuestions() + "  (" + total + "%)");

		if (total >= score.getPassedScore()) {
			txtResult.setText(getContext().getResources().getText(
					R.string.total)
					+ " " + context.getString(R.string.passed));
		} else {
			txtResult.setText(getContext().getResources().getText(
					R.string.total)
					+ " " + context.getString(R.string.failed));
		}
		flipper = QuestionTest.flipper;

		LayoutParams paramReview = new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		paramReview.gravity = Gravity.CENTER_HORIZONTAL;
		btnReview.setLayoutParams(paramReview);
		btnReview.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.result_review_soloution));
		btnReview.setTextColor(Color.WHITE);
		btnReview.setPadding(20, 0, 20, 0);
		btnReview.setTextSize(14);
		btnReview.setText(getContext().getResources().getText(R.string.review));
		btnReview.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				review();
			}
		});
		btnRetest.setLayoutParams(paramReview);
		btnRetest.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.result_review_soloution));
		btnRetest.setText(getContext().getResources().getText(R.string.retest));
		btnRetest.setTextColor(Color.WHITE);
		btnRetest.setTextSize(14);
		btnRetest.setPadding(20, 0, 20, 0);
		btnRetest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				retest();
			}
		});
		// add cac Coltrol vao View
		linear.addView(btnReview);
		linear.addView(btnRetest);
		this.addView(topicTitle);
		this.addView(textUsername);
		this.addView(txtTaken);
		this.addView(txtDuration);
		this.addView(txtUnanswered);
		this.addView(txtCorrect);
		this.addView(txtResult);
		this.addView(linear);
	}

	/*
	 * Hien thi dap an
	 */
	private void review() {

		// neu da Review het 1 lan thi tu lan 2 tro di ko can hieu chinh flipper
		// nua
		// hien thi barBottom, button Play, Pause
		QuestionTest.timerUp.setText("");
		QuestionTest.relative.setVisibility(View.VISIBLE);
		QuestionTest.btnPause.setVisibility(View.GONE);
		QuestionTest.btnPlay.setVisibility(View.VISIBLE);
		QuestionTest.btnFinish.setVisibility(View.VISIBLE);
		if (isAlreadyReview) {
			flipper.setDisplayedChild(0);
			return;
		}
		// dem so luong Child cua Flipper
		int numberOfViewsInFlipper = flipper.getChildCount();
		for (int i = 0; i < numberOfViewsInFlipper - 1; i++) {
			// Child cua flipper la mot ScrollView chua QuestionView
			ScrollView parentOfQuestionView = (ScrollView) flipper
					.getChildAt(i);
			// lay QuestionView tu parentOfQuestionView
			QuestionView view = (QuestionView) parentOfQuestionView
					.getChildAt(0);
			// lay CheckBox, Radio cho cac Choices
			for (int j = 0; j < view.getChildCount() - 1; j++) {
				// xu ly theo Radio
				RadioGroup radioGroup = (RadioGroup) view.findViewById(2012);
				if (radioGroup != null) {
					int numberOfRadio = radioGroup.getChildCount();
					// lay tung Radio trong RadioGroup va kiem tra, doi chieu
					// gia tri
					for (int k = 0; k < numberOfRadio; k++) {
						RadioButton radio = (RadioButton) radioGroup
								.getChildAt(k);
						Choice choice = (Choice) radio.getTag();
						if (choice.getIsCorrect() == 1) {
							radio.setTextColor(Color.GREEN);
						}
					}
				} else {
					// xu ly theo CheckBox
					View childView = view.getChildAt(j + 1);
					if (childView instanceof CheckBox) {
						CheckBox checkbox = (CheckBox) view.getChildAt(j + 1);
						Choice choice = (Choice) checkbox.getTag();
						if (choice.getIsCorrect() == 1) {
							checkbox.setTextColor(Color.GREEN);
						}
					}
				}
			}
		}
		// refresh flipper
		flipper.refreshDrawableState();
		flipper.showNext();
		isAlreadyReview = true;

		// free up memory

	}

	/*
	 * Thuc hien tinh nang test lai de thi vua roi
	 */
	public void retest() {
		QuestionTest.isAlreadyClickFinish = false;
		// khoi dong lai timer
		if (QuestionTest.timer != null) {
			QuestionTest.timer.start();
		} else {
			QuestionTest.timerUp.setText(null);
		}

		// tat timer auto Next Question
		if (QuestionTest.flagOnOff_PausePlay)
			QuestionTest.timerReview.cancel();
		// hien thanh next, pre, va an nut play-pause
		QuestionTest.relative.setVisibility(View.VISIBLE);
		QuestionTest.btnPlay.setVisibility(View.GONE);
		QuestionTest.btnPause.setVisibility(View.GONE);
		QuestionTest.btnFinish.setVisibility(View.VISIBLE);

		// -1 , tru child last hien thi ket qua
		int numberOfViewsInFlipper = flipper.getChildCount();
		// remove Child last hien thi ket qua cua flipper
		flipper.removeViewAt(numberOfViewsInFlipper - 1);

		for (int i = 0; i < numberOfViewsInFlipper - 1; i++) {
			// Child cua flipper la mot ScrollView chua QuestionView
			ScrollView parentOfQuestionView = (ScrollView) flipper
					.getChildAt(i);
			// lay QuestionView tu parentOfQuestionView
			QuestionView view = (QuestionView) parentOfQuestionView
					.getChildAt(0);
			int childCount = view.getChildCount();
			// lay CheckBox, Radio cho cac Choices
			if (view.getQuestion().getIsMulti() == 1) {
				for (int j = 1; j < childCount; j++) {
					// xu ly theo CheckBox
					if (view.getChildAt(j) instanceof CheckBox) {
						CheckBox checkbox = (CheckBox) view.findViewById(j);
						checkbox.setChecked(false);
						checkbox.setTextColor(Color.WHITE);
					}
				}
			} else {
				// xu ly theo Radio
				RadioGroup radioGroup = (RadioGroup) view.findViewById(2012);
				radioGroup.clearCheck();
				int numberOfRadio = radioGroup.getChildCount();
				// lay tung Radio trong RadioGroup va reset lai cac trang
				// thai
				for (int k = 0; k < numberOfRadio; k++) {
					RadioButton radio = (RadioButton) radioGroup.getChildAt(k);
					radio.setTextColor(Color.WHITE);
				}

			}// for else
		}
		flipper.refreshDrawableState();
		flipper.showNext();
	}

}
