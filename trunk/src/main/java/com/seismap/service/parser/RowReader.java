package com.seismap.service.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.seismap.service.parser.annotation.BooleanField;
import com.seismap.service.parser.annotation.CharacterField;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.EnumeratedField;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.FloatField;
import com.seismap.service.parser.annotation.IntegerField;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;

public class RowReader {

	private abstract class FieldReader {
		private Field field;
		private int startColumn;
		private int endColumn;

		public FieldReader(Field field, int position, int length,
				Field[] usedPositions) {
			markUsed(field, position, length, usedPositions);
			this.field = field;
			this.startColumn = position - 1;
			this.endColumn = position + length - 1;
		}

		public void read(AbstractEntry entry, int line, String content)
				throws InvalidDataException {
			String value = content.substring(startColumn, endColumn);
			Object object = read(line, startColumn, endColumn, value);
			try {
				field.setAccessible(true);
				field.set(entry, object);
			} catch (IllegalArgumentException e) {
				throw new EntryDefinitionException(e);
			} catch (IllegalAccessException e) {
				throw new EntryDefinitionException(e);
			}
		}

		abstract Object read(int line, int startColumn, int endColumn,
				String value) throws InvalidDataException;
	}

	private static final int ROW_LENGTH = 79;
	private char typeCharacter;
	private FieldReader[] fieldReaders;
	private Class<? extends AbstractEntry> entryClass;
	private boolean[] whiteSpacePositions;

