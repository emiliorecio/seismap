package com.seismap.service.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.seismap.common.SeismapException;
import com.seismap.model.entity.Agency;
import com.seismap.model.entity.Event;
import com.seismap.model.entity.Magnitude;
import com.seismap.model.repository.AgencyRepository;
import com.seismap.model.repository.EventRepository;
import com.seismap.service.DataLoadService;
import com.seismap.service.parser.DataProviderException;
import com.seismap.service.parser.InvalidDataException;
import com.seismap.service.parser.LogEvent;
import com.seismap.service.parser.Parser;
import com.seismap.service.parser.Type1Entry;
import com.seismap.service.parser.Parser.LogEventConsumer;
import com.seismap.service.parser.Parser.LogLineProvider;
import com.seismap.service.parser.enumeration.MagnitudeType;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class DataLoadServiceImpl implements DataLoadService {

	private AgencyRepository agencyRepository;
	private EventRepository eventRepository;
	private GeometryFactory geometryFactory;

	protected DataLoadServiceImpl() {
	}

	public DataLoadServiceImpl(AgencyRepository agencyRepository,
			EventRepository eventRepository, GeometryFactory geometryFactory) {
		this.agencyRepository = agencyRepository;
		this.eventRepository = eventRepository;
		this.geometryFactory = geometryFactory;
	}

	private class DatabaseLogEventConsumer implements LogEventConsumer {
		public void cosumeLogEvent(LogEvent logEvent) {
			Date date = null;
			float longitude = 0;
			float latitude = 0;
			float depth = 0;
			List<Type1Entry> type1Entries = logEvent.getEntries("1");
			if (type1Entries.isEmpty()) {
				throw new SeismapException("No Type1 entry found in log event");
			}
			List<Magnitude> magnitudes = new LinkedList<Magnitude>();
			for (Type1Entry entry : type1Entries) {
				if (date == null) {
					Calendar calendar = Calendar.getInstance();

					calendar.set(entry.getYear(), entry.getMonth(), entry
							.getDayOfMonth(), entry.getHour(), entry
							.getMinutes(), (int) entry.getSeconds());
					date = calendar.getTime();
					longitude = entry.getLongitude();
					latitude = entry.getLatitude();
					depth = entry.getDepth();

				}
				if (entry.getMagnitude1Type() != MagnitudeType.BLANK) {
					magnitudes.add(getMagnituede(entry.getMagnitude1(), entry
							.getMagnitude1ReportingAgency(), entry
							.getMagnitude1Type()));
				}
				if (entry.getMagnitude2Type() != MagnitudeType.BLANK) {
					magnitudes.add(getMagnituede(entry.getMagnitude2(), entry
							.getMagnitude2ReportingAgency(), entry
							.getMagnitude2Type()));
				}
				if (entry.getMagnitude3Type() != MagnitudeType.BLANK) {
					magnitudes.add(getMagnituede(entry.getMagnitude3(), entry
							.getMagnitude3ReportingAgency(), entry
							.getMagnitude3Type()));
				}
			}
			Point location = geometryFactory.createPoint(new Coordinate(
					latitude, longitude));
			Event event = new Event(location, depth, date, magnitudes);
			eventRepository.put(event);
		}

	}

	private com.seismap.model.entity.MagnitudeType getMagnitudeType(
			MagnitudeType magnitudeType) {
		switch (magnitudeType) {
		case ML:
			return com.seismap.model.entity.MagnitudeType.ML;
		case MB:
			return com.seismap.model.entity.MagnitudeType.MB;
		case MS:
			return com.seismap.model.entity.MagnitudeType.MS;
		case MW:
			return com.seismap.model.entity.MagnitudeType.MW;
		case MBLG:
			return com.seismap.model.entity.MagnitudeType.MBLG;
		case MC:
			return com.seismap.model.entity.MagnitudeType.MC;
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
