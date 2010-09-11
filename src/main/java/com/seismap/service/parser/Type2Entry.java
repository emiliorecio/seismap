package com.seismap.service.parser;

import com.seismap.service.model.CulturalEffects;
import com.seismap.service.model.DiastrophismCode;
import com.seismap.service.model.IntensityScale;
import com.seismap.service.model.MagnitudeIntensityType;
import com.seismap.service.model.SeicheCode;
import com.seismap.service.model.TsunamiCode;
import com.seismap.service.model.UnusualEvents;
import com.seismap.service.parser.annotation.CharacterField;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.EnumeratedField;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.FloatField;
import com.seismap.service.parser.annotation.IntegerField;

@Entry(value = '2', type = EntryType.TWO)
public class Type2Entry extends AbstractEntry {
	@EnumeratedField(position = 22, length = 1, mappings = {
			@EnumerationMapping(value = "F", mapsTo = "SURFACE"),
			@EnumerationMapping(value = "U", mapsTo = "UPLIFT"),
			@EnumerationMapping(value = "D", mapsTo = "FAULTING") })
	private DiastrophismCode diastrophismCode;

	@EnumeratedField(position = 23, length = 1, mappings = {
			@EnumerationMapping(value = "T", mapsTo = "TSUNAMI_GENERATED"),
			@EnumerationMapping(value = "Q", mapsTo = "POSSIBLE_TSUNAMI"), })
	private TsunamiCode tsunamiCode;

	@EnumeratedField(position = 24, length = 1, mappings = {
			@EnumerationMapping(value = "S", mapsTo = "SEICHE"),
			@EnumerationMapping(value = "Q", mapsTo = "POSSIBLE_SEICHE"), })
	private SeicheCode seicheCode;

	@EnumeratedField(position = 25, length = 1, mappings = {
			@EnumerationMapping(value = "C", mapsTo = "CASUALTIES_REPORTED"),
			@EnumerationMapping(value = "D", mapsTo = "DAMAGE_REPORTED"),
			@EnumerationMapping(value = "F", mapsTo = "EARTHQUAKE_WAS_LEFT"),
			@EnumerationMapping(value = "H", mapsTo = "EARTHQUAKE_WAS_HEARD") })
	private CulturalEffects culturalEffects;

	@EnumeratedField(position = 26, length = 1, mappings = {
			@EnumerationMapping(value = "L", mapsTo = "LIQUEFACTION"),
			@EnumerationMapping(value = "G", mapsTo = "GEYSIR_FUMEROL"),
			@EnumerationMapping(value = "S", mapsTo = "LANDSLIDES_AVALANCHES"),
			@EnumerationMapping(value = "B", mapsTo = "SAND_BLOWS"),
			@EnumerationMapping(value = "C", mapsTo = "CRACKING_IN_THE_GROUND"),
			@EnumerationMapping(value = "V", mapsTo = "VISUAL_PHENOMENA"),
			@EnumerationMapping(value = "O", mapsTo = "OLFACTORY_PHENOMENA"),
			@EnumerationMapping(value = "M", mapsTo = "MORE_THAN_ONE_OF_THE_ABOVE_OBSERVERD") })
	private UnusualEvents unusualEvents;

	@IntegerField(position = 28, digits = 2)
	private int maxIntensity;

	@CharacterField(position = 30)
	private char maxIntensityQualifier;

	@EnumeratedField(position = 31, length = 2, mappings = {
			@EnumerationMapping(value = "MM", mapsTo = "MODIFIED_MERCALLI"),
			@EnumerationMapping(value = "RF", mapsTo = "ROSSI_FOREL"),
			@EnumerationMapping(value = "CS", mapsTo = "MERCALLI_CANCANI_SEBERG"),
			@EnumerationMapping(value = "SK", mapsTo = "MEDEVEV_SPONHEUR_KARNIK33_FREE") })
	private IntensityScale intensityScale;

	@FloatField(position = 34, digits = 6, decimals = 2)
	private float macroseismicLatitude;

	@FloatField(position = 41, digits = 7, decimals = 2)
	private float macroseismicLongitude;

	@FloatField(position = 49, digits = 3, decimals = 1)
	private float macroseismicMagnitude;

	@EnumeratedField(position = 52, length = 1, mappings = {
			@EnumerationMapping(value = "A", mapsTo = "MAGNITUDE_BASED_ON_FELT_AREA"),
			@EnumerationMapping(value = "R", mapsTo = "MAGNITUDE_BASED_ON_RADIUS_AREA"),
			@EnumerationMapping(value = "*", mapsTo = "MAGNITUDE_CUSTOM_CALCULATED"), })
	private MagnitudeIntensityType typeOfMagnitude_I;

	@FloatField(position = 53, digits = 4, decimals = 2)
	private float logarithmRadiusFeltArea;

	@FloatField(position = 57, digits = 5, decimals = 2)
	private float logarithmAreaNumber1;

	@IntegerField(position = 62, digits = 2)
	private int intensityBoardering1;

	@FloatField(position = 64, digits = 5, decimals = 2)
	private float logarithmAreaNumber2;

	@IntegerField(position = 69, digits = 2)
	private int intensityBoardering2;

	@CharacterField(position = 72)
	private char qualityRankReport;

}
