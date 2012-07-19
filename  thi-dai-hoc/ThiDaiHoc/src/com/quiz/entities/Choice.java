package com.quiz.entities;

public class Choice {
	private int id;
	private String title;
	private int isCorrect;
	private int question_id = 0;

	/**
	 * 
	 */
	public Choice() {
		super();
	}

	/**
	 * @param id
	 * @param title
	 * @param isCorrect
	 * @param question_id
	 */
	public Choice(int id, String title, int isCorrect, int question_id) {
		super();
		this.id = id;
		this.title = title;
		this.isCorrect = isCorrect;
		this.question_id = question_id;
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
	 * @return the isCorrect
	 */
	public int getIsCorrect() {
		return isCorrect;
	}

	/**
	 * @param isCorrect
	 *            the isCorrect to set
	 */
	public void setIsCorrect(int isCorrect) {
		this.isCorrect = isCorrect;
	}

	/**
	 * @return the question_id
	 */
	public int getQuestion_id() {
		return question_id;
	}

	/**
	 * @param question_id
	 *            the question_id to set
	 */
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

}
