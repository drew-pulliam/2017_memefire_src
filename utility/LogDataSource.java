package utility;

public interface LogDataSource {
	/**
	 * This is the only method that a LogDataSource needs to implement.
	 * A ValueLogger object will call this method for each LogDataSource registered with it.
	 * This method is expected to call one of the following ValueLogger methods for each value it wants to show.
	 *   logBooleanValue()
	 *   logDoubleValue()
	 *   logIntValue()
	 *   logStringValue()
	 * The ValueLogger object will take care of getting the value into the dump file, the Smart Dashboard, or both.
	 * It is important that this method always logs the same set of values in every call, since the first call
	 * is used to set column headers in the dump file, and if subsequent calls log a different set of values, the
	 * column headers won't line up properly.
	 * 
	 * @param logger - This is the ValueLogger object on which the methods listed above need to be called.
	 */
	abstract void gatherValues(ValueLogger logger);
}
