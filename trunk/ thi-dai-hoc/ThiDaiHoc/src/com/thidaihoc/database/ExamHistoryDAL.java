package com.thidaihoc.database;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.thidaihoc.Main;
import com.thidaihoc.entities.Exam;

/**
 * Class chuyen quan ly lich su thi
 * 
 * @author Administrator
 * 
 */

public class ExamHistoryDAL {
	// khai bao cac bien dung chung
	private SQLiteDatabase db;
	private static final String TAG = "ExamDAL";

	// Constructor
	public ExamHistoryDAL(SQLiteDatabase db) {
		this.db = db;
	}

	public long create(Exam exam) {
		long out = 0;
		int examId = exam.getId();
		int history_size = Main.getSettingDAL().getSetting().getHistorySize();
		if (this.getCurrentNumberOfExams() > history_size) {
			// xoa exam cu~ nhut
			deleteLowest();
			// xoa du lieu exam tuong ung ben QuestionExam table
			// xoa nhung questionexam co exam KHONG hop le
			// vi du: cho phep history_size = 10, examId max la 51
			// => se xoa nhung examId tu 40 tro xuong
			Main.getQuestionExamDAL().deleteFromId(examId - history_size);
		}
		// insert vao Exam table
		out = db.insert(DBHelper.EXAM_TABLE_NAME, null,
				this.getContentValues(exam));

		// luu tru cac question vao History (QuestionExam table)
		Main.getQuestionExamDAL().createQuestionExamForExam(
				exam.getQuestionList(), examId);

		return out;
	}

	public void clear() {
		db.beginTransaction();
		try {
			db.execSQL(DBHelper.EXAM_DROP);
			db.execSQL(DBHelper.QUESTIONEXAM_DROP);

			db.execSQL(DBHelper.EXAM_CREATE);
			db.execSQL(DBHelper.QUESTIONEXAM_CREATE);
			db.setTransactionSuccessful();

		} finally {
			db.endTransaction();
		}
	}

	/**
	 * Chuyen cursor thanh Exam
	 * 
	 * @param cursor
	 * @return doi tuong Exam
	 */
	private Exam convertCursor2Exam(Cursor cursor) {
		Exam obj = new Exam();
		obj.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.EXAM_ID)));
		obj.setTopic(cursor.getString(cursor
				.getColumnIndex(DBHelper.EXAM_TOPIC)));
		obj.setTakenDate(cursor.getString(cursor
				.getColumnIndex(DBHelper.EXAM_TAKEN_DATE)));
		obj.setScoreId(cursor.getInt(cursor
				.getColumnIndex(DBHelper.EXAM_SCORE_ID)));
		return obj;
	}

	/**
	 * @param Exam
	 * @return ContentValues of a Exam
	 */
	public ContentValues getContentValues(Exam obj) {
		ContentValues values = new ContentValues();
		// id tu tang nen ko can add zo
		values.put(DBHelper.EXAM_ID, obj.getId());
		values.put(DBHelper.EXAM_TOPIC, obj.getTopic());
		values.put(DBHelper.EXAM_TAKEN_DATE, obj.getTakenDate());
		values.put(DBHelper.EXAM_SCORE_ID, obj.getScoreId());
		return values;
	}

	/**
	 * Xoa id nho nhut
	 * 
	 */
	public int deleteLowest() {
		return db.delete(DBHelper.EXAM_TABLE_NAME,
				"rowid in (SELECT rowid FROM " + DBHelper.EXAM_TABLE_NAME
						+ " ORDER BY rowid ASC LIMIT 1);", null);

	}

	/**
	 * Xoa exam
	 * 
	 * @param id
	 * 
	 */
	public int delete(Integer id) {
		return db.delete(DBHelper.EXAM_TABLE_NAME, "rowid = ?",
				new String[] { id.toString() });
	}

	/**
	 * @return Number of Exams availble in DB
	 */
	public int getCurrentNumberOfExams() {
		int count = 0;
		Cursor cursor = db.query(DBHelper.EXAM_TABLE_NAME,
				new String[] { "count(rowid)" }, null, null, null, null, null);
		cursor.moveToFirst();
		count = cursor.getInt(0);
		// giai phong cursor
		if (cursor != null) {
			cursor.close();
		}
		return count;
	}

	/**
	 * @return Max Exam ID
	 */
	public int getMaxExamId() {
		int max = 0;
		Cursor cursor = db.query(DBHelper.EXAM_TABLE_NAME,
				new String[] { "max(rowid)" }, null, null, null, null, null);
		cursor.moveToFirst();
		max = cursor.getInt(0);
		// giai phong cursor
		if (cursor != null) {
			cursor.close();
		}
		return max;
	}

	/**
	 * Tra ve 1 Exam theo primary key trong DB
	 * 
	 * @param id
	 * @return Exam History
	 */
	public Exam getExamByID(int id) {
		Exam obj = new Exam();
		Cursor cursor = db.query(DBHelper.EXAM_TABLE_NAME,
				DBHelper.EXAM_COLUMNS, "rowid = ?", new String[] { "" + id },
				null, null, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			obj = convertCursor2Exam(cursor);
			obj.setQuestionList(Main.getQuestionExamDAL().getQuestionList(id));
		}
		// giai phong cursor
		if (cursor != null) {
			cursor.close();
		}
		return obj;
	}

	/**
	 * @return all available Exams in DB
	 */
	public Collection<Exam> getExamsHistory() {
		Log.i(TAG, " getExamsHistory all");
		Collection<Exam> list = new ArrayList<Exam>();
		Cursor cursor = db.query(DBHelper.EXAM_TABLE_NAME,
				DBHelper.EXAM_COLUMNS, null, null, null, null, " rowid DESC");
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("rowid"));
			String topic = cursor.getString(cursor
					.getColumnIndex(DBHelper.EXAM_TOPIC));
			int scoreId = cursor.getInt(cursor
					.getColumnIndex(DBHelper.EXAM_SCORE_ID));
			String takenDate = cursor.getString(cursor
					.getColumnIndex(DBHelper.EXAM_SCORE_ID));
			list.add(new Exam(id, topic, takenDate, scoreId));
		}
		// giai phong cursor
		cursor.close();
		return list;
	}

}
