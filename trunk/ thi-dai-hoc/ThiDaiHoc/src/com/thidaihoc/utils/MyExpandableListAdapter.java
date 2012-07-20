package com.thidaihoc.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private MyListItem[] groups;
	private MyListItem[][] children;

	public MyExpandableListAdapter() {
		// TODO Auto-generated constructor stub
	}

	public MyExpandableListAdapter(Context context, MyListItem[] groups,
			MyListItem[][] children) {
		this.context = context;
		this.groups = groups;
		this.children = children;
	}

	public ViewGroup getGenericView() {

		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		ViewGroup viewGroup = new ViewGroup(this.context) {

			@Override
			protected void onLayout(boolean changed, int l, int t, int r, int b) {
				// TODO Auto-generated method stub

			}
		};
		viewGroup.setLayoutParams(lp);

		return viewGroup;
	}

	@Override
	public MyListItem getGroup(int groupPosition) {
		return groups[groupPosition];
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public int getGroupCount() {
		return groups.length;
	}

	@Override
	public ViewGroup getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		ViewGroup viewGroup = getGroup(groupPosition).getViewGroup();

		return viewGroup;
	}

	@Override
	public MyListItem getChild(int groupPosition, int childPosition) {
		return children[groupPosition][childPosition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return children[groupPosition].length;
	}

	@Override
	public ViewGroup getChildView(int groupPosition, int childPosition,
			boolean isExpanded, View convertView, ViewGroup parent) {

		ViewGroup viewGroup = getChild(groupPosition, childPosition)
				.getViewGroup();

		return viewGroup;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

}
