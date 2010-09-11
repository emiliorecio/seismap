package com.seismap.service.parser;

import com.seismap.service.model.CulturalEffects;
import com.seismap.service.model.DiastrophismCode;
import com.seismap.service.model.IntensityScale;
import com.seismap.service.model.MagnitudeIntensityType;
import com.seismap.service.model.SeicheCode;
import com.seismap.service.model.TsunamiCode;
import com.seismap.service.model.UnusualEvents;
import com.seismap.service.parser.annotation.CharacterColumn;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.EnumeratedColumn;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.FloatColumn;
import com.seismap.service.parser.annotation.IntegerColumn;


@Entry(value = '2', type = EntryType.TWO)
public class Type2Entry {
	@EnumeratedColumn(position=22, length=1, mappings={
			@EnumerationMapping(value="F", mapsTo="SURFACE"),
			@EnumerationMapping(value="U", mapsTo="UPLIFT"),
			@EnumerationMapping(value="D", mapsTo="FAULTING")
	})
	private DiastrophismCode diastrophismCode;
	
	@EnumeratedColumn(position=23, length=1, mappings={
			@EnumerationMapping(value="T", mapsTo="TSUNAMI_GENERATED"),
			@EnumerationMapping(value="Q", mapsTo="POSSIBLE_TSUNAMI"),
	})
	private TsunamiCode tsunamiCode;
	
	@EnumeratedColumn(position=24, length=1, mappings={
			@EnumerationMapping(value="S", mapsTo="SEICHE"),
			@EnumerationMapping(value="Q", mapsTo="POSSIBLE_SEICHE"),
	})
	private SeicheCode seicheCode;
	
	@EnumeratedColumn(position=25, length=1, mappings={
			@EnumerationMapping(value="C", mapsTo="CASUALTIES_REPORTED"),
			@EnumerationMapping(value="D", mapsTo="DAMAGE_REPORTED"),
			@EnumerationMapping(value="F", mapsTo="EARTHQUAKE_WAS_LEFT"),
			@EnumerationMapping(value="H", mapsTo="EARTHQUAKE_WAS_HEARD")
	})
	private CulturalEffects culturalEffects;
	
	@EnumeratedColumn(position=26, length=1, mappings={
			@EnumerationMapping(value="L", mapsTo="LIQUEFACTION"),
			@EnumerationMapping(value="G", mapsTo="GEYSIR_FUMEROL"),
			@EnumerationMapping(value="S", mapsTo="LANDSLIDES_AVALANCHES"),
			@EnumerationMapping(value="B", mapsTo="SAND_BLOWS"),
			@EnumerationMapping(value="C", mapsTo="CRACKING_IN_THE_GROUND"),
			@EnumerationMapping(value="V", mapsTo="VISUAL_PHENOMENA"),
			@EnumerationMapping(value="O", mapsTo="OLFACTORY_PHENOMENA"),
			@EnumerationMapping(value="M", mapsTo="MORE_THAN_ONE_OF_THE_ABOVE_OBSERVERD")
	})
	private UnusualEvents unusualEvents;
	
	
	@IntegerColumn(position=28, digits=2)
	private int maxIntensity;
	
	@CharacterColumn(position=30)
	private char maxIntensityQualifier;
	
	@EnumeratedColumn(position=31, length=2 , mappings={
			@EnumerationMapping(value="MM", mapsTo="MODIFIED_MERCALLI"),
			@EnumerationMapping(value="RF", mapsTo="ROSSI_FOREL"),
			@EnumerationMapping(value="CS", mapsTo="MERCALLI_CANCANI_SEBERG"),
			@EnumerationMapping(value="SK", mapsTo="MEDEVEV_SPONHEUR_KARNIK33_FREE")			
	})
	private IntensityScale intensityScale;

	@FloatColumn(position = 34, integerDigits = 6, decimalDigits = 2)
	private int macroseismicLatitude;
	
	
	@FloatColumn(position = 41, integerDigits = 7, decimalDigits = 2)
	private int macroseismicLongitude;
	
	
	@FloatColumn(position = 49, integerDigits = 3, decimalDigits = 1)
	private int macroseismicMagnitude;
	
	
	@EnumeratedColumn(position=52, length=1 , mappings={
			@EnumerationMapping(value="A", mapsTo="MAGNITUDE_BASED_ON_FELT_AREA"),
			@EnumerationMapping(value="R", mapsTo="MAGNITUDE_BASED_ON_RADIUS_AREA"),
			@EnumerationMapping(value="*", mapsTo="MAGNITUDE_CUSTOM_CALCULATED"),
	})
	private MagnitudeIntensityType typeOfMagnitude_I;

	
	@FloatColumn(position = 53, integerDigits = 4, decimalDigits = 2)
	private int logarithmRadiusFeltArea;
	
	
	@FloatColumn(position = 57, integerDigits = 5, decimalDigits = 2)
	private int logarithmAreaNumber1;
	
	
	@IntegerColumn(position = 62, digits=2)
	private int intensityBoardering1;
	
