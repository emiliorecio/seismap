package com.seismap.service.parser;

import com.seismap.service.parser.annotation.BooleanField;
import com.seismap.service.parser.annotation.ConstantField;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.EnumeratedField;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.IntegerField;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;
import com.seismap.service.parser.enumeration.ActionIndicator;
import com.seismap.service.parser.enumeration.ActionIndicatorText;

@Entry(id = "I", after = { "1", "2", "3", "4", "E", "F" }, whitespaces = {
		@Whitespace(position = 1, length = 1),
		@Whitespace(position = 12, length = 1),
		@Whitespace(position = 21, length = 1),
		@Whitespace(position = 27, length = 1),
		@Whitespace(position = 35, length = 1),
		@Whitespace(position = 57, length = 1),
		@Whitespace(position = 77, length = 3) })
public class TypeIEntry extends AbstractEntry {

	@EnumeratedField(position = 2, length = 7, mappings = {
			@EnumerationMapping(value = "ACTION:", mapsTo = "ACTION"),
			@EnumerationMapping(value = "OLDACT:", mapsTo = "OLD_ACTION") })
	private ActionIndicatorText actionIndicatorText;

	@EnumeratedField(position = 9, length = 3, mappings = {
			@EnumerationMapping(value = "SPL", mapsTo = "SPLIT"),
			@EnumerationMapping(value = "REG", mapsTo = "REGISTER"),
			@EnumerationMapping(value = "ARG", mapsTo = "AUTO_REGISTER"),
			@EnumerationMapping(value = "UPD", mapsTo = "UPDATE"),
			@EnumerationMapping(value = "UP ", mapsTo = "UPDATE_ONLY_FROM_EEV"),
			@EnumerationMapping(value = "REE", mapsTo = "REGISTER_FROM_EEV"),
			@EnumerationMapping(value = "DUP", mapsTo = "DUPLICATED_EVENT"),
			@EnumerationMapping(value = "NEW", mapsTo = "NEW_EVENT") })
	private ActionIndicator lastAction;

	@IntegerField(position = 13, digits = 2)
	private int year;

	@ConstantField(position = 15)
	static final String YEAR_MOTH_DELIMITER = "-";

	@IntegerField(position = 16, digits = 2)
	private int month;

	@ConstantField(position = 18)
	static final String MONTH_DAY_DELIMITER = "-";

	@IntegerField(position = 19, digits = 2)
	private int day;

	@IntegerField(position = 22, digits = 2)
	private int hour;

	@ConstantField(position = 24)
	static final String HOUR_MINUTE_DELIMITER = ":";

	@IntegerField(position = 25, digits = 2)
	private int minute;

	@ConstantField(position = 28)
	static final String OPERATOR_CODE_TEXT = "OP:";

	@StringField(position = 31, length = 4)
	private String operatorCode;

	@ConstantField(position = 36)
	static final String STATUS_FLAGS_TEXT = "STATUS:";

	@StringField(position = 43, length = 14)
	private String statusFlags;

	@ConstantField(position = 58)
	static final String IDS_TEXT = "ID:";

	@IntegerField(position = 61, digits = 14)
	private long id;

	@BooleanField(position = 75, off = ' ', on = 'd')
	private boolean differentFile;

	@BooleanField(position = 76, off = ' ', on = 'L')
	private boolean idLocked;

	public ActionIndicatorText getActionIndicatorText() {
		return actionIndicatorText;
	}

	public ActionIndicator getLastAction() {
		return lastAction;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public String getStatusFlags() {
		return statusFlags;
	}

	public long getId() {
		return id;
	}

	public boolean isDifferentFile() {
		return differentFile;
	}

	public boolean isIdLocked() {
		return idLocked;
	}

}
