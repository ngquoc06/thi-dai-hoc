package com.quiz.entities;

import java.util.Locale;

public class Setting {
	private int questions = 20;
	private int seconds = 20;
	private boolean timer = true;
	private int passedScore = 40; // 40%
	private int historySize = 5;
	private String language = "vi";

	public Setting() {
	}

	public int getQuestions() {
		return questions;
	}

	public void setQuestions(int questions) {
		this.questions = questions;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public boolean getTimer() {
		return timer;
	}

	public void setTimer(boolean timer) {
		this.timer = timer;
	}

	public int getPassedScore() {
		return passedScore;
	}

	public void setPassedScore(int passedScore) {
		this.passedScore = passedScore;
	}

	public int getHistorySize() {
		return historySize;
	}

	public void setHistorySize(int historySize) {
		this.historySize = historySize;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Locale getLocale() {
		if (this.language.equals("en")) {
			return Locale.US;
		} else if (this.language.equals("vi")) {
			return new Locale("vi", "VN");
		} else if (this.language.equals("fr")) {
			return Locale.FRANCE;
		} else {
			return Locale.US;// normal
		}
	}
}