	@FloatColumn(position = 64, integerDigits = 5, decimalDigits = 2)
	private int logarithmAreaNumber2;
	
	@IntegerColumn(position = 69, digits=2)
	private int intensityBoardering2;
	
	@CharacterColumn(position= 72)
	private char qualityRankReport;

	public DiastrophismCode getDiastrophismCode() {
		return diastrophismCode;
	}

	public void setDiastrophismCode(DiastrophismCode diastrophismCode) {
		this.diastrophismCode = diastrophismCode;
	}

	public TsunamiCode getTsunamiCode() {
		return tsunamiCode;
	}

	public void setTsunamiCode(TsunamiCode tsunamiCode) {
		this.tsunamiCode = tsunamiCode;
	}

	public SeicheCode getSeicheCode() {
		return seicheCode;
	}

	public void setSeicheCode(SeicheCode seicheCode) {
		this.seicheCode = seicheCode;
	}

	public CulturalEffects getCulturalEffects() {
		return culturalEffects;
	}

	public void setCulturalEffects(CulturalEffects culturalEffects) {
		this.culturalEffects = culturalEffects;
	}

	public UnusualEvents getUnusualEvents() {
		return unusualEvents;
	}

	public void setUnusualEvents(UnusualEvents unusualEvents) {
		this.unusualEvents = unusualEvents;
	}

	public int getMaxIntensity() {
		return maxIntensity;
	}

	public void setMaxIntensity(int maxIntensity) {
		this.maxIntensity = maxIntensity;
	}

	public char getMaxIntensityQualifier() {
		return maxIntensityQualifier;
	}

	public void setMaxIntensityQualifier(char maxIntensityQualifier) {
		this.maxIntensityQualifier = maxIntensityQualifier;
	}

	public IntensityScale getIntensityScale() {
		return intensityScale;
	}

	public void setIntensityScale(IntensityScale intensityScale) {
		this.intensityScale = intensityScale;
	}

	public int getMacroseismicLatitude() {
		return macroseismicLatitude;
	}

	public void setMacroseismicLatitude(int macroseismicLatitude) {
		this.macroseismicLatitude = macroseismicLatitude;
	}

	public int getMacroseismicLongitude() {
		return macroseismicLongitude;
	}

	public void setMacroseismicLongitude(int macroseismicLongitude) {
		this.macroseismicLongitude = macroseismicLongitude;
	}

	public int getMacroseismicMagnitude() {
		return macroseismicMagnitude;
	}

	public void setMacroseismicMagnitude(int macroseismicMagnitude) {
		this.macroseismicMagnitude = macroseismicMagnitude;
	}

	public MagnitudeIntensityType getTypeOfMagnitude_I() {
		return typeOfMagnitude_I;
	}

	public void setTypeOfMagnitude_I(MagnitudeIntensityType typeOfMagnitude_I) {
		this.typeOfMagnitude_I = typeOfMagnitude_I;
	}

	public int getLogarithmRadiusFeltArea() {
		return logarithmRadiusFeltArea;
	}

	public void setLogarithmRadiusFeltArea(int logarithmRadiusFeltArea) {
		this.logarithmRadiusFeltArea = logarithmRadiusFeltArea;
	}

	public int getLogarithmAreaNumber1() {
		return logarithmAreaNumber1;
	}

	public void setLogarithmAreaNumber1(int logarithmAreaNumber1) {
		this.logarithmAreaNumber1 = logarithmAreaNumber1;
	}

	public int getIntensityBoardering1() {
		return intensityBoardering1;
	}

	public void setIntensityBoardering1(int intensityBoardering1) {
		this.intensityBoardering1 = intensityBoardering1;
	}

	public int getLogarithmAreaNumber2() {
		return logarithmAreaNumber2;
	}

	public void setLogarithmAreaNumber2(int logarithmAreaNumber2) {
		this.logarithmAreaNumber2 = logarithmAreaNumber2;
	}

	public int getIntensityBoardering2() {
		return intensityBoardering2;
	}

	public void setIntensityBoardering2(int intensityBoardering2) {
		this.intensityBoardering2 = intensityBoardering2;
	}

	public char getQualityRankReport() {
		return qualityRankReport;
	}

	public void setQualityRankReport(char qualityRankReport) {
		this.qualityRankReport = qualityRankReport;
	}
	
	
}
