package com.quiz.entities;

import java.util.Collection;

public class Exam {
	private int id; // = score id
	private String topic;
	private String takenDate;
	private int scoreId = 0; // scoreId = 0 => chua thi xong
	private Collection<Question> questionList;

	public Exam() {
	}

	public Exam(int id, String topic, String takenDate, int scoreId) {
		this.id = id;
		this.topic = topic;
		this.takenDate = takenDate;
		this.scoreId = scoreId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTakenDate() {
		return takenDate;
	}

	public void setTakenDate(String takenDate) {
		this.takenDate = takenDate;
	}

	public int getScoreId() {
		return scoreId;
	}

	public void setScoreId(int scoreId) {
		this.scoreId = scoreId;
	}

	public Collection<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(Collection<Question> questionList) {
		this.questionList = questionList;
	}

}
