package com.quiz.utils;

import android.content.Context;

/**
 * 
 * @author long Doi tuong gom ImageView + TextView(Title) + TextView(Text) su
 *         dung cho CustomItemAdapter
 */
public class CustomItem {

	private Context context;
	private int iconId = 0;
	private int textId = 0;

	public CustomItem() {

	}

	/**
	 * constructor for Item full with Icon, Title and Text content
	 * 
	 * @param context
	 * @param iconId
	 * @param textId
	 */
	public CustomItem(Context context, int iconId, int textId) {
		this.context = context;
		this.iconId = iconId;
		this.textId = textId;
	}

	public Context getContext() {
		return this.context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public int getIconId() {
		return this.iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public int getTextId() {
		return this.textId;
	}

	public void setTextId(int textId) {
		this.textId = textId;
	}

}
