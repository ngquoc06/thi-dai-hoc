package com.quiz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Administrator
 * 
 */

public class DBHelper extends SQLiteOpenHelper {
	// khai bao cac hang so cho DB
	private static final String DB_NAME = "quiz.db";
	private static final CursorFactory CURSOR_FACTORY = null;
	private static final int DB_VERSION = 4;

	public static final String QUESTION_TABLE_NAME = "questions";
	public static final String QUESTION_XML_TAG_NAME = "question";
	public static final String QUESTION_ID = "_id";
	public static final String QUESTION_TITLE = "question_title";
	public static final String QUESTION_IS_MULTI = "isMulti";
	public static final String QUESTION_TOPIC = "topic";

	// Hang so cho table TOPIC
	public static final String TOPIC_TABLE_NAME = "topics";
	public static final String TOPIC_XML_TAG_NAME = "topic";
	public static final String TOPIC_ID = "_id";
	public static final String TOPIC_TITLE = "topic_title";
	public static final String TOPIC_CATEGORY = "category";
	public static final String TOPIC_DESCRIPTION = "description";

	// Hang so cho table CHOICE
	public static final String CHOICE_TABLE_NAME = "choices";
	public static final String CHOICE_XML_TAG_NAME = "choice";
	public static final String CHOICE_ID = "_id";
	public static final String CHOICE_TITLE = "choice_title";
	public static final String CHOICE_IS_CORRECT = "isCorrect";
	public static final String CHOICE_QUESTION_ID = "question_id";

	// Hang so cho table SCORE
	public static final String SCORE_TABLE_NAME = "scores";
	public static final String SCORE_XML_TAG_NAME = "score";
	public static final String SCORE_ID = "_id";
	public static final String SCORE_USERNAME = "username";
	public static final String SCORE_TOPIC = "topic";
	public static final String SCORE_TIME = "time";
	public static final String SCORE_DURATION = "duration";
	public static final String SCORE_UNANSWERED = "unanswered";
	public static final String SCORE_CORRECT = "correct";
	public static final String SCORE_TOTAL = "total";

	// Hang so cho table EXAM
	public static final String EXAM_TABLE_NAME = "exam_history";
	public static final String EXAM_ID = "_id";
	public static final String EXAM_TOPIC = "topic_title";
	public static final String EXAM_TAKEN_DATE = "taken_date";
	public static final String EXAM_SCORE_ID = "score_id";

	// Hang so cho table QUESTIONEXAM
	public static final String QUESTIONEXAM_TABLE_NAME = "question_exam";
	public static final String QUESTIONEXAM_ID = "_id";
	public static final String QUESTIONEXAM_QUESTION_ID = "question_id";
	public static final String QUESTIONEXAM_EXAM_ID = "examId";

	// mang 05 phan tu chua ca ten Column trong table QUESTION
	public static final String[] QUESTION_COLUMNS = { QUESTION_ID,
			QUESTION_TITLE, QUESTION_IS_MULTI, QUESTION_TOPIC };

	// mang 04 phan tu chua ca ten Column trong table TOPIC
	public static final String[] TOPIC_COLUMNS = { TOPIC_ID, TOPIC_TITLE,
			TOPIC_CATEGORY, TOPIC_DESCRIPTION };

	// mang 04 phan tu chua ca ten Column trong table CHOICE
	public static final String[] CHOICE_COLUMNS = { CHOICE_ID, CHOICE_TITLE,
			CHOICE_IS_CORRECT, CHOICE_QUESTION_ID };

	// mang 11 phan tu chua ca ten Column trong table SCORE
	public static final String[] SCORE_COLUMNS = { SCORE_ID, SCORE_USERNAME,
			SCORE_TOPIC, SCORE_TIME, SCORE_DURATION, SCORE_UNANSWERED,
			SCORE_CORRECT, SCORE_TOTAL };

	// EXAM COLUMNS
	public static final String[] EXAM_COLUMNS = { EXAM_ID, EXAM_TOPIC,
			EXAM_TAKEN_DATE, EXAM_SCORE_ID };

	// QUESTIONEXAM COLUMNS
	public static final String[] QUESTIONEXAM_COLUMNS = { QUESTIONEXAM_ID,
			QUESTIONEXAM_QUESTION_ID, QUESTIONEXAM_EXAM_ID };

	// sql tao table QUESTION
	private static final String QUESTION_CREATE = "create table "
			+ QUESTION_TABLE_NAME + "(" + QUESTION_ID
			+ " integer primary key autoincrement, " + QUESTION_TITLE
			+ " text not null, " + QUESTION_IS_MULTI + " integer, "
			+ QUESTION_TOPIC + " integer, " + " UNIQUE(" + QUESTION_TITLE
			+ ", " + QUESTION_TOPIC + ") ON CONFLICT IGNORE);";

