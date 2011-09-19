package com.seismap.service.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.seismap.common.SeismapException;
import com.seismap.model.entity.Agency;
import com.seismap.model.entity.Event;
import com.seismap.model.entity.Magnitude;
import com.seismap.model.repository.AgencyRepository;
import com.seismap.model.repository.EventRepository;
import com.seismap.service.dataload.DataLoadService;
import com.seismap.service.impl.CoordinatesConverter.LatitudeLongitudePosition;
import com.seismap.service.impl.CoordinatesConverter.SphericalMercatorPosition;
import com.seismap.service.parser.DataProviderException;
import com.seismap.service.parser.InvalidDataException;
import com.seismap.service.parser.LogEvent;
import com.seismap.service.parser.Parser;
import com.seismap.service.parser.Parser.LogEventConsumer;
import com.seismap.service.parser.Parser.LogLineProvider;
import com.seismap.service.parser.Type1Entry;
import com.seismap.service.parser.enumeration.MagnitudeType;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class DataLoadServiceImpl implements DataLoadService {

	private AgencyRepository agencyRepository;
	private EventRepository eventRepository;
	private GeometryFactory geometryFactory;
	private CoordinatesConverter coordinatesConverter;

	protected DataLoadServiceImpl() {
	}

	public DataLoadServiceImpl(AgencyRepository agencyRepository,
			EventRepository eventRepository, GeometryFactory geometryFactory,
			CoordinatesConverter coordinatesConverter) {
		this.agencyRepository = agencyRepository;
		this.eventRepository = eventRepository;
		this.geometryFactory = geometryFactory;
		this.coordinatesConverter = coordinatesConverter;
	}

	private class DatabaseLogEventConsumer implements LogEventConsumer {
		public void cosumeLogEvent(LogEvent logEvent) {
			Date date = null;
			Float longitude = null;
			Float latitude = null;
			Float depth = null;
			List<Type1Entry> type1Entries = logEvent.getEntries("1");
			if (type1Entries.isEmpty()) {
				throw new SeismapException("No Type1 entry found in log event");
			}
			List<Magnitude> magnitudes = new LinkedList<Magnitude>();
			for (Type1Entry entry : type1Entries) {
				if (date == null) {
					Calendar calendar = new GregorianCalendar(entry.getYear(),
							entry.getMonth(), entry.getDayOfMonth(),
							entry.getHour(), entry.getMinutes(),
							(int) entry.getSeconds());
					date = calendar.getTime();
					longitude = entry.getLongitude();
					latitude = entry.getLatitude();
					depth = entry.getDepth();

				}
				if (entry.getMagnitude1Type() != MagnitudeType.BLANK
						&& entry.getMagnitude1() != null) {
					magnitudes.add(getMagnituede(entry.getMagnitude1()
							.floatValue(),
							entry.getMagnitude1ReportingAgency(), entry
									.getMagnitude1Type()));
				}
				if (entry.getMagnitude2Type() != MagnitudeType.BLANK
						&& entry.getMagnitude2() != null) {
					magnitudes.add(getMagnituede(entry.getMagnitude2()
							.floatValue(),
							entry.getMagnitude2ReportingAgency(), entry
									.getMagnitude2Type()));
				}
				if (entry.getMagnitude3Type() != MagnitudeType.BLANK
						&& entry.getMagnitude3() != null) {
					magnitudes.add(getMagnituede(entry.getMagnitude3()
							.floatValue(),
							entry.getMagnitude3ReportingAgency(), entry
									.getMagnitude3Type()));
				}
			}
			if (latitude == null || longitude == null || depth == null) {
				System.out.println("Warning: incomplete event " + date);
				return;
			}
			LatitudeLongitudePosition latitudeLongitudePosition = coordinatesConverter
					.createLatitudeLongitudePosition(latitude.floatValue(),
							longitude.floatValue());
			SphericalMercatorPosition sphericalMercatorPosition = latitudeLongitudePosition
					.getSphericalMercatorPosition();
			Point location = geometryFactory.createPoint(new Coordinate(
					sphericalMercatorPosition.getX(), sphericalMercatorPosition
							.getY()));
			Event event = new Event(location, depth.floatValue(), date,
					magnitudes, null, null, null);
			eventRepository.put(event);
		}

	}

	private com.seismap.service.event.MagnitudeType getMagnitudeType(
			MagnitudeType magnitudeType) {
		switch (magnitudeType) {
		case ML:
			return com.seismap.service.event.MagnitudeType.ML;
		case MB:
			return com.seismap.service.event.MagnitudeType.MB;
		case MS:
			return com.seismap.service.event.MagnitudeType.MS;
		case MW:
			return com.seismap.service.event.MagnitudeType.MW;
		case MBLG:
			return com.seismap.service.event.MagnitudeType.MBLG;
		case MC:
			return com.seismap.service.event.MagnitudeType.MC;
		default:
			throw new IllegalArgumentException("Invalid magnitude type: "
					+ magnitudeType);
		}
	}

	private Magnitude getMagnituede(float magnitudeValue,
			String magnitudeReportingAgency, MagnitudeType magnitudeType) {
		Magnitude magnitude = new Magnitude(
				getReportingAgency(magnitudeReportingAgency),
				getMagnitudeType(magnitudeType), magnitudeValue);
		return magnitude;
	}

	private Agency getReportingAgency(String magnitudeReportingAgency) {
		Agency agency = agencyRepository.getByCode(magnitudeReportingAgency);
		if (agency == null) {
			agency = new Agency(magnitudeReportingAgency);
			agencyRepository.put(agency);
		}

		return agency;
	}

	@Transactional
	public void load(String file) throws InvalidDataException,
			DataProviderException, IOException {

		Parser parser = new Parser();
		InputStream inputStream = new FileInputStream(file);
		DataInputStream in = new DataInputStream(inputStream);
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		parser.parse(new LogLineProvider() {
			public String readLogLine(int lineNumber)
					throws DataProviderException {
				try {
					return br.readLine();
				} catch (IOException e) {
					throw new DataProviderException(e);
				}
			}
		}, new DatabaseLogEventConsumer());
		in.close();
	}

}
