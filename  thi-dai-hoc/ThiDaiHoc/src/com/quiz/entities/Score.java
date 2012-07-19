package com.quiz.entities;

import com.quiz.utils.ConverterHelper;

public class Score {
	private int id;
	private String username = "<unknown>";
	private int topic;
	private String topicTitle = "<unknown>";
	private String time;
	private int duration;
	private int unanswered; // so cau chua tra loi
	private int correct; // so cau tra loi dung
	private int noOfQuestions; // tong so cau hoi
	private int passedScore;
	private int examId;

	public Score() {
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public int getPassedScore() {
		return passedScore;
	}

	public void setPassedScore(int passedScore) {
		this.passedScore = passedScore;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getTopic() {
		return topic;
	}

	public void setTopic(int topic) {
		this.topic = topic;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getUnanswered() {
		return unanswered;
	}

	public void setUnanswered(int unanswered) {
		this.unanswered = unanswered;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public int getNoOfQuestions() {
		return noOfQuestions;
	}

	public void setNoOfQuestions(int noOfQuestions) {
		this.noOfQuestions = noOfQuestions;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getDurationFormat() {
		return ConverterHelper.getFormatResultDuration(duration);
	}

	// hien thi % so diem da duoc lam trong 2 digits
	public double getTotal() {
		if (this.noOfQuestions == 0)
			return -1;
		else
			return ((double) (10000 * this.correct / this.noOfQuestions) / 100);
	}
}
