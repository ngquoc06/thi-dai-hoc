package com.quiz.entities;

public class QuestionExam {
	private int id;
	private int questionId;
	private int examId;

	public QuestionExam() {
	}

	public QuestionExam(int id, int questionId, int examId) {
		this.id = id;
		this.questionId = questionId;
		this.examId = examId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

}
