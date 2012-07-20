package com.thidaihoc.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.thidaihoc.Main;
import com.thidaihoc.entities.Score;

/**
 * Thao tac tren SharedPreferenced danh cho Score
 * 
 * @author Administrator
 * 
 */
public class ScoreDAL {
	Context context;
	public static final int QUIZ_NO_OF_SCORES_IN_HISTORES = Main
			.getSettingDAL().getSetting().getHistorySize();
	public static final int MODE = Context.MODE_PRIVATE;

	public static final String SCORE_TIME = "time";
	public static final String SCORE_TOPIC = "topic";
	public static final String SCORE_TOPIC_TITLE = "topic_title";
	public static final String SCORE_DURATION = "duration";
	public static final String SCORE_NO_OF_QUESTIONS = "noOfQuestions";
	public static final String SCORE_CORRECT = "correct";
	public static final String SCORE_UNANSWERED = "unanswered";
	public static final String SCORE_USERNAME = "username";
	public static final String SCORE_ID = "_id";
	public static final String SCORE_PASSED_SCORE = "passedScore";
	public static final String SCORE_EXAM_ID = "examId";

	public static final String TAG = "ResultHistoryModel";

	public ScoreDAL(Context context) {
		this.context = context;

	}

	public Editor getEditor(String prefName) {
		return this.context.getSharedPreferences(prefName, MODE).edit();
	}

	public void clear() {
		for (int i = QUIZ_NO_OF_SCORES_IN_HISTORES; i >= 1; i--) {
			this.context.getSharedPreferences("" + i, MODE).edit().clear()
					.commit();
		}
	}

	/**
	 * Luu tru history, the oldest score se bi thay the
	 * 
	 * @param score
	 * @return true neu nhu save thanh cong
	 */
	public boolean store(Score score) {
		boolean flag = false;

		Log.i("ScoreDAL, store()", "tp_id=" + score.getTopic());
		Log.i("ScoreDAL, store()", "tp_title=" + score.getTopicTitle());

		if (this.isHistoryFull()) {

			// lay 4 score cu~ va save lai trong sharedpref moi
			// e.g: score 5 duoc save zo Pref 4
			int index = 1;
			List<Score> list = this.getFullScoreHistoryExceptFirstOne();

			for (Score s : list) {
				Log.i("ScoreDAL, store()", "tp_id 2=" + score.getTopic());
				Log.i("ScoreDAL, store()",
						"tp_title 2=" + score.getTopicTitle());

				Editor editor = this.getEditor("" + index++);
				editor.putString(SCORE_TIME, s.getTime());
				editor.putInt(SCORE_TOPIC, s.getTopic());
				editor.putString(SCORE_TOPIC_TITLE, s.getTopicTitle());
				editor.putInt(SCORE_DURATION, s.getDuration());
				editor.putInt(SCORE_NO_OF_QUESTIONS, s.getNoOfQuestions());
				editor.putInt(SCORE_CORRECT, s.getCorrect());
				editor.putInt(SCORE_UNANSWERED, s.getUnanswered());
				editor.putString(SCORE_USERNAME, s.getUsername());
				editor.putInt(SCORE_ID, s.getId());
				editor.putInt(SCORE_PASSED_SCORE, s.getPassedScore());
				editor.putInt(SCORE_EXAM_ID, s.getExamId());
				editor.commit();
			}
			// cap nhat Score moi nhut o vi tri TOP
			Editor editor = this.getEditor("" + QUIZ_NO_OF_SCORES_IN_HISTORES);
			editor.putString(SCORE_TIME, score.getTime());
			editor.putInt(SCORE_TOPIC, score.getTopic());
			editor.putString(SCORE_TOPIC_TITLE, score.getTopicTitle());
			editor.putInt(SCORE_DURATION, score.getDuration());
			editor.putInt(SCORE_NO_OF_QUESTIONS, score.getNoOfQuestions());
			editor.putInt(SCORE_CORRECT, score.getCorrect());
			editor.putInt(SCORE_UNANSWERED, score.getUnanswered());
			editor.putString(SCORE_USERNAME, score.getUsername());
			editor.putInt(SCORE_ID, score.getId());
			editor.putInt(SCORE_PASSED_SCORE, score.getPassedScore());
			editor.putInt(SCORE_EXAM_ID, score.getExamId());
			editor.commit();
		} else {
			// tao SCORE tiep theo trong History (e.g: neu da co 3 thi tao 4)
			Editor editor = this.getEditor(""
					+ (this.getCurrentSizeOfHistory() + 1));
			editor.putString(SCORE_TIME, score.getTime());
			editor.putInt(SCORE_TOPIC, score.getTopic());
			editor.putString(SCORE_TOPIC_TITLE, score.getTopicTitle());
			editor.putInt(SCORE_DURATION, score.getDuration());
			editor.putInt(SCORE_NO_OF_QUESTIONS, score.getNoOfQuestions());
			editor.putInt(SCORE_CORRECT, score.getCorrect());
			editor.putInt(SCORE_UNANSWERED, score.getUnanswered());
			editor.putString(SCORE_USERNAME, score.getUsername());
			editor.putInt(SCORE_PASSED_SCORE, score.getPassedScore());
			editor.putInt(SCORE_ID, score.getId());
			editor.putInt(SCORE_EXAM_ID, score.getExamId());
			editor.commit();
		}

		return flag;
	}

