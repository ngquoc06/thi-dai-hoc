package com.quiz.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.quiz.R;
import com.quiz.database.DBHelper;
import com.quiz.entities.Choice;
import com.quiz.entities.Question;
import com.quiz.entities.Topic;

/**
 * @author LE Thanh Huy
 * 
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 * 
 */
public class XMLHelper {

	Collection<Topic> topicList;
	Collection<Question> questionList;
	Collection<Choice> choiceList;

	public XMLHelper() {
	}

	public Collection<Topic> getTopicList() {
		return topicList;
	}

	public void setTopicList(Collection<Topic> topicList) {
		this.topicList = topicList;
	}

	public Collection<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(Collection<Question> questionList) {
		this.questionList = questionList;
	}

	public Collection<Choice> getChoiceList() {
		return choiceList;
	}

	public void setChoiceList(Collection<Choice> choiceList) {
		this.choiceList = choiceList;
	}

	/**
	 * Import Question tu XML file trong res/xml/questions.xml Using
	 * XMLPullParser
	 * 
	 * @param activity
	 * @return danh sach Question
	 */
	public void readXML(Context ctx) {
		Collection<Topic> topicList = new ArrayList<Topic>();
		Collection<Question> questionList = new ArrayList<Question>();
		Collection<Choice> choiceList = new ArrayList<Choice>();

		XmlResourceParser xmlResourceParser = ctx.getResources().getXml(
				R.xml.questions);

		int eventType;
		Topic topic = null;
		Question question = null;
		Choice choice = null;
		int topicID = 0;
		int questionID = 0;
		try {
			do {
				xmlResourceParser.next();
				eventType = xmlResourceParser.getEventType();
				String name = xmlResourceParser.getName();

				if (eventType == XmlPullParser.START_TAG) {
					// khi gap the Choice
					if (DBHelper.CHOICE_XML_TAG_NAME.equals(name)) {
						choice = new Choice();
						choice.setQuestion_id(questionID);
						choice.setIsCorrect(xmlResourceParser
								.getAttributeIntValue(null,
										DBHelper.CHOICE_IS_CORRECT, 0));
					} else if (DBHelper.CHOICE_TITLE.equals(name)) {
						choice.setTitle(xmlResourceParser.nextText());
					}
					// khi gap Question
					else if (DBHelper.QUESTION_XML_TAG_NAME.equals(name)) {
						question = new Question();
						questionID += 1;
						question.setId(questionID);
						question.setTopic(topicID);
						question.setIsMulti(xmlResourceParser
								.getAttributeIntValue(null,
										DBHelper.QUESTION_IS_MULTI, 0));
					} else if (DBHelper.QUESTION_TITLE.equals(name)) {
						question.setTitle(xmlResourceParser.nextText());
					}
					// khi gap Topic
					else if (DBHelper.TOPIC_XML_TAG_NAME.equals(name)) {
						topic = new Topic();
						topicID += 1;
						topic.setId(topicID);
						topic.setCategory(xmlResourceParser.getAttributeValue(
								null, DBHelper.TOPIC_CATEGORY));
					} else if (DBHelper.TOPIC_TITLE.equals(name)) {
						topic.setTitle(xmlResourceParser.nextText());
					} else if (DBHelper.TOPIC_DESCRIPTION.equals(name)) {
						topic.setDescription(xmlResourceParser.nextText());
					}

				} else if (eventType == XmlPullParser.END_TAG) {
					if (DBHelper.CHOICE_XML_TAG_NAME.equals(name)) {
						choiceList.add(choice);
					} else if (DBHelper.QUESTION_XML_TAG_NAME.equals(name)) {
						questionList.add(question);
					} else if (DBHelper.TOPIC_XML_TAG_NAME.equals(name)) {
						topicList.add(topic);
					}
				}

			} while (eventType != XmlPullParser.END_DOCUMENT);

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// tra ve danh sach
		this.setChoiceList(choiceList);
		this.setQuestionList(questionList);
		this.setTopicList(topicList);
	}

	/**
	 * @return XMLReader ho tro parse XML using SAX
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public static XMLReader initializeReader()
			throws ParserConfigurationException, SAXException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// create a parser
		SAXParser parser = factory.newSAXParser();
		// create the reader (scanner)
		XMLReader xmlreader = parser.getXMLReader();
		return xmlreader;
	}

}
