/**
 * 
 */
package org.ideaproject.util;

import java.util.Calendar;
import java.util.Date;

import org.ideaproject.Constants;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class StringUtilTest {
	private final static String NULL_STRING = null;
	private final static String EMPTY_STRING = "";
	private final static String NONEMPTY_STRING = "nonempty";

	private static final int NOW_YEAR = 2010;
	private static final int NOW_MONTH = Calendar.APRIL;
	private static final int NOW_DOM = 15;
	private static final int NOW_HOUR = 10;
	private static final int NOW_MINUTE = 23;
	private static final int NOW_SECOND = 34;

	private Date now;

	@BeforeClass
	public void setUp() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, NOW_MONTH);
		cal.set(Calendar.YEAR, NOW_YEAR);
		cal.set(Calendar.DAY_OF_MONTH, NOW_DOM);
		cal.set(Calendar.HOUR, NOW_HOUR);
		cal.set(Calendar.MINUTE, NOW_MINUTE);
		cal.set(Calendar.SECOND, NOW_SECOND);
		cal.set(Calendar.AM_PM, Calendar.AM);
		now = cal.getTime();

	}

	@Test
	public void testIsEmpty() throws Exception {
		assert StringUtil.isEmpty(NULL_STRING);
		assert StringUtil.isEmpty(EMPTY_STRING);
		assert !StringUtil.isEmpty(NONEMPTY_STRING);
	}

	@Test
	public void testFormatTimeValue_Date() throws Exception {
		String result = StringUtil.formatTimeValue(now);
		String[] parts = result.split(":");

		assert parts.length == 3;
		assert Integer.parseInt(parts[0]) == NOW_HOUR;
		assert Integer.parseInt(parts[1]) == NOW_MINUTE;
		assert Integer.parseInt(parts[2]) == NOW_SECOND;

		result = StringUtil.formatTimeValue(null);
		assert result.equals(Constants.DEFAULT_TIME_ENTRY);
	}

	@Test
	public void testFormatTimeValue_Date_boolean() throws Exception {
		String result1 = StringUtil.formatTimeValue(now);
		String result2 = StringUtil.formatTimeValue(now, false);
		assert result1.equals(result2);

		result1 = StringUtil.formatTimeValue(now, true);
		String[] parts = result1.split(":");
		assert parts.length == 3;
		assert Integer.parseInt(parts[0]) == NOW_HOUR + Constants.HOURS_IN_DAY;
		assert Integer.parseInt(parts[1]) == NOW_MINUTE;
		assert Integer.parseInt(parts[2]) == NOW_SECOND;
	}
}
