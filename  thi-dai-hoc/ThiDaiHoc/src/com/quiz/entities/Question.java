package com.quiz.entities;

import java.util.Collection;

import com.quiz.Main;

public class Question {

	private int id;
	private String title;
	private int isMulti = 1;
	private int topic = 0;

	public Question() {
	}

	/**
	 * @param id
	 * @param title
	 * @param isTrueFalse
	 * @param isMulti
	 * @param isActive
	 * @param topic
	 */
	public Question(int id, String title, int isMulti, int topic) {
		this.id = id;
		this.title = title;
		this.isMulti = isMulti;
		this.topic = topic;
	}

	/**
	 * Constructor khong co id - vi id trong DB la auto-increment
	 * 
	 * @param title
	 * @param isTrueFalse
	 * @param isMulti
	 * @param isActive
	 * @param topic
	 */
	public Question(String title, int isMulti, int topic) {
		super();
		this.title = title;
		this.isMulti = isMulti;
		this.topic = topic;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the isMulti
	 */
	public int getIsMulti() {
		return isMulti;
	}

	/**
	 * @param isMulti
	 *            the isMulti to set
	 */
	public void setIsMulti(int isMulti) {
		this.isMulti = isMulti;
	}

	/**
	 * @return the topic
	 */
	public int getTopic() {
		return topic;
	}

	/**
	 * @param topic
	 *            the topic to set
	 */
	public void setTopic(int topic) {
		this.topic = topic;
	}

	public Collection<Choice> getChoices() {
		return Main.getChoiceDAL().getChoicesByQuestion(this.getId());
	}
}
