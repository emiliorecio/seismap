package com.seismap.service.parser;

import com.seismap.service.parser.Parser.LogEventConsumer;
import com.seismap.service.parser.Parser.LogLineProvider;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class ParserTest {

	private int count;

	@Test
	public void parseSampleData() throws Exception {
		count = 0;
		Parser parser = new Parser();
		InputStream inputStream = ParserTest.class.getClassLoader()
				.getResourceAsStream("datafiles/collect2010-2011.data");
		Assert.assertNotNull(inputStream);
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
		}, new LogEventConsumer() {
			public void cosumeLogEvent(LogEvent logEvent) {
				count++;
			}
		});
		in.close();
		System.out.println("Total line processed: "+count);
		Assert.assertEquals(7374, count);
	}

}
