package com.thidaihoc.view;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

import com.thidaihoc.R;
import com.thidaihoc.TopicCoverActivity;

public class CustomAdapter extends BaseAdapter {
	private final Context mContext;
	private final ArrayList<String> mItems;
	Button btn;

	public CustomAdapter(Context c, ArrayList<String> items) {
		this.mContext = c;
		this.mItems = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return mItems.get(index);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int topicID = position + 1;
		View view;
		if (convertView == null) {
			btn = new Button(mContext);
			Drawable icon = null;
			btn.setLayoutParams(new LayoutParams(
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			LayoutInflater li = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = li.inflate(R.layout.grid_item, null);
			btn = (Button) view.findViewById(R.id.gridItem_btn);
			switch (position) {
			case 0:
				btn.setId(position);
				icon = mContext.getResources().getDrawable(R.drawable.btn_topic_ly);
				btn.setText(mContext.getResources().getText(
						R.string.topic_ly_khoiA_2012));
				break;
			case 1:
				icon = mContext.getResources().getDrawable(R.drawable.btn_topic_sinh);
				btn.setText(mContext.getResources().getText(
						R.string.topic_sinh_khoiB_2012));
				btn.setId(position);
				break;
			case 2:
				icon = mContext.getResources().getDrawable(R.drawable.btn_topic_hoa);
				btn.setText(mContext.getResources().getText(
						R.string.topic_hoa_khoiA_2012));
				btn.setId(position);
				break;
			case 3:
				icon = mContext.getResources().getDrawable(R.drawable.btn_topic_english);
				btn.setText(mContext.getResources().getText(
						R.string.topic_english_khoiA1_2012));
				btn.setId(position);
				break;
			}
			btn.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
			btn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext,
							TopicCoverActivity.class);
					intent.putExtra("topic_id", topicID);
					mContext.startActivity(intent);
				}
			});
		} else {
			view = convertView;
		}
		return view;
	}

}
