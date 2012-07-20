package com.thidaihoc;

import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thidaihoc.entities.Question;
import com.thidaihoc.entities.Score;
import com.thidaihoc.entities.Setting;
import com.thidaihoc.entities.Topic;
import com.thidaihoc.utils.ConverterHelper;

public class TopicCoverActivity extends Activity {
	//private ProgressDialog _progressDialog;
	private TextView txtTopic;
	private TextView txtNumberOfQues;
	private TextView txtDuration;
	private TextView txtTimer;
	private TextView txtPassRequied;
	private RelativeLayout layoutParent;
	private ImageButton ibtnHome;
	Setting setting;
	public static Topic topic;
	public static int duration;
	public static int actualNumberOfQuestions;
	public static EditText textUserName;

	// danh sach se duoc truy xuat o cac Activity sau nay
	public static Collection<Question> questionList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_cover_screen);

		Log.i("TopicCoverActivity", "onCreate");
		// lay thong tin Topic
		Bundle intent = getIntent().getExtras();
		int topicId = intent.getInt("topic_id");
		int examId = intent.getInt("examId");
		topic = Main.getTopicDAL().getTopicByID(topicId);
		// lay doi tuong Setting
		setting = Main.getSettingDAL().getSetting();

		if (examId == 0) {
			// tao bo de thi MOI (new) duoc lay ngau nhien theo Cau hinh so
			// luong cau hoi
			// questionList = Main.getQuestionDAL().getQuestions();
			questionList = Main.getQuestionDAL().getQuestionsByTopic(topicId,
					setting.getQuestions());
		} else {
			// lay bo de thi tu history
			questionList = Main.getExamHistoryDAL().getExamByID(examId)
					.getQuestionList();
		}

		actualNumberOfQuestions = questionList.size();
		duration = actualNumberOfQuestions * setting.getSeconds(); 
		// retrieve UI components 
		txtTopic = (TextView) findViewById(R.id.topic_cover_text_topic);
		txtNumberOfQues = (TextView) findViewById(R.id.topic_cover_text_numofques);
		txtPassRequied = (TextView) findViewById(R.id.topic_cover_text_score_required);
		txtDuration = (TextView) findViewById(R.id.topic_cover_text_duration);
		txtTimer = (TextView) findViewById(R.id.topic_cover_text_timer);
		ibtnHome = (ImageButton) findViewById(R.id.topic_cover_ibtn_home);
		textUserName = (EditText) findViewById(R.id.topic_cover_value_username);
		layoutParent = (RelativeLayout) findViewById(R.id.topic_relative);
		textUserName.setBackgroundColor(Color.BLACK);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		// set values
		txtTopic.setText(getResources().getText(R.string.topic) + " "
				+ topic.getTitle().trim());
		txtNumberOfQues.setText(getResources().getText( 
				R.string.number_of_questions)
				+ " " + actualNumberOfQuestions);
		txtPassRequied.setText(getResources().getText(R.string.pass_score)
				+ " " + setting.getPassedScore() + "%");
		txtDuration.setText(getResources().getText(R.string.duration) + " "
				+ ConverterHelper.getFormatResultDuration(duration));

		if (setting.getTimer()) {
			txtTimer.setText(getResources().getText(R.string.timer) + " "
					+ getApplicationContext().getString(R.string.on));
		} else {
			txtTimer.setText(getResources().getText(R.string.timer) + " "
					+ getApplicationContext().getString(R.string.off));
		}

		// Xu ly lay Score Last

		List<Score> scoreLast = Main.getScoreDAL().getScoreHistory();
		if (scoreLast.size() > 0) {
			//Log.i("TopicCover", "sizeScore = " + scoreLast.size());
			textUserName.setText("" + scoreLast.get(0).getUsername());
		}
		
	
		// imagebutton back event
		
		
		
		ibtnHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TopicCoverActivity.this.finish();
			}
		});
		textUserName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textUserName.setCursorVisible(true);
				textUserName.setBackgroundResource(R.color.gray_black);
				textUserName.setTextColor(Color.WHITE);
				
			}
		});
		layoutParent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textUserName.setCursorVisible(false);
				textUserName.setBackgroundColor(Color.BLACK);
				textUserName.setTextColor(Color.WHITE);
				offKeyBoard();
			}
		});
	}

	public void offKeyBoard() {
		layoutParent.requestFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(layoutParent.getWindowToken(), 0);
	}
	// cac phuong thuc onClick
	public void onClickBeginTest(View view) {
//		_progressDialog = new ProgressDialog(TopicCoverActivity.this);
//		_progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		_progressDialog.setMessage("Đang nạp câu hỏi...");
//		//_progressDialog.setCancelable(false);
//		_progressDialog.show();
		Log.i("TopicCoverActivity - onClickBeginTest()", "End");
		Intent itent = new Intent(TopicCoverActivity.this,
				QuestionTest.class);
		startActivity(itent);
		TopicCoverActivity.this.finish();
	}

	@Override
	public void onBackPressed() {
		TopicCoverActivity.this.finish();
	}
}
