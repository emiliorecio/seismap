package com.seismap.service.event;

public enum ExtendedMagnitudeType {

	RANK(null),

	ML(MagnitudeType.ML),

	MB(MagnitudeType.MB),

	MS(MagnitudeType.MS),

	MW(MagnitudeType.MW),

	MBLG(MagnitudeType.MBLG),

	MC(MagnitudeType.MC);

	private MagnitudeType magnitudeType;

	private ExtendedMagnitudeType(MagnitudeType magnitudeType) {
		this.magnitudeType = magnitudeType;
	}

	public MagnitudeType getMagnitudeType() {
		return magnitudeType;
	}
}
