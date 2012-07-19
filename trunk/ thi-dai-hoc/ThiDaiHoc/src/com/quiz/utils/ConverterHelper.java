package com.quiz.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * @author Huy Class cung cap cac method tien ich nhu convert(long time) >
 *         format nao do
 */
public class ConverterHelper {

	/**
	 * Chuyen doi tap gia tri thanh String e.g: [1,4,6,7] => "(1,4,6,7)" su dung
	 * trong SQLite truy van SELECT .... WHERE rowid IN (1,4,6,7)
	 * 
	 * @param values
	 * @return
	 */
	public static String convertMultiValues2String(Collection<Integer> values) {
		StringBuffer buff = new StringBuffer();

		buff.append("(");

		Iterator<Integer> iterator = values.iterator();
		if (iterator.hasNext()) {
			buff.append(iterator.next());
		}
		while (iterator.hasNext()) {
			buff.append(",");
			buff.append(iterator.next());
		}

		buff.append(")");

		return buff.toString();
	}

	/**
	 * @return lay ngay hien hanh theo format yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * @return lay ngay hien hanh theo long
	 */
	public static long getCurrentDateTimeInLong() {
		Date date = new Date();
		return date.getTime();
	}

	/**
	 * @param duration
	 * @return chuyen duration theo dinh dang: 8h12m20s
	 */
	public static String getFormatResultDuration(long duration) {
		int hour = (int) (duration / 3600);
		int min = (int) ((duration % 3600) / 60);
		int sec = (int) ((duration % 3600) % 60);
		StringBuffer buffer = new StringBuffer();

		if (hour > 0) {
			buffer.append(hour);
			buffer.append("h ");
		}

		if (min > 0) {
			buffer.append(min);
			buffer.append("m ");
		}

		if (sec > 0) {
			buffer.append(sec);
			buffer.append("s");
		}

		return buffer.toString();
	}

	/**
	 * @param duration
	 * @return chuyen durationo theo dinh dang HH:mm:ss
	 */
	public static String getFormatCountDownTimerDuration(long seconds) {
		int hour = (int) (seconds / 3600);
		int min = (int) (seconds / 60);
		int sec = (int) (seconds % 60);
		StringBuffer buffer = new StringBuffer();

		if (hour > 0) {
			if (hour < 10) {
				buffer.append("0");
			}
			buffer.append(hour);
			buffer.append(":");
		}

		if (min < 10) {
			buffer.append("0");
		}
		buffer.append(min);
		buffer.append(":");

		if (sec < 10) {
			buffer.append("0");
		}
		buffer.append(sec);

		return buffer.toString();
	}

}
