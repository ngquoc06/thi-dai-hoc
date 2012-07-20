package com.thidaihoc.database;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.thidaihoc.entities.Question;
import com.thidaihoc.utils.ConverterHelper;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 * 
 */
public class QuestionDAL {
	// khai bao cac bien dung chung
	private SQLiteDatabase db;

	// Constructor
	public QuestionDAL(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * INSERT 1 Question
	 * 
	 * @param question
	 *            cau hoi can insert
	 * @return ket qua
	 */
	public long create(Question question) {
		// goi lenh INSERT va tra ve ket qua (-1: neu that bai, row_id: neu OK)
		long out = db.insert(DBHelper.QUESTION_TABLE_NAME, null,
				this.getContentValues(question));
		return out;
	}

	/**
	 * UPDATE 1 Question
	 * 
	 * @param questioncau
	 *            hoi can update
	 * @return ket qua
	 */
	public long update(Question question) {
		// goi lenh UPDATE va tra ve ket qua (-1: neu that bai, row_id: neu OK)
		return db.update(DBHelper.QUESTION_TABLE_NAME,
				this.getContentValues(question), "_id = ?", new String[] { ""
						+ question.getId() });
	}

	/**
	 * @param id
	 *            PK of Question
	 */
	public int delete(Integer id) {
		return db.delete(DBHelper.QUESTION_TABLE_NAME, "_id = ?",
				new String[] { id.toString() });
	}

	/**
	 * @param topic
	 * @return danh sach Question trong DB
	 */
	public Collection<Question> getQuestionsByTopic(int topic) {
		Collection<Question> list = new ArrayList<Question>();
		Cursor cursor = db.query(DBHelper.QUESTION_TABLE_NAME,
				DBHelper.QUESTION_COLUMNS, DBHelper.QUESTION_TOPIC + " = ?",
				new String[] { "" + topic }, null, null, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			list.add(convertCursor2Question(cursor));
		}
		// giai phong cursor
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}

	/**
	 * Bo tao cau hoi ngau nhien theo moi chu de So luong cau hoi duoc lay ra tu
	 * Setting
	 * 
	 * @param numberOfQuestions
	 *            So luong cau hoi
	 * @param topic
	 *            cac cau hoi thuoc loai chu de nao
	 * @return Chon numberOfQuestions theo mot topic nao do
	 */
	public Collection<Question> getQuestionsByTopic(int topic,
			int numberOfQuestions) {
		Collection<Question> list = new ArrayList<Question>();
		Cursor cursor = db.query(DBHelper.QUESTION_TABLE_NAME,
				DBHelper.QUESTION_COLUMNS, DBHelper.QUESTION_TOPIC + " = ?",
				new String[] { "" + topic }, null, null, "RANDOM() LIMIT "
						+ numberOfQuestions);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			list.add(convertCursor2Question(cursor));
		}
		// giai phong cursor
		if (cursor != null) {
			cursor.close();
		}

		return list;
	}

	/**
	 * @param isMulti
	 * @return Questions thuoc loai co the co nhieu cau tra loi dung
	 */
	public Collection<Question> getQuestionsMulti() {
		Collection<Question> list = new ArrayList<Question>();
		Cursor cursor = db.query(DBHelper.QUESTION_TABLE_NAME,
				DBHelper.QUESTION_COLUMNS, DBHelper.QUESTION_IS_MULTI + " = 1",
				null, null, null, null);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			list.add(convertCursor2Question(cursor));
		}

		if (cursor != null) {
			cursor.close();
		}
		return list;
	}

	/**
	 * @param questionIds
	 *            danh sach id cac cau hoi
	 * @return Questions with given ids
	 */
	public Collection<Question> getQuestionsByIds(
			Collection<Integer> questionIds) {
		Collection<Question> list = new ArrayList<Question>();

		if (questionIds.size() <= 0) {
			return list;
		}
		// unlock db
		db.setLockingEnabled(false);
		// begin Transaction
		db.beginTransaction();

		String in = ConverterHelper.convertMultiValues2String(questionIds);

		Cursor cursor = db.query(DBHelper.QUESTION_TABLE_NAME,
				DBHelper.QUESTION_COLUMNS, " rowid IN " + in, null, null, null,
				null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			list.add(convertCursor2Question(cursor));
		}
		// giai phong cursor
		cursor.close();
		// COMMIT Transaction
		db.setTransactionSuccessful();
		// end Transaction
		db.endTransaction();
		// lock db as default
		db.setLockingEnabled(true);

		return list;
	}

	/**
	 * @return all active Questions
	 */
	public Collection<Question> getQuestions() {
		Collection<Question> list = new ArrayList<Question>();
		Cursor cursor = db.query(DBHelper.QUESTION_TABLE_NAME,
				DBHelper.QUESTION_COLUMNS, null, null, null, null, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			list.add(convertCursor2Question(cursor));
		}
		// giai phong cursor
		cursor.close();

		return list;
	}

	/**
	 * Chuyen cursor thanh Question
	 * 
	 * @param cursor
	 * @return doi tuong Question
	 */
	private Question convertCursor2Question(Cursor cursor) {
		Question question = new Question();
		question.setId(cursor.getInt(0));
		question.setTitle(cursor.getString(1));
		question.setIsMulti(cursor.getInt(2));
		question.setTopic(cursor.getInt(3));
		return question;
	}

	/**
	 * @param question
	 * @return ContentValues of a Question
	 */
	public ContentValues getContentValues(Question question) {
		ContentValues values = new ContentValues();
		// id
		values.put(DBHelper.QUESTION_TITLE, question.getTitle());
		values.put(DBHelper.QUESTION_IS_MULTI, question.getIsMulti());
		values.put(DBHelper.QUESTION_TOPIC, question.getTopic());
		return values;
	}

	/**
	 * Remove duplicates rows in Questions table
	 */
	public void removeDuplicates() {
		Log.i("QuestionDAL:", " removeDuplicates");
		String sql = "delete from "
				+ DBHelper.QUESTION_TABLE_NAME
				+ " where rowid in ( "
				+ "select rowid from "
				+ DBHelper.QUESTION_TABLE_NAME
				+ " as Duplicates where rowid > "
				+ "( select min(rowid) from "
				+ DBHelper.QUESTION_TABLE_NAME
				+ " as First where "
				+ "First.topic= Duplicates.topic First.title = Duplicates.title GROUP BY First.topic));";
		db.execSQL(sql);
		sql = null;
	}
}
