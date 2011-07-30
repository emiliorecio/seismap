package com.seismap.service.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.seismap.service.parser.annotation.BooleanField;
import com.seismap.service.parser.annotation.CharacterField;
import com.seismap.service.parser.annotation.ConstantField;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.EnumeratedField;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.FloatField;
import com.seismap.service.parser.annotation.IntegerField;
import com.seismap.service.parser.annotation.ScientificNotationField;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;

public class RowReader {

	private abstract class FieldReader {
		private Field field;
		private int startColumn;
		private int endColumn;
		private boolean readOnly;

		public FieldReader(Field field, int position, int length,
				Field[] usedPositions) {
			markUsed(field, position, length, usedPositions);
			this.field = field;
			this.startColumn = position - 1;
			this.endColumn = position + length - 1;
			this.readOnly = Modifier.isFinal(field.getModifiers());
		}

		public void read(AbstractEntry entry, int line, String content)
				throws InvalidDataException {
			String value = content.substring(startColumn, endColumn);
			Object object = read(line, startColumn, endColumn, value);
			if (!readOnly) {
				try {
					field.setAccessible(true);
					field.set(entry, object);
				} catch (IllegalArgumentException e) {
					throw new EntryDefinitionException(e);
				} catch (IllegalAccessException e) {
					throw new EntryDefinitionException(e);
				}
			}
		}

		abstract Object read(int line, int startColumn, int endColumn,
				String value) throws InvalidDataException;
	}

	private static final int TOTAL_ROW_LENGTH = 80;
	private int rowLength;
	private String id;
	private String[] alternativeIds;
	private String[] afterIds;
	private String[] allIds;
	private FieldReader[] fieldReaders;
	private Class<? extends AbstractEntry> entryClass;
	private boolean[] whiteSpacePositions;
	private boolean terminator;