	// sql tao table TOPIC
	private static final String TOPIC_CREATE = "create table "
			+ TOPIC_TABLE_NAME + "(" + TOPIC_ID
			+ " integer primary key autoincrement, " + TOPIC_TITLE
			+ " text not null, " + TOPIC_CATEGORY + " text, "
			+ TOPIC_DESCRIPTION + " text, UNIQUE(" + TOPIC_TITLE
			+ ") ON CONFLICT IGNORE);";

	// sql tao table CHOICE
	private static final String CHOICE_CREATE = "create table "
			+ CHOICE_TABLE_NAME + "(" + CHOICE_ID
			+ " integer primary key autoincrement, " + CHOICE_TITLE
			+ " text not null, " + CHOICE_IS_CORRECT + " integer, "
			+ CHOICE_QUESTION_ID + " integer);";

	// sql tao table SCORE
	/*
	 * private static final String SCORE_CREATE = "create table " +
	 * SCORE_TABLE_NAME + "(" + SCORE_ID +
	 * " integer primary key autoincrement, " + SCORE_USERNAME + " text, " +
	 * SCORE_TOPIC + " integer, " + SCORE_TIME + " integer, " + SCORE_DURATION +
	 * " integer, " + SCORE_UNANSWERED + " integer, " + SCORE_CORRECT +
	 * " integer, " + SCORE_TOTAL + " integer);";
	 */

	// sql tao table EXAM
	public static final String EXAM_CREATE = "create table " + EXAM_TABLE_NAME
			+ "(" + EXAM_ID + " integer primary key, " + EXAM_TOPIC + " text, "
			+ EXAM_TAKEN_DATE + " text, " + EXAM_SCORE_ID + " integer);";

	// sql tao table QUESTIONEXAM
	public static final String QUESTIONEXAM_CREATE = "create table "
			+ QUESTIONEXAM_TABLE_NAME + "(" + QUESTIONEXAM_ID
			+ " integer primary key autoincrement, " + QUESTIONEXAM_QUESTION_ID
			+ " integer, " + QUESTIONEXAM_EXAM_ID + " integer);";

	// sql xoa tat ca tables
	private static final String QUESTION_DROP = "DROP TABLE IF EXISTS "
			+ QUESTION_TABLE_NAME + "; ";
	private static final String TOPIC_DROP = "DROP TABLE IF EXISTS "
			+ TOPIC_TABLE_NAME + "; ";
	private static final String CHOICE_DROP = "DROP TABLE IF EXISTS "
			+ CHOICE_TABLE_NAME + "; ";
	public static final String EXAM_DROP = "DROP TABLE IF EXISTS "
			+ EXAM_TABLE_NAME + "; ";
	public static final String QUESTIONEXAM_DROP = "DROP TABLE IF EXISTS "
			+ QUESTIONEXAM_TABLE_NAME + "; ";
	/*
	 * private static final String SCORE_DROP = "DROP TABLE IF EXISTS " +
	 * SCORE_TABLE_NAME + "; ";
	 */

	// khai bao cac variable
	private SQLiteDatabase db = null;
	Context context = null;

	public DBHelper(Context context) {
		super(context, DB_NAME, CURSOR_FACTORY, DB_VERSION);
		this.context = context;
	}

	/**
	 * @return ket noi
	 */
	public SQLiteDatabase open() {
		// Log.i("DBHelper:", " open()");
		if (isOpened() == false) {
			Log.i("DBHelper:", " tao ket noi moi");
			db = this.getWritableDatabase();
		}
		return db;
	}

	/*
	 * dong ket noi
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#close()
	 */
	@Override
	public synchronized void close() {
		// Log.i("DBHelper:", " close()");
		if (isOpened())
			db.close();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		db.close();
	}

	/**
	 * @return true/false whether db is already opened
	 */
	public boolean isOpened() {
		if (db != null)
			return true;
		return false;
	}

	/*
	 * Create database & tables
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Log.i("DBHelper:", "OnCreate() ");
		db.beginTransaction();
		try {
			db.execSQL(QUESTION_CREATE);
			db.execSQL(TOPIC_CREATE);
			db.execSQL(CHOICE_CREATE);
			db.execSQL(EXAM_CREATE);
			db.execSQL(QUESTIONEXAM_CREATE);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}

		// import du lieu cho Questions, Topics & Choices
		CommonDAL common = new CommonDAL(db);
		common.importDataFromResource();
		// Main.getCommonDAL().importDataFromResource();

		// String strURL = context.getString(R.string.url_questions);
		// common.importDataFromURL(context, strURL);
	}

	/*
	 * Update Database
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e("DBHelper", "Cap nhat database tu version " + oldVersion + " to "
				+ newVersion);
		db.beginTransaction();
		try {
			db.execSQL(QUESTION_DROP);
			db.execSQL(TOPIC_DROP);
			db.execSQL(CHOICE_DROP);
			db.execSQL(EXAM_DROP);
			db.execSQL(QUESTIONEXAM_DROP);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		onCreate(db);
	}

}
