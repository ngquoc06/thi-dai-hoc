package com.quiz.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.quiz.Main;
import com.quiz.entities.Choice;
import com.quiz.entities.Question;
import com.quiz.entities.Topic;
import com.quiz.utils.NetworkUtility;
import com.quiz.xml.QuestionXMLHandler;
import com.quiz.xml.XMLHelper;

public class CommonDAL {
	// khai bao cac bien dung chung
	private SQLiteDatabase db;

	// Constructor
	public CommonDAL(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * Import data from res/xml/questions.xml
	 * 
	 * @param context
	 */
	public void importDataFromResource() {
		XMLHelper helper = new XMLHelper();
		helper.readXML(Main.getContext());
		Collection<Topic> topicList = helper.getTopicList();
		Collection<Question> questionList = helper.getQuestionList();
		Collection<Choice> choiceList = helper.getChoiceList();
		Log.e("importDataFromResource",
				"ChoiceList size = " + choiceList.size());

		this.storeData(topicList, questionList, choiceList);
	}

	/**
	 * Import data from an URL e.g
	 * https://dl.dropbox.com/u/7059745/questions.xml
	 * 
	 * @param context
	 * @param strURL
	 */
	public boolean importDataFromURL(String strURL) {
		Log.i("CommonDAL", "importDataFromURL() " + strURL);
		boolean flag = true;
		InputStream in = NetworkUtility.checkFileAvailableStatus(strURL);

		if (in != null) {
			Collection<Topic> topicList = null;
			Collection<Question> questionList = null;
			Collection<Choice> choiceList = null;
			QuestionXMLHandler handler = new QuestionXMLHandler();
			XMLReader xmlreader;
			try {
				xmlreader = XMLHelper.initializeReader();
				xmlreader.setContentHandler(handler);
				xmlreader.parse(new InputSource(in));
			} catch (ParserConfigurationException e) {
				Log.e("CommonDAL",
						"importDataFromURL() - ParserConfigurationException");
				flag = false;
			} catch (SAXException e) {
				Log.e("CommonDAL", "importDataFromURL()- SAXException");
				flag = false;
			} catch (IOException e) {
				Log.e("CommonDAL", "importDataFromURL() - IOException");
				flag = false;
			}
			topicList = handler.getTopicList();
			questionList = handler.getQuestionList();
			choiceList = handler.getChoiceList();

			Log.i("topicList", topicList.toString());
			this.storeData(topicList, questionList, choiceList);
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * Import Topics, Questions and Choices into SQLite
	 * 
	 * @param context
	 */
	public void storeData(Collection<Topic> topicList,
			Collection<Question> questionList, Collection<Choice> choiceList) {
		Log.i("CommonDAL", "importData()");
		// ca 3 list phai !=null
		if (topicList != null && questionList != null && choiceList != null) {
			// unlock db
			db.setLockingEnabled(false);

			// begin Transaction
			db.beginTransaction();

			// ----------- TOPIC -------------
			// Create a single InsertHelper to handle this set of insertions.
			InsertHelper topicInsertHelper = new InsertHelper(db,
					DBHelper.TOPIC_TABLE_NAME);
			// Get the numeric indexes for each of the columns that we're
			// updating
			final int iTopicTitle = topicInsertHelper
					.getColumnIndex(DBHelper.TOPIC_TITLE);
			final int iCategory = topicInsertHelper
					.getColumnIndex(DBHelper.TOPIC_CATEGORY);
			final int iDescription = topicInsertHelper
					.getColumnIndex(DBHelper.TOPIC_DESCRIPTION);

			// ----------- QUESTION -------------
			// Create a single InsertHelper to handle this set of insertions.
			InsertHelper questionInsertHelper = new InsertHelper(db,
					DBHelper.QUESTION_TABLE_NAME);

			// Get the numeric indexes for each of the columns that we're
			// updating
			final int iQuestionTitle = questionInsertHelper
					.getColumnIndex(DBHelper.QUESTION_TITLE);
			final int iMulti = questionInsertHelper
					.getColumnIndex(DBHelper.QUESTION_IS_MULTI);
			final int iTopic = questionInsertHelper
					.getColumnIndex(DBHelper.QUESTION_TOPIC);

			// ----------- CHOICE -------------
			// Create a single InsertHelper to handle this set of insertions.
			InsertHelper choiceInsertHelper = new InsertHelper(db,
					DBHelper.CHOICE_TABLE_NAME);

			// Get the numeric indexes for each of the columns that we're
			// updating
			final int iChoiceTitle = choiceInsertHelper
					.getColumnIndex(DBHelper.CHOICE_TITLE);
			final int iISCORRECT = choiceInsertHelper
					.getColumnIndex(DBHelper.CHOICE_IS_CORRECT);
			final int iQUESTIONID = choiceInsertHelper
					.getColumnIndex(DBHelper.CHOICE_QUESTION_ID);

			int rowsTopic = 0;
			int rowsQuestion = 0;
			int rowsChoice = 0;
			long topicID = 0;
			long questionID = 0;
			try {
				for (Topic topic : topicList) {
					topicInsertHelper.prepareForInsert();
					topicInsertHelper.bind(iTopicTitle, topic.getTitle());
					topicInsertHelper
							.bind(iDescription, topic.getDescription());
					topicInsertHelper.bind(iCategory, topic.getCategory());

					// EXECUTE
					topicID = topicInsertHelper.execute();

					// neu Topic insert thanh cong thi tiep tuc insert Question
					if (topicID != -1) {
						rowsTopic++;
						for (Question question : questionList) {
							if (question.getTopic() == topic.getId()) {
								questionInsertHelper.prepareForInsert();
								questionInsertHelper.bind(iQuestionTitle,
										question.getTitle());
								questionInsertHelper.bind(iMulti,
										question.getIsMulti());
								questionInsertHelper.bind(iTopic, topicID);
								// EXECUTE
								questionID = questionInsertHelper.execute();
								// neu Question insert thanh cong thi tiep tuc
								// insert
								// Choice
								if (questionID != -1) {
									rowsQuestion++;
									for (Choice choice : choiceList) {
										if (choice.getQuestion_id() == question
												.getId()) {
											choiceInsertHelper
													.prepareForInsert();
											choiceInsertHelper.bind(
													iChoiceTitle,
													choice.getTitle());
											choiceInsertHelper.bind(iISCORRECT,
													choice.getIsCorrect());
											choiceInsertHelper.bind(
													iQUESTIONID, questionID);
											if (choiceInsertHelper.execute() != -1) {
												rowsChoice++;
											}
										}
									}

								}

							}
						}
					}
				}
				// COMMIT Transaction
				db.setTransactionSuccessful();
			} finally {
				// end Transaction
				db.endTransaction();
				// lock db as default
				db.setLockingEnabled(true);
				choiceInsertHelper.close();
				questionInsertHelper.close();
				topicInsertHelper.close();
				// db.close();
			}
			Log.i("CommonDAL", rowsTopic + "/" + topicList.size()
					+ " topics created");
			Log.i("CommonDAL", rowsQuestion + "/" + questionList.size()
					+ " questions created");
			Log.i("CommonDAL", rowsChoice + "/" + choiceList.size()
					+ " choices created");

		}
	}
}
