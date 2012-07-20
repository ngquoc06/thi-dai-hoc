package com.thidaihoc.database;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.thidaihoc.Main;
import com.thidaihoc.entities.Topic;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 * 
 */
public class TopicDAL {
	// khai bao cac bien dung chung
	private SQLiteDatabase db;

	// Constructor
	public TopicDAL(SQLiteDatabase db) {
		this.db = Main.getDB();
	}

	/**
	 * INSERT 1 Topic
	 * 
	 * @param topic
	 *            : cau hoi can insert
	 * @return ket qua
	 */
	public long create(Topic topic) {
		Log.i("TopicDAL:", " create");
		// goi lenh INSERT va tra ve ket qua (-1: neu that bai, row_id: neu OK)
		long out = db.insert(DBHelper.TOPIC_TABLE_NAME, null,
				this.getContentValues(topic));
		return out;
	}

	/**
	 * UPDATE 1 Topic
	 * 
	 * @param topic
	 *            hoi can update
	 * @return ket qua
	 */
	public long update(Topic topic) {
		// goi lenh UPDATE va tra ve ket qua (-1: neu that bai, row_id: neu OK)
		return db.update(DBHelper.TOPIC_TABLE_NAME,
				this.getContentValues(topic), "_id = ?", new String[] { ""
						+ topic.getId() });
	}

	/**
	 * @param id
	 * 
	 */
	public int delete(Integer id) {
		return db.delete(DBHelper.TOPIC_TABLE_NAME, "_id = ?",
				new String[] { id.toString() });
	}

	/**
	 * Tra ve 1 Topics theo primary key trong DB
	 * 
	 * @param key
	 * @return Topic
	 */
	public Topic getTopicByID(int id) {
		Log.i("TopicDAL:", " getTopicsByID");
		Topic topic = new Topic();
		Cursor cursor = db.query(DBHelper.TOPIC_TABLE_NAME,
				DBHelper.TOPIC_COLUMNS, "rowid = ?", new String[] { "" + id },
				null, null, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			topic = convertCursor2Topic(cursor);
		}
		// giai phong cursor
		if (cursor != null) {
			cursor.close();
		}
		return topic;
	}

	/**
	 * Tra ve 1 Topics theo primary key trong Collection
	 * 
	 * @param key
	 * @param list
	 *            : Collection of Topics
	 * @return Topic
	 */
	public Topic getTopicByID(int id, Collection<Topic> list) {
		Log.i("TopicDAL:", " getTopicByID- Collection");
		for (Topic topic : list) {
			if (id == topic.getId()) {
				return topic;
			}
		}
		return null;
	}

	/**
	 * Tra ve danh sach Topics theo danh muc nao do
	 * 
	 * @param category
	 * @return danh sach Topics trong DB
	 */
	public Collection<Topic> getTopicsByCategory(String category) {
		Log.i("TopicDAL:", " getTopicsByCategory");
		Collection<Topic> list = new ArrayList<Topic>();
		Cursor cursor = db.query(DBHelper.TOPIC_TABLE_NAME,
				DBHelper.TOPIC_COLUMNS, DBHelper.TOPIC_CATEGORY + " = ?",
				new String[] { "" + category }, null, null, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			list.add(convertCursor2Topic(cursor));
		}
		// giai phong cursor
		if (cursor != null) {
			cursor.close();
		}

		return list;
	}

	/**
	 * @return all available Topics in DB
	 */
	public Collection<Topic> getTopics() {
		Log.i("TopicDAL:", " getTopics - all");
		Collection<Topic> list = new ArrayList<Topic>();
		Cursor cursor = db.query(DBHelper.TOPIC_TABLE_NAME,
				DBHelper.TOPIC_COLUMNS, null, null, null, null, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			list.add(convertCursor2Topic(cursor));
		}
		// giai phong cursor
		cursor.close();

		return list;
	}

	/**
	 * Chuyen cursor thanh Topic
	 * 
	 * @param cursor
	 * @return doi tuong Topic
	 */
	private Topic convertCursor2Topic(Cursor cursor) {
		Topic topic = new Topic();
		topic.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.TOPIC_ID)));
		topic.setTitle(cursor.getString(cursor
				.getColumnIndex(DBHelper.TOPIC_TITLE)));
		topic.setDescription(cursor.getString(cursor
				.getColumnIndex(DBHelper.TOPIC_DESCRIPTION)));
		topic.setCategory(cursor.getString(cursor
				.getColumnIndex(DBHelper.TOPIC_CATEGORY)));
		return topic;
	}

	/**
	 * @param topic
	 * @return ContentValues of a Topic
	 */
	public ContentValues getContentValues(Topic topic) {
		ContentValues values = new ContentValues();
		// id tự tăng nên ko can put(id)
		values.put(DBHelper.TOPIC_TITLE, topic.getTitle());
		values.put(DBHelper.TOPIC_CATEGORY, topic.getCategory());
		values.put(DBHelper.TOPIC_DESCRIPTION, topic.getDescription());
		return values;
	}

	/**
	 * Remove duplicates rows in Topics table
	 */
	public void removeDuplicates() {
		Log.i("TopicDAL:", " removeDuplicates");
		String sql = "delete from " + DBHelper.TOPIC_TABLE_NAME
				+ " where rowid in ( " + "select rowid from "
				+ DBHelper.TOPIC_TABLE_NAME + " as Duplicates where rowid > "
				+ "(select min(rowid) from " + DBHelper.TOPIC_TABLE_NAME
				+ " as First where " + "First." + DBHelper.TOPIC_TITLE
				+ "= Duplicates." + DBHelper.TOPIC_TITLE + "));";
		db.execSQL(sql);
		sql = null;
	}
}
