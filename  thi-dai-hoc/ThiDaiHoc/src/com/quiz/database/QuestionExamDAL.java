package com.quiz.database;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.quiz.Main;
import com.quiz.entities.Question;
import com.quiz.entities.QuestionExam;

public class QuestionExamDAL {
	// khai bao cac bien dung chung
	private SQLiteDatabase db;
	private static final String TAG = "QuestionExamDAL";

	public QuestionExamDAL(SQLiteDatabase db) {
		this.db = db;
	}

	public void createQuestionExamForExam(Collection<Question> questionList,
			int examId) {
		Log.i(TAG, "createQuestionExamForExam()");
		// unlock db
		db.setLockingEnabled(false);
		// begin Transaction
		db.beginTransaction();

		// Create a single InsertHelper to handle this set of insertions.
		InsertHelper helper = new InsertHelper(db,
				DBHelper.QUESTIONEXAM_TABLE_NAME);
		// Get the numeric indexes for each of the columns that we're updating
		final int iQuestionId = helper
				.getColumnIndex(DBHelper.QUESTIONEXAM_QUESTION_ID);
		final int iExamId = helper
				.getColumnIndex(DBHelper.QUESTIONEXAM_EXAM_ID);

		// insert cac cau hoi
		for (Question question : questionList) {
			helper.prepareForInsert();
			helper.bind(iQuestionId, question.getId());
			helper.bind(iExamId, examId);
			// EXECUTE
			helper.execute();
		}

		// COMMIT Transaction
		db.setTransactionSuccessful();
		// end Transaction
		db.endTransaction();
		// lock db as default
		db.setLockingEnabled(true);

		// free up memory
		helper.close();
		helper = null;
		if (questionList != null) {
			questionList.clear();
			questionList = null;
		}
	}

	/**
	 * Tra ve danh sach cau hoi thuoc mot de thi nao do
	 * 
	 * @param examId
	 * @return danh sach QuestionExam trong DB
	 */
	public Collection<Question> getQuestionList(Integer examId) {
		Log.i(TAG, " getTopicsByCategory");
		Collection<Question> list = new ArrayList<Question>();
		Cursor cursor = db.query(DBHelper.QUESTIONEXAM_TABLE_NAME,
				new String[] { DBHelper.QUESTIONEXAM_QUESTION_ID },
				DBHelper.QUESTIONEXAM_EXAM_ID + " = ?", new String[] { ""
						+ examId }, null, null, null);
		Collection<Integer> questionIds = new ArrayList<Integer>();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			questionIds.add(cursor.getInt(0));
		}
		// get Questions from questionIds
		list = Main.getQuestionDAL().getQuestionsByIds(questionIds);
		// giai phong cursor
		if (cursor != null) {
			cursor.close();
		}

		questionIds.clear();
		questionIds = null;
		return list;
	}

	/**
	 * Xoa cac cau hoi thuoc bo de thi nao do
	 * 
	 * @param ExamId
	 * 
	 */
	public int delete(Integer examId) {
		return db.delete(DBHelper.QUESTIONEXAM_TABLE_NAME,
				DBHelper.QUESTIONEXAM_EXAM_ID + " = ?", new String[] { ""
						+ examId });
	}

	/**
	 * Xoa cac cau hoi thuoc Bo de thi KHONG hop le
	 * 
	 * @param ExamId
	 * 
	 */
	public int deleteFromId(Integer minExamId) {
		return db.delete(DBHelper.QUESTIONEXAM_TABLE_NAME,
				DBHelper.QUESTIONEXAM_EXAM_ID + " < ?", new String[] { ""
						+ minExamId });
	}

	/**
	 * Chuyen cursor thanh Exam
	 * 
	 * @param cursor
	 * @return doi tuong Exam
	 */
	/*
	 * private QuestionExam convertCursor2QuestionExam(Cursor cursor) {
	 * QuestionExam obj = new QuestionExam();
	 * obj.setId(cursor.getInt(cursor.getColumnIndex("rowid")));
	 * obj.setQuestionId(cursor.getInt(cursor
	 * .getColumnIndex(DBHelper.QUESTIONEXAM_QUESTION_ID)));
	 * obj.setExamId(cursor.getInt(cursor
	 * .getColumnIndex(DBHelper.QUESTIONEXAM_EXAM_ID))); return obj; }
	 */

	/**
	 * @param Exam
	 * @return ContentValues of a Exam
	 */
	public ContentValues getContentValues(QuestionExam obj) {
		ContentValues values = new ContentValues();
		// id tu tang nen ko can add zo
		// values.put(DBHelper.QUESTIONEXAM_ID, obj.getId());
		values.put(DBHelper.QUESTIONEXAM_QUESTION_ID, obj.getQuestionId());
		values.put(DBHelper.QUESTIONEXAM_EXAM_ID, obj.getExamId());
		return values;
	}

}
