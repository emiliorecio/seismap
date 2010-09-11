package com.seismap.service.parser;

import javax.swing.text.Position;

import com.seismap.service.model.DepthIndicator;
import com.seismap.service.model.DistanceIndicator;
import com.seismap.service.model.EventId;
import com.seismap.service.parser.annotation.BooleanColumn;
import com.seismap.service.parser.annotation.CharacterColumn;
import com.seismap.service.parser.annotation.EnumeratedColumn;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.FloatColumn;
import com.seismap.service.parser.annotation.IntegerColumn;

public class Type1Entry {

	@IntegerColumn(position=2, digits=4)
	private int year;

	@IntegerColumn(position=7, digits=2)
	private int month;

	@IntegerColumn(position=9, digits=2)
	private int dayOfMonth;

	@BooleanColumn(position=11, on='F', off=' ')
	private boolean fixOriginTime;

	@IntegerColumn(position=12, digits=2)
	private int hour;

	@IntegerColumn(position=14, digits=2)
	private int minutes;

	@FloatColumn(position=17, integerDigits=4, decimalDigits=1)
	private float seconds;

	@CharacterColumn(position=21)
	private char locationModelIndicator;

	@EnumeratedColumn(position=22, mappings={
			@EnumerationMapping(value='L', mapsTo="LOCAL"),
			@EnumerationMapping(value='R', mapsTo="REGIONAL"),
			@EnumerationMapping(value='D', mapsTo="DISTANT")
	})
	private DistanceIndicator distanceIndicator;

	private EventId eventId;

	private float latitude;

	private float longitude;

	private float depth;

	private DepthIndicator depthIndicator;
	
	private char locatingIndicator;
	
	private String hypocenterReportingAgency;

}
