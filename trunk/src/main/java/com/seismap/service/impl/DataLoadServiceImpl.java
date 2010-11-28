package com.seismap.service.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.seismap.model.entity.Event;
import com.seismap.service.DataLoadService;
import com.seismap.service.parser.AbstractEntry;
import com.seismap.service.parser.DataProviderException;
import com.seismap.service.parser.InvalidDataException;
import com.seismap.service.parser.LogEvent;
import com.seismap.service.parser.Parser;
import com.seismap.service.parser.Parser.LogEventConsumer;
import com.seismap.service.parser.Parser.LogLineProvider;
import com.seismap.service.parser.ParserTest;
import com.seismap.service.parser.Type1Entry;


public class DataLoadServiceImpl implements DataLoadService {
	
	private class DatabaseLogEventConsumer implements LogEventConsumer {
		public void cosumeLogEvent(LogEvent logEvent) {
			Event event = null;
			Map events = logEvent.getLogEvent();
			List<Type1Entry> list = (List<Type1Entry>) events.get("1");
			if (list != null || !list.isEmpty()){
			for (Type1Entry entry : list) {
				if (event== null){
					Calendar calendar = Calendar.getInstance();
					Long lo = new Long((long)entry.getSeconds());
				
					//calendar.set(entry.getYear(), entry.getMonth(), entry.getDayOfMonth(), entry.getHour(), entry.getMinutes(), );
					
					//calendar.set(Calendar.SECOND, )
					//calendar.set(Calendar.MILLISECOND,entry.getSeconds());
					//event=new Event(entry.getLatitude(), entry.getLongitude(), entry.getDepth(), calendar.getTime(), magnitudes)
				}
			}
			}
			else
				System.out.println("No Type1 event in this file");
			
		}
	}
	
	public void dataLoadService(String file) throws InvalidDataException, DataProviderException, IOException{
		
		Parser parser = new Parser();
		InputStream inputStream = ParserTest.class.getClassLoader()
				.getResourceAsStream(file);
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