	/**
	 * @return true if there are enough scores in history (e.g: 5)
	 */
	public boolean isHistoryFull() {
		return (this.getCurrentSizeOfHistory() == QUIZ_NO_OF_SCORES_IN_HISTORES);
	}

	public int getCurrentSizeOfHistory() {
		int size = QUIZ_NO_OF_SCORES_IN_HISTORES;
		while (size > 0) {
			if (this.context.getSharedPreferences("" + size, MODE).contains(
					SCORE_TIME)) {
				break;
			} else
				size--;
		}
		return size;
	}

	public List<Score> getScoreHistory() {
		int currentHistorySize = this.getCurrentSizeOfHistory();
		List<Score> list = new ArrayList<Score>(currentHistorySize);

		for (int i = currentHistorySize; i >= 1; i--) {
			SharedPreferences pref = this.context.getSharedPreferences("" + i,
					MODE);
			Score score = new Score();
			score.setTime(pref.getString(SCORE_TIME, "<no time>"));
			score.setTopic(pref.getInt(SCORE_TOPIC, 0));
			Log.i("ScoreDAL, getScoreHistory()", "tp_id=" + score.getTopic());
			Log.i("ScoreDAL, getScoreHistory()",
					"tp_title=" + score.getTopicTitle());
			score.setTopicTitle(pref.getString(SCORE_TOPIC_TITLE, "<unknown>"));
			score.setDuration(pref.getInt(SCORE_DURATION, 0));
			score.setNoOfQuestions(pref.getInt(SCORE_NO_OF_QUESTIONS, 0));
			score.setCorrect(pref.getInt(SCORE_CORRECT, 0));
			score.setUnanswered(pref.getInt(SCORE_UNANSWERED, 0));
			score.setUsername(pref.getString(SCORE_USERNAME, "unknown"));
			score.setPassedScore(pref.getInt(SCORE_PASSED_SCORE, 0));
			score.setExamId(pref.getInt(SCORE_EXAM_ID, 0));
			list.add(score);
		}

		return list;
	}

	public List<Score> getFullScoreHistoryExceptFirstOne() {
		List<Score> list = new ArrayList<Score>(
				QUIZ_NO_OF_SCORES_IN_HISTORES - 1);

		for (int i = 2; i <= QUIZ_NO_OF_SCORES_IN_HISTORES; i++) {
			SharedPreferences pref = this.context.getSharedPreferences("" + i,
					MODE);
			Score score = new Score();
			score.setTime(pref.getString(SCORE_TIME, "<no time>"));
			score.setTopic(pref.getInt(SCORE_TOPIC, 0));
			score.setTopicTitle(pref.getString(SCORE_TOPIC_TITLE, "<unknown>"));
			score.setDuration(pref.getInt(SCORE_DURATION, 0));
			score.setNoOfQuestions(pref.getInt(SCORE_NO_OF_QUESTIONS, 0));
			score.setCorrect(pref.getInt(SCORE_CORRECT, 0));
			score.setUnanswered(pref.getInt(SCORE_UNANSWERED, 0));
			score.setUsername(pref.getString(SCORE_USERNAME, "unknown"));
			score.setPassedScore(pref.getInt(SCORE_PASSED_SCORE, 0));
			score.setExamId(pref.getInt(SCORE_EXAM_ID, 0));
			list.add(score);
		}

		return list;
	}

}