	public RowReader(Class<? extends AbstractEntry> entryClass) {
		Entry entry = entryClass.getAnnotation(Entry.class);
		if (entry == null) {
			throw new EntryDefinitionException("Missing @Entry annotation: "
					+ entryClass.getName());
		}
		this.entryClass = entryClass;
		this.id = entry.id();
		this.alternativeIds = entry.alternative();
		this.afterIds = entry.after();
		this.allIds = new String[alternativeIds.length + 1];
		this.allIds[0] = this.id;
		this.terminator = entry.terminator();
		for (int i = 0; i < this.alternativeIds.length; i++) {
			String alternativeId = this.alternativeIds[i];
			if (alternativeId.length() != this.id.length()) {
				throw new EntryDefinitionException("Alternative id '"
						+ alternativeId
						+ "' has different length than the regular id '" + id
						+ "': '" + entryClass.getName());
			}
			this.allIds[i + 1] = alternativeId;
		}
		rowLength = TOTAL_ROW_LENGTH - this.id.length();
		Field[] fields = entryClass.getDeclaredFields();
		Field[] usedPositions = new Field[rowLength];
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
			if (!Modifier.isStatic(field.getModifiers())
					&& !Modifier.isFinal(field.getModifiers())) {
				if (annotation instanceof BooleanField) {
					processField(i, field, (BooleanField) annotation,
							usedPositions);
				} else if (annotation instanceof CharacterField) {
					processField(i, field, (CharacterField) annotation,
							usedPositions);
				} else if (annotation instanceof EnumeratedField) {
					processField(i, field, (EnumeratedField) annotation,
							usedPositions);
				} else if (annotation instanceof FloatField) {
					processField(i, field, (FloatField) annotation,
							usedPositions);
				} else if (annotation instanceof IntegerField) {
					processField(i, field, (IntegerField) annotation,
							usedPositions);
				} else if (annotation instanceof ScientificNotationField) {
					processField(i, field,
							(ScientificNotationField) annotation, usedPositions);
				} else if (annotation instanceof StringField) {
					processField(i, field, (StringField) annotation,
							usedPositions);
				} else {
					throw new EntryDefinitionException(
							"Invalid annotation for instance field "
									+ entryClass.getName() + '.'
									+ field.getName() + ": " + annotation);
				}
			} else if (Modifier.isStatic(field.getModifiers())
					&& Modifier.isFinal(field.getModifiers())) {
				if (annotation instanceof ConstantField) {
					processField(i, field, (ConstantField) annotation,
							usedPositions);
				} else {
					throw new EntryDefinitionException(
							"Invalid annotation for static final field "
									+ entryClass.getName() + '.'
									+ field.getName() + ": " + annotation);
				}
			} else {
				throw new EntryDefinitionException(
						"Field must be instance non-final, or static final: "
								+ entryClass.getName() + '.' + field.getName());
			}
		}
		whiteSpacePositions = new boolean[rowLength];
		markWhitespaces(entryClass, entry.whitespaces(), usedPositions);
		for (int i = 1; i <= rowLength; i++) {
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
		if (whitespace.position() + whitespace.length() > rowLength + 1) {
			throw new EntryDefinitionException(
					"Whitespace exceeds the row length limit of " + rowLength
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

	private void checkType(Field field, Class<?>... expectedTypes) {
		for (Class<?> expectedType : expectedTypes) {
			if (expectedType.isAssignableFrom(field.getType())) {
				return;
			}

		}
		throw new EntryDefinitionException(
				"Field type incompatible with annotation at "
						+ field.getDeclaringClass().getName() + "."
						+ field.getName());
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
		if (position + length > rowLength + 1) {
			throw new EntryDefinitionException(
					"Field exceeds the row length limit of " + rowLength
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

	public String[] getAllIds() {
		return allIds;
	}

	public String getId() {
		return id;
	}

	public String[] getAlternativeIds() {
		return alternativeIds;
	}

	public String[] getAfterIds() {
		return afterIds;
	}

	public boolean isTerminator() {
		return terminator;
	}

	private InvalidDataException invalidData(int line, int startColumn,
			int endColumn, String value, Field field)
			throws InvalidDataException {
		return new InvalidDataException("Illegal value"
				+ (field == null ? "" : " for "
						+ field.getDeclaringClass().getName() + "."
						+ field.getName()) + " at line: " + line
				+ ", columns: " + (startColumn + 1) + "-" + endColumn
				+ ", value=" + value);
	}

	private void processField(int index, final Field field,
			final BooleanField annotation, Field[] usedPositions) {
		checkType(field, boolean.class);
		fieldReaders[index] = new FieldReader(field, annotation.position(), 1,
				usedPositions) {

			@Override
			Object read(int line, int startColumn, int endColumn, String value)
					throws InvalidDataException {
				char c = value.charAt(0);
				if (c == annotation.on()) {
					return Boolean.TRUE;
				} else if (c == annotation.off()) {
					return Boolean.FALSE;
				}
				throw invalidData(line, startColumn, endColumn, value, field);
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
				return Character.valueOf(c);
			}

		};
	}

	private void processField(int index, final Field field,
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
				throw new EntryDefinitionException("Mapping value (" + value
						+ ") defined twice: "
						+ field.getDeclaringClass().getName() + "."
						+ field.getName());
			}
		}
		fieldReaders[index] = new FieldReader(field, annotation.position(),
				length, usedPositions) {
			@Override
			Object read(int line, int startColumn, int endColumn, String value)
					throws InvalidDataException {
				Enum<?> mapsToConstant = map.get(value);
				if (mapsToConstant == null) {
					throw invalidData(line, startColumn, endColumn, value,
							field);
				}
				return mapsToConstant;
			}
		};
	}

	private void processField(int index, final Field field,
			final FloatField annotation, Field[] usedPositions) {
		checkType(field, float.class, Float.class);
		fieldReaders[index] = new FieldReader(field, annotation.position(),
				annotation.digits(), usedPositions) {
			@Override
			Object read(int line, int startColumn, int endColumn, String value)
					throws InvalidDataException {
				int indexOfDot = value.indexOf('.');
				String processedValue = value.trim();
				if (processedValue.length() == 0) {
					if (field.getType() == Float.class) {
						return null;
					} else {
						throw invalidData(line, startColumn, endColumn, value,
								field);
					}
				}
				if (indexOfDot == -1) {
					processedValue = processedValue.substring(0,
							processedValue.length() - annotation.decimals())
							+ '.'
							+ processedValue.substring(processedValue.length()
									- annotation.decimals());
				}
				try {
					return Float.valueOf(processedValue);
				} catch (NumberFormatException e) {
					throw invalidData(line, startColumn, endColumn, value,
							field);
				}
			}
		};
	}

	private void processField(int index, final Field field,
			final IntegerField annotation, Field[] usedPositions) {
		checkType(field, int.class, Integer.class, long.class, Long.class);
		if (field.getType() == int.class || field.getType() == Integer.class) {
			fieldReaders[index] = new FieldReader(field, annotation.position(),
					annotation.digits(), usedPositions) {
				@Override
				Object read(int line, int startColumn, int endColumn,
						String value) throws InvalidDataException {
					String processedValue = value.trim();
					if (processedValue.length() == 0) {
						if (field.getType() == Integer.class) {
							return null;
						} else {
							throw invalidData(line, startColumn, endColumn,
									value, field);
						}
					}
					try {
						return Integer.valueOf(processedValue);
					} catch (NumberFormatException e) {
						throw invalidData(line, startColumn, endColumn, value,
								field);
					}
				}
			};
		} else {
			fieldReaders[index] = new FieldReader(field, annotation.position(),
					annotation.digits(), usedPositions) {
				@Override
				Object read(int line, int startColumn, int endColumn,
						String value) throws InvalidDataException {
					String processedValue = value.trim();
					if (processedValue.length() == 0) {
						if (field.getType() == Long.class) {
							return null;
						} else {
							throw invalidData(line, startColumn, endColumn,
									value, field);
						}
					}
					try {
						return Long.valueOf(processedValue);
					} catch (NumberFormatException e) {
						throw invalidData(line, startColumn, endColumn, value,
								field);
					}
				}
			};
		}
	}

	private void processField(int index, final Field field,
			final ScientificNotationField annotation, Field[] usedPositions) {
		checkType(field, float.class);
		fieldReaders[index] = new FieldReader(field, annotation.position(),
				annotation.digits(), usedPositions) {
			@Override
			Object read(int line, int startColumn, int endColumn, String value)
					throws InvalidDataException {
				String processedValue = value.trim();
				if (processedValue.length() == 0) {
					return Float.valueOf(0.0f);
				}
				try {
					return Float.valueOf(processedValue);
				} catch (NumberFormatException e) {
					throw invalidData(line, startColumn, endColumn, value,
							field);
				}
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

	private void processField(int index, final Field field,
			final ConstantField annotation, Field[] usedPositions) {
		checkType(field, String.class);
		final String constantValue;
		try {
			field.setAccessible(true);
			constantValue = (String) field.get(null);
		} catch (IllegalArgumentException e) {
			throw new EntryDefinitionException(e);
		} catch (IllegalAccessException e) {
			throw new EntryDefinitionException(e);
		}
		if (constantValue == null) {
			throw new EntryDefinitionException(
					"Constant value must not be null: " + entryClass.getName()
							+ '.' + field.getName());
		}
		fieldReaders[index] = new FieldReader(field, annotation.position(),
				constantValue.length(), usedPositions) {
			@Override
			Object read(int line, int startColumn, int endColumn, String value)
					throws InvalidDataException {
				if (!value.equals(constantValue)) {
					throw invalidData(line, startColumn, endColumn, value,
							field);
				}
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
				invalidData(line, i, i + 1, " ", null);
			}

		}
		return entry;

	}
}
