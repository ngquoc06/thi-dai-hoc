package com.quiz;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.LayoutParams;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quiz.entities.Score;
import com.quiz.utils.MyExpandableListAdapter;
import com.quiz.utils.MyListItem;

public class HistoryActivity extends Activity {

	private Button btnClearHistory;
	private Button[] btnRetest;
	private List<Score> list;
	private ExpandableListView myExList;
	private MyExpandableListAdapter myAdapter;
	private MyListItem[] groups;
	private MyListItem[][] children;
	int examId = 0;
	int topic_id = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Context context = this.getApplicationContext();
		setContentView(R.layout.history_screen);
		btnClearHistory = (Button) findViewById(R.id.history_clear_history);

		myExList = (ExpandableListView) findViewById(R.id.history_expandablelist);

		if (Main.getScoreDAL().getCurrentSizeOfHistory() == 0) {
			// hide button Clear
			btnClearHistory.setVisibility(View.INVISIBLE);

			// hien thi thong bao
			Toast msg = Toast.makeText(context, R.string.msg_empty_history,
					Toast.LENGTH_SHORT);
			msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2,
					msg.getYOffset() / 2);
			msg.show();

			return;
		}

		// lay danh sach Score
		list = Main.getScoreDAL().getScoreHistory();

		groups = new MyListItem[list.size()];
		children = new MyListItem[list.size()][5];
		btnRetest = new Button[5];
		int i = 0;

		LayoutParams lp = new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		for (Score score : list) {
			TextView txtUsername = new TextView(context);
			txtUsername.setText(""
					+ getResources().getString(R.string.username)
					+ score.getUsername() + "\n" + "(" + score.getTime() + ")");

			// if insert a button in group, must setFocusable(false)
			LinearLayout viewUser = new LinearLayout(context);
			viewUser.setLayoutParams(lp);
			viewUser.setPadding(60, 0, 0, 0);
			viewUser.addView(txtUsername);
			groups[i] = new MyListItem(context, viewUser);

			TextView txtTopic = new TextView(context);
			txtTopic.setText("" + getResources().getString(R.string.topic)
					+ score.getTopicTitle());
			LinearLayout viewTopic = new LinearLayout(context);
			viewTopic.setLayoutParams(lp);
			viewTopic.setPadding(10, 0, 0, 0);
			viewTopic.addView(txtTopic);
			children[i][0] = new MyListItem(context, viewTopic);

			TextView txtResult = new TextView(context);
			txtResult.setText(getResources().getString(R.string.result)
					+ score.getCorrect() + "/" + score.getNoOfQuestions());
			LinearLayout viewResult = new LinearLayout(context);
			viewResult.setLayoutParams(lp);
			viewResult.setPadding(10, 0, 0, 0);
			viewResult.addView(txtResult);
			children[i][1] = new MyListItem(context, viewResult);

			TextView txtDuration = new TextView(context);
			txtDuration.setText(getResources().getString(R.string.duration)
					+ score.getDurationFormat());
			LinearLayout viewDuration = new LinearLayout(context);
			viewDuration.setLayoutParams(lp);
			viewDuration.setPadding(10, 0, 0, 0);
			viewDuration.addView(txtDuration);
			children[i][2] = new MyListItem(context, viewDuration);

			TextView txtUnanswer = new TextView(context);
			txtUnanswer.setText(getResources().getString(R.string.unanswer)
					+ score.getUnanswered());
			LinearLayout viewUnanswer = new LinearLayout(context);
			viewUnanswer.setLayoutParams(lp);
			viewUnanswer.setPadding(10, 0, 0, 0);
			viewUnanswer.addView(txtUnanswer);
			children[i][3] = new MyListItem(context, viewUnanswer);

			btnRetest[i] = new Button(context);
			btnRetest[i].setText(getResources().getString(R.string.retest));
			btnRetest[i].setBackgroundDrawable(this.getResources().getDrawable(
					R.drawable.result_review_soloution));
			btnRetest[i].setTextColor(Color.WHITE);
			LinearLayout lastChildView = new LinearLayout(context);
			lastChildView.setLayoutParams(lp);
			lastChildView.setGravity(Gravity.CENTER);
			lastChildView.addView(btnRetest[i]);
			children[i][4] = new MyListItem(context, lastChildView);

			examId = score.getExamId();
			topic_id = score.getTopic();

			Log.i("HistoryActivity", "topic_id=" + topic_id);
			// onClickListener cho cac button
			btnRetest[i].setOnClickListener(new OnClickListener() { 
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(),
							TopicCoverActivity.class);
					intent.putExtra("examId", examId);
					intent.putExtra("topic_id", topic_id);
					startActivity(intent);
					HistoryActivity.this.finish();
				}
			});

			i++;
		}// i

		myExList = (ExpandableListView) findViewById(R.id.history_expandablelist);
		myAdapter = new MyExpandableListAdapter(context, groups, children);

		myExList.setAdapter(myAdapter);

		/*
		 * show all int counter = myAdapter.getGroupCount(); for(int j = 1; j <=
		 * counter; j++) { myExList.expandGroup(j-1); }
		 */
		btnClearHistory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// clear giao dien
				list.clear();
				// xoa trong SharePreference
				Main.getScoreDAL().clear();
				// xoa Exams & QuestionExam trong DB
				Main.getExamHistoryDAL().clear();
				myAdapter = null;
				myExList.setAdapter(myAdapter);
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		this.getParent().onBackPressed();
	}
}
