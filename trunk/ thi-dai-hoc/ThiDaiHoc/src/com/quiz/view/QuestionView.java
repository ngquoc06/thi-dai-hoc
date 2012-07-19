package com.quiz.view;

import java.util.Collection;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.quiz.Main;
import com.quiz.R;
import com.quiz.entities.Choice;
import com.quiz.entities.Question;

public class QuestionView extends LinearLayout {

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	Question question;
	TextView questionTitle;
	LinearLayout.LayoutParams pl = new LayoutParams(
			android.view.ViewGroup.LayoutParams.FILL_PARENT,
			android.view.ViewGroup.LayoutParams.FILL_PARENT);

	public QuestionView(Context context, Question question, int index,
			int noOfQuestions, boolean flagReview) {
		super(context);
		this.setQuestion(question);
		this.setOrientation(VERTICAL);
		this.setLayoutParams(pl);
		questionTitle = new TextView(context);
		questionTitle.setTextColor(Color.WHITE);
		questionTitle.setTextSize(16);
		questionTitle.setText(Html.fromHtml("[" + index + "/" + noOfQuestions
				+ "] " + question.getTitle().trim()));
		this.addView(questionTitle);
		Collection<Choice> choiceList = Main.getChoiceDAL()
				.getChoicesByQuestion(question.getId());
		if (question.getIsMulti() == 1) {
			// hien thi cac checkbox
			int id = 1;
			for (Choice choice : choiceList) {
				CheckBox checkbox = new CheckBox(context);
				checkbox.setTextSize(14);
				checkbox.setButtonDrawable(R.drawable.customcheckbox);
				checkbox.setId(id++);
				checkbox.setTag(choice);
				checkbox.setText(Html.fromHtml(choice.getTitle().trim()));
				checkbox.setTextColor(Color.WHITE);
				this.addView(checkbox);
			}
		} else {
			RadioGroup groupRadio = new RadioGroup(context);
			groupRadio.setId(2012);
			int id = 1;
			// hien thi cac radiobutton
			for (Choice choice : choiceList) {
				RadioButton radio = new RadioButton(context);
				radio.setTextColor(Color.WHITE);
				radio.setTextSize(14);
				radio.setButtonDrawable(R.drawable.customradio);
				radio.setId(id++);
				radio.setTag(choice);
				radio.setText(Html.fromHtml(choice.getTitle().trim()));
				groupRadio.addView(radio);
			}
			// add RadioGroup to View
			this.addView(groupRadio);
		}

		if (choiceList != null) {
			choiceList.clear();
			choiceList = null;
		}
	}

}
