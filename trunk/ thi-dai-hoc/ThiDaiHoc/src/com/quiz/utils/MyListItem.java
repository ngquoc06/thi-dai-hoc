package com.quiz.utils;

import android.content.Context;
import android.view.ViewGroup;

public class MyListItem {

	private Context context;
	private ViewGroup viewGroup;
	private int viewGroupId;

	public MyListItem() {

	}

	public MyListItem(Context context, ViewGroup viewGroup) {
		this.context = context;
		this.viewGroup = viewGroup;
	}

	public MyListItem(Context context, int viewGroupId) {
		this.context = context;
		this.viewGroupId = viewGroupId;
	}

	public Context getContext() {
		return this.context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public ViewGroup getViewGroup() {
		return this.viewGroup;
	}

	public void setViewGroup(ViewGroup viewGroup) {
		this.viewGroup = viewGroup;
	}

	public int getViewGroupId() {
		return this.viewGroupId;
	}

	public void setViewGroupId(int viewGroupId) {
		this.viewGroupId = viewGroupId;
	}

}
