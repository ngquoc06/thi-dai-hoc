package com.quiz.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.quiz.entities.Choice;
import com.quiz.entities.Question;

/**
 * @author Administrator
 * 
 */
public class ChoiceDAL {
	// khai bao cac bien dung chung
	private SQLiteDatabase db;

	// Constructor
	public ChoiceDAL(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * INSERT 1 Choice
	 * 
	 * @param choice
	 *            : cau hoi can insert
	 * @return ket qua
	 */
	public long create(Choice choice) {
		Log.i("ChoiceDAL:", " create");
		// goi lenh INSERT va tra ve ket qua (-1: neu that bai, row_id: neu OK)
		long out = db.insert(DBHelper.CHOICE_TABLE_NAME, null,
				this.getContentValues(choice));
		return out;
	}

	/**
	 * UPDATE 1 Choice
	 * 
	 * @param choice
	 *            hoi can update
	 * @return ket qua
	 */
	public long update(Choice choice) {
		// goi lenh UPDATE va tra ve ket qua (-1: neu that bai, row_id: neu OK)
		return db.update(DBHelper.CHOICE_TABLE_NAME,
				this.getContentValues(choice), "rowid = ?", new String[] { ""
						+ choice.getId() });
	}

	/**
	 * @param id
	 * 
	 */
	public int delete(Integer id) {
		return db.delete(DBHelper.CHOICE_TABLE_NAME, "rowid = ?",
				new String[] { id.toString() });
	}

	/**
	 * Tra ve 1 Choices theo primary key trong DB
	 * 
	 * @param key
	 * @return Choice
	 */
	public Choice getChoiceByID(int id) {
		Log.i("ChoiceDAL:", " getChoicesByID");
		Choice choice = null;
		Cursor cursor = db.query(DBHelper.CHOICE_TABLE_NAME,
				DBHelper.CHOICE_COLUMNS, "rowid = ?", new String[] { "" + id },
				null, null, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			choice = convertCursor2Choice(cursor);
		}
		// giai phong cursor
		if (cursor != null) {
			cursor.close();
		}
		return choice;
	}

	/**
	 * Tra ve 1 Choices theo primary key trong Collection
	 * 
	 * @param key
	 * @param list
	 *            : Collection of Choices
	 * @return Choice
	 */
	public Choice getChoiceByID(int id, Collection<Choice> list) {
		Log.i("ChoiceDAL:", " getChoiceByID- Collection");
		for (Choice choice : list) {
			if (id == choice.getId()) {
				return choice;
			}
		}
		return null;
	}

	/**
	 * Tra ve danh sach cac Choices theo Question
	 * 
	 * @param question_id
	 * @return danh sach Choices theo question id
	 */
	public Collection<Choice> getChoicesByQuestion(Integer question_id) {
		// Log.i("ChoiceDAL:", " getChoicesByQuestion");
		Collection<Choice> list = new ArrayList<Choice>();
		Cursor cursor = db.query(DBHelper.CHOICE_TABLE_NAME,
				DBHelper.CHOICE_COLUMNS, DBHelper.CHOICE_QUESTION_ID + " = ?",
				new String[] { "" + question_id }, null, null, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			list.add(convertCursor2Choice(cursor));
		}
		// giai phong cursor
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}

	/**
	 * @param questionList
	 *            danh sach du lieu cac question
	 * @return Choices cho Question List
	 */
	public Collection<Choice> getChoices(List<Question> questionList) {
		Log.i("ChoiceDAL:", " getChoices - question arraylist");
		Collection<Choice> list = new ArrayList<Choice>();

		StringBuilder buff = new StringBuilder(" IN (");

		/* Khu vua tao ? tham so */
		int size = questionList.size(); // size >= 1
		if (size > 1) {
			for (int i = 0; i < size; i++) {
				buff.append(",?");
			}
		} else { // size = 1
			buff.append("?");
		}
		buff.append(") ");

		// tao String[] gia tri cho tham so
		String[] ids = new String[size];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = "" + questionList.get(i).getId();
		}

		// khu vua query
		Cursor cursor = db.query(DBHelper.CHOICE_TABLE_NAME,
				DBHelper.CHOICE_COLUMNS,
				DBHelper.CHOICE_QUESTION_ID + buff.toString(), ids, null, null,
				null);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			list.add(convertCursor2Choice(cursor));
		}
		// giai phong cursor
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}

	/**
	 * Chuyen cursor thanh Choice
	 * 
	 * @param cursor
	 * @return doi tuong Choice
	 */
	private Choice convertCursor2Choice(Cursor cursor) {
		Choice choice = new Choice();
		choice.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.CHOICE_ID)));
		choice.setTitle(cursor.getString(cursor
				.getColumnIndex(DBHelper.CHOICE_TITLE)));
		choice.setIsCorrect(cursor.getInt(cursor
				.getColumnIndex(DBHelper.CHOICE_IS_CORRECT)));
		choice.setQuestion_id(cursor.getInt(cursor
				.getColumnIndex(DBHelper.CHOICE_QUESTION_ID)));
		return choice;
	}

	/**
	 * @param choice
	 * @return ContentValues of a Choice
	 */
	public ContentValues getContentValues(Choice choice) {
		ContentValues values = new ContentValues();
		// id tu tang nen ko can add zo
		values.put(DBHelper.CHOICE_TITLE, choice.getTitle());
		values.put(DBHelper.CHOICE_IS_CORRECT, choice.getIsCorrect());
		values.put(DBHelper.CHOICE_QUESTION_ID, choice.getQuestion_id());
		return values;
	}

	/**
	 * Remove duplicates rows in Choices table
	 */
	public void removeDuplicates() {
		Log.i("ChoiceDAL:", " removeDuplicates");
		String sql = "delete from " + DBHelper.CHOICE_TABLE_NAME
				+ " where rowid in ( " + "select rowid from "
				+ DBHelper.CHOICE_TABLE_NAME + " as Duplicates where rowid > "
				+ "(select min(rowid) from " + DBHelper.CHOICE_TABLE_NAME
				+ " as First where First." + DBHelper.CHOICE_TITLE
				+ " = Duplicates." + DBHelper.CHOICE_TITLE + " GROUP BY First."
				+ DBHelper.CHOICE_QUESTION_ID + "));";
		db.execSQL(sql);
		sql = null;
	}
}