	public RowReader(Class<? extends AbstractEntry> entryClass) {
		Entry entry = entryClass.getAnnotation(Entry.class);
		if (entry == null) {
			throw new EntryDefinitionException("Missing @Entry annotation");
		}
		this.entryClass = entryClass;
		this.typeCharacter = entry.value();
		Field[] fields = entryClass.getDeclaredFields();
		Field[] usedPositions = new Field[ROW_LENGTH];
		fieldReaders = new FieldReader[fields.length];
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			Annotation[] annotations = field.getAnnotations();
			if (annotations.length == 0) {
				throw new EntryDefinitionException(
						"Missing column annotation for" + entryClass.getName()
								+ "." + field.getName());
			}
			if (annotations.length > 1) {
				throw new EntryDefinitionException(
						"Two many column annotations for"
								+ entryClass.getName() + "." + field.getName());
			}
			Annotation annotation = annotations[0];
			if (annotation instanceof BooleanField) {
				processField(i, field, (BooleanField) annotation, usedPositions);
			}
			if (annotation instanceof CharacterField) {
				processField(i, field, (CharacterField) annotation,
						usedPositions);
			}
			if (annotation instanceof EnumeratedField) {
				processField(i, field, (EnumeratedField) annotation,
						usedPositions);
			}
			if (annotation instanceof FloatField) {
				processField(i, field, (FloatField) annotation, usedPositions);
			}
			if (annotation instanceof IntegerField) {
				processField(i, field, (IntegerField) annotation, usedPositions);
			}
			if (annotation instanceof StringField) {
				processField(i, field, (StringField) annotation, usedPositions);
			}
		}
		whiteSpacePositions = new boolean[ROW_LENGTH];
		markWhitespaces(entryClass, entry.whitespaces(), usedPositions);
		for (int i = 1; i <= ROW_LENGTH; i++) {
			if (usedPositions[i - 1] == null && !whiteSpacePositions[i - 1]) {
				throw new EntryDefinitionException(
						"Position "
								+ i
								+ " not used for a field and not marked as whitespace: "
								+ entryClass.getName());
			}
		}
	}

	private void markWhitespace(Class<? extends AbstractEntry> entryClass,
			Whitespace whitespace, Field[] usedPositions) {
		if (whitespace.position() < 1) {
			throw new EntryDefinitionException(
					"Whitespace position cannot be less than one: "
							+ entryClass.getName());
		}
		if (whitespace.length() < 1) {
			throw new EntryDefinitionException(
					"Whitespace length cannot be less than one: "
							+ entryClass.getName());
		}
		if (whitespace.position() + whitespace.length() > ROW_LENGTH + 1) {
			throw new EntryDefinitionException(
					"Whitespace exceeds the row length limit of " + ROW_LENGTH
							+ " characters: " + entryClass.getName());
		}
		for (int i = whitespace.position(); i < whitespace.position()
				+ whitespace.length(); i++) {
			if (usedPositions[i - 1] != null) {
				throw new EntryDefinitionException("Whitespace position " + i
						+ " already used by field "
						+ usedPositions[i - 1].getName() + ": "
						+ entryClass.getName());
			} else if (whiteSpacePositions[i - 1]) {
				throw new EntryDefinitionException("Whitespace position " + i
						+ " already marked as whitespace: "
						+ entryClass.getName());
			} else {
				whiteSpacePositions[i - 1] = true;
			}

		}
	}

	private void markWhitespaces(Class<? extends AbstractEntry> entryClass,
			Whitespace[] whitespaces, Field[] usedPositions) {
		for (Whitespace whitespace : whitespaces) {
			markWhitespace(entryClass, whitespace, usedPositions);
		}
	}

	private void checkType(Field field, Class<?> expectedType) {
		if (!expectedType.isAssignableFrom(field.getType())) {
			throw new EntryDefinitionException(
					"Field type incompatible with annotation at "
							+ field.getDeclaringClass().getName() + "."
							+ field.getName());
		}
	}

	private void markUsed(Field field, int position, int length,
			Field[] usedPositions) {
		if (position < 1) {
			throw new EntryDefinitionException(
					"Field position cannot be less than one: "
							+ field.getDeclaringClass().getName() + "."
							+ field.getName());
		}
		if (length < 1) {
			throw new EntryDefinitionException(
					"Field length cannot be less than one: "
							+ field.getDeclaringClass().getName() + "."
							+ field.getName());
		}
		if (position + length > ROW_LENGTH + 1) {
			throw new EntryDefinitionException(
					"Field exceeds the row length limit of " + ROW_LENGTH
							+ " characters: "
							+ field.getDeclaringClass().getName() + "."
							+ field.getName());
		}
		for (int i = position; i < position + length; i++) {
			if (usedPositions[i - 1] != null) {
				throw new EntryDefinitionException("Field position " + i
						+ " already used by field "
						+ usedPositions[i - 1].getName() + ": "
						+ field.getDeclaringClass().getName() + "."
						+ field.getName());
			} else {
				usedPositions[i - 1] = field;
			}

		}
	}

	public char getTypeCharacter() {
		return typeCharacter;
	}

	private InvalidDataException invalidData(int line, int startColumn,
			int endColumn, String value) throws InvalidDataException {
		return new InvalidDataException("Illegal value at line: " + line
				+ ", columns: " + (startColumn + 1) + "-" + endColumn
				+ ", value=" + value);
	}

	private void processField(int index, Field field,
			final BooleanField annotation, Field[] usedPositions) {
		checkType(field, boolean.class);
		fieldReaders[index] = new FieldReader(field, annotation.position(), 1,
				usedPositions) {

			@Override
			Object read(int line, int startColumn, int endColumn, String value)
					throws InvalidDataException {
				char c = value.charAt(0);
				if (c == annotation.on()) {
					return true;
				} else if (c == annotation.off()) {
					return false;
				}
				throw invalidData(line, startColumn, endColumn, value);
			}

		};
	}

	private void processField(int index, Field field,
			final CharacterField annotation, Field[] usedPositions) {
		checkType(field, char.class);
		fieldReaders[index] = new FieldReader(field, annotation.position(), 1,
				usedPositions) {

			@Override
			Object read(int line, int startColumn, int endColumn, String value)
					throws InvalidDataException {
				char c = value.charAt(0);
				return c;
			}

		};
	}

	private void processField(int index, Field field,
			final EnumeratedField annotation, Field[] usedPositions) {
		checkType(field, Enum.class);
		final Map<String, Enum<?>> map = new HashMap<String, Enum<?>>();
		int length = annotation.length();
		EnumerationMapping[] mappings = annotation.mappings();
		Class<?> enumClass = field.getType();
		Enum<?>[] enumConstants = (Enum<?>[]) enumClass.getEnumConstants();
		for (EnumerationMapping mapping : mappings) {
			String value = mapping.value();
			if (value.length() != length) {
				throw new EntryDefinitionException("Mapping value (" + value
						+ ") length does not match field: "
						+ field.getDeclaringClass().getName() + "."
						+ field.getName());
			}

			String mapsTo = mapping.mapsTo();
			Enum<?> mapsToConstant = null;
			for (Enum<?> enumConstant : enumConstants) {
				if (enumConstant.name().equals(mapsTo)) {
					mapsToConstant = enumConstant;
					break;
				}
			}
			if (mapsToConstant == null) {
				throw new EntryDefinitionException("Mapping constant ("
						+ mapsTo + ") does not exist: "
						+ field.getDeclaringClass().getName() + "."
						+ field.getName());
			}
			if (map.put(value, mapsToConstant) != null) {
				if (mapsToConstant == null) {
					throw new EntryDefinitionException("Mapping value ("
							+ value + ") defined twice: "
							+ field.getDeclaringClass().getName() + "."
							+ field.getName());
				}
			}
		}
		fieldReaders[index] = new FieldReader(field, annotation.position(),
				length, usedPositions) {
			@Override
			Object read(int line, int startColumn, int endColumn, String value)
					throws InvalidDataException {
				Enum<?> mapsToConstant = map.get(value);
				if (mapsToConstant == null) {
					throw invalidData(line, startColumn, endColumn, value);
				}
				return mapsToConstant;
			}
		};
	}

	private boolean isNumber(String value, int start, int length) {
		int end = start + length;
		for (int i = start; i < end; i++) {
			char c = value.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

	private void processField(int index, Field field,
			final FloatField annotation, Field[] usedPositions) {
		checkType(field, float.class);
		final int integerDigits = annotation.digits() - annotation.decimals()
				- 1;
		fieldReaders[index] = new FieldReader(field, annotation.position(),
				annotation.digits(), usedPositions) {
			@Override
			Object read(int line, int startColumn, int endColumn, String value)
					throws InvalidDataException {
				if (!isNumber(value, 0, integerDigits)) {
					throw invalidData(line, startColumn, endColumn, value);
				}
				if (value.charAt(integerDigits) != '.') {
					throw invalidData(line, startColumn, endColumn, value);
				}
				if (!isNumber(value, integerDigits + 1, annotation.digits())) {
					throw invalidData(line, startColumn, endColumn, value);
				}
				return Float.parseFloat(value);
			}
		};
	}

	private void processField(int index, Field field,
			final IntegerField annotation, Field[] usedPositions) {
		checkType(field, int.class);
		fieldReaders[index] = new FieldReader(field, annotation.position(),
				annotation.digits(), usedPositions) {
			@Override
			Object read(int line, int startColumn, int endColumn, String value)
					throws InvalidDataException {
				if (!isNumber(value, 0, annotation.digits())) {
					throw invalidData(line, startColumn, endColumn, value);
				}
				return Integer.parseInt(value);
			}
		};
	}

	private void processField(int index, Field field,
			final StringField annotation, Field[] usedPositions) {
		checkType(field, String.class);
		fieldReaders[index] = new FieldReader(field, annotation.position(),
				annotation.length(), usedPositions) {
			@Override
			Object read(int line, int startColumn, int endColumn, String value)
					throws InvalidDataException {
				return value;
			}

		};
	}

	public AbstractEntry read(int line, String content)
			throws InvalidDataException {
		AbstractEntry entry;
		try {
			entry = entryClass.newInstance();
		} catch (InstantiationException e) {
			throw new EntryDefinitionException(e);
		} catch (IllegalAccessException e) {
			throw new EntryDefinitionException(e);
		}
		for (FieldReader fieldReader : fieldReaders) {
			fieldReader.read(entry, line, content);
		}
		for (int i = 0; i < whiteSpacePositions.length; i++) {
			if (whiteSpacePositions[i] && content.charAt(i) != ' ') {
				invalidData(line, i, i + 1, " ");
			}

		}
		return entry;

	}
}
