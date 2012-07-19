package com.quiz.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Topic implements Parcelable {
	private int id;
	private String title;
	private String description;
	private String category;

	/**
	 * 
	 */
	public Topic() {
	}

	/* BEGIN cho phan Parcel */
	private Topic(Parcel in) {
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void readFromParcel(Parcel in) {
		this.setId(in.readInt());
		this.setTitle(in.readString());
		this.setDescription(in.readString());
		this.setCategory(in.readString());
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(this.getId());
		out.writeString(this.getTitle());
		out.writeString(this.getDescription());
		out.writeString(this.getCategory());
	}

	public static final Parcelable.Creator<Topic> CREATOR = new Creator<Topic>() {

		@Override
		public Topic[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Topic[size];
		}

		@Override
		public Topic createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Topic(source);
		}
	};

	/* END Parcel */

	/**
	 * @param id
	 * @param title
	 * @param description
	 * @param category
	 */
	public Topic(int id, String title, String description, String category) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.category = category;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

}
