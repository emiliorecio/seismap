package com.seismap.service.parser;

import com.seismap.service.model.CulturalEffects;
import com.seismap.service.model.DiastrophismCode;
import com.seismap.service.model.SeicheCode;
import com.seismap.service.model.TsunamiCode;
import com.seismap.service.model.UnusualEvents;
import com.seismap.service.parser.annotation.CharacterColumn;
import com.seismap.service.parser.annotation.EnumeratedColumn;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.IntegerColumn;



public class Type2Entry {
	@EnumeratedColumn(position=22, mappings={
			@EnumerationMapping(value='F', mapsTo="SURFACE"),
			@EnumerationMapping(value='U', mapsTo="UPLIFT"),
			@EnumerationMapping(value='D', mapsTo="FAULTING")
	})
	private DiastrophismCode diastrophismCode;
	
	@EnumeratedColumn(position=23, mappings={
			@EnumerationMapping(value='T', mapsTo="TSUNAMI_GENERATED"),
			@EnumerationMapping(value='Q', mapsTo="POSSIBLE_TSUNAMI"),
	})
	private TsunamiCode tsunamiCode;
	
	@EnumeratedColumn(position=24, mappings={
			@EnumerationMapping(value='S', mapsTo="SEICHE"),
			@EnumerationMapping(value='Q', mapsTo="POSSIBLE_SEICHE"),
	})
	private SeicheCode seicheCode;
	
	@EnumeratedColumn(position=25, mappings={
			@EnumerationMapping(value='C', mapsTo="CASUALTIES_REPORTED"),
			@EnumerationMapping(value='D', mapsTo="DAMAGE_REPORTED"),
			@EnumerationMapping(value='F', mapsTo="EARTHQUAKE_WAS_LEFT"),
			@EnumerationMapping(value='H', mapsTo="EARTHQUAKE_WAS_HEARD")
	})
	private CulturalEffects culturalEffects;
	
	@EnumeratedColumn(position=26, mappings={
			@EnumerationMapping(value='L', mapsTo="LIQUEFACTION"),
			@EnumerationMapping(value='G', mapsTo="GEYSIR_FUMEROL"),
			@EnumerationMapping(value='S', mapsTo="LANDSLIDES_AVALANCHES"),
			@EnumerationMapping(value='B', mapsTo="SAND_BLOWS"),
			@EnumerationMapping(value='C', mapsTo="CRACKING_IN_THE_GROUND"),
			@EnumerationMapping(value='V', mapsTo="VISUAL_PHENOMENA"),
			@EnumerationMapping(value='O', mapsTo="OLFACTORY_PHENOMENA"),
			@EnumerationMapping(value='M', mapsTo="MORE_THAN_ONE_OF_THE_ABOVE_OBSERVERD")
	})
	private UnusualEvents unusualEvents;
	
	
	@IntegerColumn(position=28, digits=2)
	private int maxIntensity;
	
	@CharacterColumn(position=30)
	private char maxIntensityQualifier;
	
	
	
	
}
