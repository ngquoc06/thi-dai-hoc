package com.quiz.xml;

import java.util.ArrayList;
import java.util.Collection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.quiz.database.DBHelper;
import com.quiz.entities.Choice;
import com.quiz.entities.Question;
import com.quiz.entities.Topic;

public class QuestionXMLHandler extends DefaultHandler {

	Collection<Topic> topicList = null;
	Collection<Question> questionList = null;
	Collection<Choice> choiceList = null;

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

	private StringBuffer buffer = new StringBuffer();
	private Topic topic = null;
	private Question question = null;
	private Choice choice = null;
	private int topicID = 0;
	private int questionID = 0;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// reset Buffer
		buffer.setLength(0);

		// khi gap Choice
		if (DBHelper.CHOICE_XML_TAG_NAME.equals(localName)) {
			choice = new Choice();
			choice.setQuestion_id(questionID);
			choice.setIsCorrect(Integer.valueOf(attributes
					.getValue(DBHelper.CHOICE_IS_CORRECT)));
		}
		// khi gap Question
		else if (DBHelper.QUESTION_XML_TAG_NAME.equals(localName)) {
			question = new Question();
			questionID += 1;
			question.setId(questionID);
			question.setTopic(topicID);
			question.setIsMulti(Integer.valueOf(attributes
					.getValue(DBHelper.QUESTION_IS_MULTI)));
		}
		// khi gap Topic
		else if (DBHelper.TOPIC_XML_TAG_NAME.equals(localName)) {
			topic = new Topic();
			topicID += 1;
			topic.setId(topicID);
			topic.setCategory(attributes.getValue(DBHelper.TOPIC_CATEGORY));
		}

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		buffer.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (DBHelper.CHOICE_TITLE.equals(localName)) {
			choice.setTitle(buffer.toString());
		} else if (DBHelper.CHOICE_XML_TAG_NAME.equals(localName)) {
			choiceList.add(choice);
		} else if (DBHelper.QUESTION_TITLE.equals(localName)) {
			question.setTitle(buffer.toString());
		} else if (DBHelper.QUESTION_XML_TAG_NAME.equals(localName)) {
			questionList.add(question);
		} else if (DBHelper.TOPIC_TITLE.equals(localName)) {
			topic.setTitle(buffer.toString());
		} else if (DBHelper.TOPIC_DESCRIPTION.equals(localName)) {
			topic.setDescription(buffer.toString());
		} else if (DBHelper.TOPIC_XML_TAG_NAME.equals(localName)) {
			topicList.add(topic);
		}
	}

	@Override
	public void startDocument() throws SAXException {
		topicList = new ArrayList<Topic>();
		questionList = new ArrayList<Question>();
		choiceList = new ArrayList<Choice>();
	}

	@Override
	public void endDocument() throws SAXException {
		// tra ve danh sach
		this.setChoiceList(choiceList);
		this.setQuestionList(questionList);
		this.setTopicList(topicList);
	}

}
