package com.quiz;

import java.util.Collection;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.quiz.database.ChoiceDAL;
import com.quiz.database.CommonDAL;
import com.quiz.database.DBHelper;
import com.quiz.database.ExamHistoryDAL;
import com.quiz.database.QuestionDAL;
import com.quiz.database.QuestionExamDAL;
import com.quiz.database.ScoreDAL;
import com.quiz.database.SettingDAL;
import com.quiz.database.TopicDAL;
import com.quiz.entities.Topic;

/* File quan ly Ket noi DB
 * Phai cau hinh Main Application trong AndroidMagifest.xml
 * <application
 android:icon="@drawable/ic_launcher"
 android:theme="@android:style/Theme.Black.NoTitleBar.Fullscreen"
 android:label="@string/app_name" 
 android:name=".Main">
 * 
 * */

public class Main extends Application {
	private static DBHelper dbHelper;
	private static SQLiteDatabase db;
	private static Context context;
	private static TopicDAL topicDAL;
	private static QuestionDAL questionDAL;
	private static ChoiceDAL choiceDAL;
	private static CommonDAL commonDAL;
	private static ExamHistoryDAL examHistoryDAL;
	private static QuestionExamDAL questionExamDAL;
	private static ScoreDAL scoreDAL;
	private static SettingDAL settingDAL;

	/**
	 * Tra ve DB dang ket noi
	 * 
	 * @return SQLiteDB
	 */
	public static SQLiteDatabase getDB() {
		// Log.i("Main", "dbHelper = " + dbHelper);
		db = dbHelper.open();
		return db;
	}

	/*
	 * Khoi tao cac DAL dung chung cho toan Application
	 */
	public static TopicDAL getTopicDAL() {
		return topicDAL;
	}

	public static QuestionDAL getQuestionDAL() {
		return questionDAL;
	}

	public static ChoiceDAL getChoiceDAL() {
		return choiceDAL;
	}

	public static ExamHistoryDAL getExamHistoryDAL() {
		return examHistoryDAL;
	}

	public static QuestionExamDAL getQuestionExamDAL() {
		return questionExamDAL;
	}

	public static CommonDAL getCommonDAL() {
		return commonDAL;
	}

	public static ScoreDAL getScoreDAL() {
		return scoreDAL;
	}

	public static SettingDAL getSettingDAL() {
		return settingDAL;
	}

	/**
	 * @return all topics in DB
	 */
	public static Collection<Topic> getTopics() {
		return getTopicDAL().getTopics();
	}

	public static Context getContext() {
		return context;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		Log.i("Main", "onCreate");

		dbHelper = new DBHelper(this);
		context = this.getApplicationContext();
		db = dbHelper.open();

		// khoi tao cac DAL - nho dung theo thu tu nay
		settingDAL = new SettingDAL(context);
		scoreDAL = new ScoreDAL(context);
		commonDAL = new CommonDAL(db);
		topicDAL = new TopicDAL(db);
		questionDAL = new QuestionDAL(db);
		choiceDAL = new ChoiceDAL(db);
		examHistoryDAL = new ExamHistoryDAL(db);
		questionExamDAL = new QuestionExamDAL(db);

		// updateLanguage
		Main.getSettingDAL().updateLanguage(context);
		// thuc hien cong viec asynchronous
		// new AsyncUpdate().execute(context);

	}

	@Override
	public void onTerminate() {
		Log.i("Main", "onTerminate");
		dbHelper.close();
		super.onTerminate();
	}

}
