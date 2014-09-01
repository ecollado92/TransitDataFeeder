/**
 * 
 */
package org.ideaproject.util;

import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class HashCodeUtilTest {
	private final static int HASH_CODE_TRUE = HashCodeUtil.hash(HashCodeUtil.SEED, true);
	private final static int HASH_CODE_FALSE = HashCodeUtil.hash(HashCodeUtil.SEED, false);

	private final static int HASH_CODE_A = HashCodeUtil.hash(HashCodeUtil.SEED, 'a');
	private final static int HASH_CODE_HYPHEN = HashCodeUtil.hash(HashCodeUtil.SEED, '-');

	private final static int HASH_CODE_INT_MIN = HashCodeUtil.hash(HashCodeUtil.SEED, Integer.MIN_VALUE);
	private final static int HASH_CODE_INT_MAX = HashCodeUtil.hash(HashCodeUtil.SEED, Integer.MAX_VALUE);

	private final static int HASH_CODE_LONG_MIN = HashCodeUtil.hash(HashCodeUtil.SEED, Long.MIN_VALUE);
	private final static int HASH_CODE_LONG_MAX = HashCodeUtil.hash(HashCodeUtil.SEED, Long.MAX_VALUE);

	private final static int HASH_CODE_DBL_MIN = HashCodeUtil.hash(HashCodeUtil.SEED, Double.MIN_VALUE);
	private final static int HASH_CODE_DBL_MAX = HashCodeUtil.hash(HashCodeUtil.SEED, Double.MAX_VALUE);

	private final static int HASH_CODE_FLOAT_MIN = HashCodeUtil.hash(HashCodeUtil.SEED, Float.MIN_VALUE);
	private final static int HASH_CODE_FLOAT_MAX = HashCodeUtil.hash(HashCodeUtil.SEED, Float.MAX_VALUE);

	@Test
	public void testHash_int_boolean() throws Exception {
		int hashResult = HashCodeUtil.hash(HashCodeUtil.SEED, true);
		assert hashResult == HASH_CODE_TRUE;
		assert hashResult != HASH_CODE_FALSE;

		hashResult = HashCodeUtil.hash(HashCodeUtil.SEED, false);
		assert hashResult != HASH_CODE_TRUE;
		assert hashResult == HASH_CODE_FALSE;

		hashResult = HashCodeUtil.hash(4444, true);
		assert hashResult != HASH_CODE_TRUE;
		assert hashResult != HASH_CODE_FALSE;
	}

	@Test
	public void testHash_int_char() throws Exception {
		int hashResult = HashCodeUtil.hash(HashCodeUtil.SEED, 'a');
		assert hashResult == HASH_CODE_A;
		assert hashResult != HASH_CODE_HYPHEN;

		hashResult = HashCodeUtil.hash(HashCodeUtil.SEED, '-');
		assert hashResult != HASH_CODE_A;
		assert hashResult == HASH_CODE_HYPHEN;

		hashResult = HashCodeUtil.hash(4444, 'a');
		assert hashResult != HASH_CODE_A;
		assert hashResult != HASH_CODE_HYPHEN;
	}

	@Test
	public void testHash_int_int() throws Exception {
		int hashResult = HashCodeUtil.hash(HashCodeUtil.SEED, Integer.MIN_VALUE);
		assert hashResult == HASH_CODE_INT_MIN;
		assert hashResult != HASH_CODE_INT_MAX;

		hashResult = HashCodeUtil.hash(HashCodeUtil.SEED, Integer.MAX_VALUE);
		assert hashResult != HASH_CODE_INT_MIN;
		assert hashResult == HASH_CODE_INT_MAX;

		hashResult = HashCodeUtil.hash(4444, Integer.MIN_VALUE);
		assert hashResult != HASH_CODE_INT_MIN;
		assert hashResult != HASH_CODE_INT_MAX;
	}

	@Test
	public void testHash_int_long() throws Exception {
		int hashResult = HashCodeUtil.hash(HashCodeUtil.SEED, Long.MIN_VALUE);
		assert hashResult == HASH_CODE_LONG_MIN;
		assert hashResult != HASH_CODE_LONG_MAX;

		hashResult = HashCodeUtil.hash(HashCodeUtil.SEED, Long.MAX_VALUE);
		assert hashResult != HASH_CODE_LONG_MIN;
		assert hashResult == HASH_CODE_LONG_MAX;

		hashResult = HashCodeUtil.hash(4444, Long.MIN_VALUE);
		assert hashResult != HASH_CODE_LONG_MIN;
		assert hashResult != HASH_CODE_LONG_MAX;
	}

	@Test
	public void testHash_int_double() throws Exception {
		int hashResult = HashCodeUtil.hash(HashCodeUtil.SEED, Double.MIN_VALUE);
		assert hashResult == HASH_CODE_DBL_MIN;
		assert hashResult != HASH_CODE_DBL_MAX;

		hashResult = HashCodeUtil.hash(HashCodeUtil.SEED, Double.MAX_VALUE);
		assert hashResult != HASH_CODE_DBL_MIN;
		assert hashResult == HASH_CODE_DBL_MAX;

		hashResult = HashCodeUtil.hash(4444, Double.MIN_VALUE);
		assert hashResult != HASH_CODE_DBL_MIN;
		assert hashResult != HASH_CODE_DBL_MAX;
	}

	@Test
	public void testHash_int_float() throws Exception {
		int hashResult = HashCodeUtil.hash(HashCodeUtil.SEED, Float.MIN_VALUE);
		assert hashResult == HASH_CODE_FLOAT_MIN;
		assert hashResult != HASH_CODE_FLOAT_MAX;

		hashResult = HashCodeUtil.hash(HashCodeUtil.SEED, Float.MAX_VALUE);
		assert hashResult != HASH_CODE_FLOAT_MIN;
		assert hashResult == HASH_CODE_FLOAT_MAX;

		hashResult = HashCodeUtil.hash(4444, Float.MIN_VALUE);
		assert hashResult != HASH_CODE_FLOAT_MIN;
		assert hashResult != HASH_CODE_FLOAT_MAX;
	}

}
